package com.javastart.transportation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransportationRequestDTO {

    private Long userId;

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

    // состояние при инициализации сразу не рассмотрен не нужно здесь




}
