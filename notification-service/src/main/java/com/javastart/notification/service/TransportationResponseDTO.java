package com.javastart.notification.service;

import lombok.Builder;
import lombok.Data;


import java.util.Date;

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

    // пользвотель с каким эмайлом получил одобрение на транспортацию
    private String email;


}
