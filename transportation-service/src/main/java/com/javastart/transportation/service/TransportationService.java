package com.javastart.transportation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javastart.transportation.dto.TransportationResponseDTO;
import com.javastart.transportation.entity.Transportation;
import com.javastart.transportation.exception.SavingToDatabaseException;
import com.javastart.transportation.rest.UserResponseDTO;
import com.javastart.transportation.rest.UserServiceClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import com.javastart.transportation.repository.TransportationRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransportationService {

    private static final String TOPIC_EXCHANGE_TRANSPORTATION = "js.trans.notify.exchange";
    private static final String ROUTING_KEY_TRANSPORTATION = "js.key.transportation";


    private final TransportationRepository transportationRepository;

    private final UserServiceClient userServiceClient;

    private final RabbitTemplate rabbitTemplate;

    public TransportationService(TransportationRepository transportationRepository, UserServiceClient userServiceClient, RabbitTemplate rabbitTemplate) {
        this.transportationRepository = transportationRepository;
        this.userServiceClient = userServiceClient;
        this.rabbitTemplate = rabbitTemplate;
    }


    public Transportation transport(Long idOfUser, String from, String to, Date dateOfShipment, Date dateOfUnloading, Double cost) {
        Transportation transportation = Transportation.builder()
                .from(from)
                .cost(cost)
                .dateOfShipment(dateOfShipment)
                .to(to)
                .dateOfUnloading(dateOfUnloading)
                .idOfUser(idOfUser)
                .build();

        return transportationRepository.save(transportation)
                .orElseThrow(() -> new SavingToDatabaseException("Ошибка сохранения перевозки"));
    }



    public List<UserResponseDTO> getAllUsers() {
        List<Long> idsOfUsers = new ArrayList<>();
        this.transportationRepository.findAll().forEach(e -> idsOfUsers.add(e.getTransportationId()));
        return idsOfUsers.stream().map(userServiceClient::getUserById
        ).collect(Collectors.toList());
    }


    public boolean setStateUnderReview(Long idTransportation) {
        int countOfUpdate = transportationRepository.updateStateById(idTransportation, Transportation.State.UNDER_REVIEW);
        return countOfUpdate == 1;
    }

    public boolean setStateCanceled(Long idTransportation) {
        int countOfUpdate = transportationRepository.updateStateById(idTransportation, Transportation.State.CANCELED);
        return countOfUpdate == 1;
    }

    public boolean setStateAccepted(Long idTransportation) {
        int countOfUpdate = transportationRepository.updateStateById(idTransportation, Transportation.State.ACCEPTED);
        Long idOfUser = transportationRepository.findUserIdByTransportationId(idTransportation);
        try {
            rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_TRANSPORTATION, ROUTING_KEY_TRANSPORTATION,
                    new ObjectMapper().writeValueAsString(userServiceClient.getUserById(idOfUser)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return countOfUpdate == 1;
    }


}
