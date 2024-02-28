package com.javastart.transportation.controller;

import com.javastart.transportation.dto.TransportationRequestDTO;
import com.javastart.transportation.dto.TransportationResponseDTO;
import com.javastart.transportation.rest.UserResponseDTO;
import com.javastart.transportation.rest.UserServiceClient;
import com.javastart.transportation.service.TransportationService;
import jakarta.ws.rs.core.MediaType;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransportationController {

    private final TransportationService transportationService;

   // private final UserServiceClient userServiceClient;

    @Autowired
    public TransportationController(TransportationService transportationService, UserServiceClient userServiceClient) {
        this.transportationService = transportationService;
      //  this.userServiceClient = userServiceClient;
    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON)
    public TransportationResponseDTO transportationSave(@RequestBody TransportationRequestDTO transportationRequestDTO) {

        return TransportationResponseDTO.from(transportationService.transport(transportationRequestDTO.getUserId(),
                transportationRequestDTO.getTransportationFrom(),
                transportationRequestDTO.getTransportationTo(),
                transportationRequestDTO.getDateOfShipment(),
                transportationRequestDTO.getDateOfUnloading(),
                transportationRequestDTO.getCost()));
    }

    // все пользователи у которых есть перевозки
    @GetMapping(value = "/get/allUsers", produces = MediaType.APPLICATION_JSON)
    public List<UserResponseDTO> getUsers(){
        return transportationService.getAllUsers();
    }


    @PatchMapping(value = "/{idTransportation}/setState/under_review")
    public boolean setTransportationStateUnderReview(@PathVariable Long idTransportation) {
        try {
            transportationService.setStateUnderReview(idTransportation);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @PatchMapping(value = "/{idTransportation}/setState/canceled")
    public boolean setTransportationStateCanceled(@PathVariable Long idTransportation) {
        try {
            transportationService.setStateCanceled(idTransportation);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @PatchMapping(value = "/{idTransportation}/setState/accepted")
    public boolean setTransportationStateAccepted(@PathVariable Long idTransportation) {
        try {
            transportationService.setStateAccepted(idTransportation);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
