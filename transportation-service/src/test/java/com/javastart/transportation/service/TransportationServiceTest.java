package com.javastart.transportation.service;

import com.javastart.transportation.dto.TransportationResponseDTO;
import com.javastart.transportation.dto.TransportationResponseNotificationDTO;
import com.javastart.transportation.entity.Transportation;
import com.javastart.transportation.repository.TransportationRepository;
import com.javastart.transportation.rest.UserResponseDTO;
import com.javastart.transportation.rest.UserServiceClient;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class TransportationServiceTest {

    @Mock
    private TransportationRepository transportationRepository;

    @Mock
    private UserServiceClient userServiceClient;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private TransportationService transportationService;

    @Test
    public void transportationServiceTest_withTransportationId(){


        Mockito.when(userServiceClient.getUserById(ArgumentMatchers.anyLong())).thenReturn(createUserResponseDTO());
        Mockito.when(transportationRepository.updateStateById(ArgumentMatchers.anyLong(), Transportation.State.ACCEPTED)).thenReturn(1);
        Mockito.when(transportationRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(createTransportation()));


        TransportationResponseNotificationDTO transportationResponseNotificationDTO = transportationService.setStateAccepted(1L);

        Assertions.assertThat(transportationResponseNotificationDTO.getEmail()).isEqualTo("fikus710828@gmail.com");

    }



    private UserResponseDTO createUserResponseDTO(){
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setEmail("fikus710828@gmail.com");
        userResponseDTO.setName("df");
        userResponseDTO.setSurname("dfdfdf");
        userResponseDTO.setPhone("8912313");
        return userResponseDTO;
    }

    private Transportation createTransportation(){
         return Transportation.builder().idOfUser(1L).build();
    }
}
