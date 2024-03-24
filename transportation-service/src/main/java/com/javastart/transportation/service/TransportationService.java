package com.javastart.transportation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javastart.transportation.dto.TransportationResponseNotificationDTO;
import com.javastart.transportation.entity.Transportation;
import com.javastart.transportation.rest.UserResponseDTO;
import com.javastart.transportation.rest.UserServiceClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import com.javastart.transportation.repository.TransportationRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
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


    public Transportation saveTransportation(Long idOfUser, String from, String to, Date dateOfShipment, Date dateOfUnloading, Double cost) {
        Transportation transportation = Transportation.builder()
                .from(from)
                .cost(cost)
                .dateOfShipment(dateOfShipment)
                .to(to)
                .dateOfUnloading(dateOfUnloading)
                .idOfUser(idOfUser)
                .state(Transportation.State.NOT_REVIEWED)
                .build();

        return transportationRepository.save(transportation);
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

    public TransportationResponseNotificationDTO setStateAccepted(Long idTransportation) {

        int countOfUpdate = transportationRepository.updateStateById(idTransportation, Transportation.State.ACCEPTED);
        if(countOfUpdate>=1) {
            Transportation transportation = transportationRepository.findById(idTransportation).get();
            Long idOfUser = transportation.getIdOfUser();
            UserResponseDTO userResponseDTO = userServiceClient.getUserById(idOfUser);

            String emailUser = userResponseDTO.getEmail();

            return createResponse(transportation, emailUser);
        } else {
            throw new NoSuchElementException();
        }
    }

    private TransportationResponseNotificationDTO createResponse(Transportation transportation, String email){
        TransportationResponseNotificationDTO transportationResponseNotificationDTO = TransportationResponseNotificationDTO.builder()
                .cost(transportation.getCost())
                .transportationTo(transportation.getTo())
                .transportationFrom(transportation.getFrom())
                .dateOfShipment(transportation.getDateOfShipment())
                .dateOfUnloading(transportation.getDateOfUnloading())
                .email(email)
                .build();

        try {
            rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_TRANSPORTATION, ROUTING_KEY_TRANSPORTATION, new ObjectMapper().writeValueAsString(transportationResponseNotificationDTO));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return transportationResponseNotificationDTO;
    }






}
