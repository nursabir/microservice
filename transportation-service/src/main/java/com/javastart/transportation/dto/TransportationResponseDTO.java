package com.javastart.transportation.dto;


import com.javastart.transportation.entity.Transportation;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

// здесь мы просто скопировали класс в дальнейшем надо будет все dtoшки и прочий код где он копируется вывести в мавен зависимости и использовать оттуда для каждого сервиса
@Data
@Builder
public class TransportationResponseDTO {
    // откуда транспортация
    private String transportationFrom;

    // куда
    private String transportationTo;

    // стоимость
    private Double cost;

    // дата погрузки
    private Date dateOfShipment;

    // дата выгрузки
    private Date dateOfUnloading;

    // состояние
    private String transportationState;

    private String email;

    public static TransportationResponseDTO from(Transportation transportation){
        return TransportationResponseDTO.builder()
                .cost(transportation.getCost())
                .dateOfShipment(transportation.getDateOfShipment())
                .dateOfUnloading(transportation.getDateOfUnloading())
                .transportationState(transportation.getState().toString())
                .transportationFrom(transportation.getFrom())
                .transportationTo(transportation.getTo())
                .build();
    }

    public static List<TransportationResponseDTO> from(List<Transportation> transportation){
        return transportation.stream().map(TransportationResponseDTO::from).collect(Collectors.toList());
    }
}
