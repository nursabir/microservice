package com.javastart.transportation.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@ToString
@Builder
@Getter
@AllArgsConstructor
public class Transportation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transportationId;

    // пункт погрузки
    @Column(name = "from_location")
    private String from;

    // пункт выгрузки
    @Column(name = "to_location")
    private String to;

    // кто заказал услугу
    @Column
    private Long idOfUser;

    // дата погрузки
    @Column
    private Date dateOfShipment;

    // дата выгрузки
    @Column
    private Date dateOfUnloading;

    @Column
    private Double cost;

    // статус погрузки
    @Enumerated
    private State state;

    public enum State{
        NOT_REVIEWED, UNDER_REVIEW, CANCELED, ACCEPTED
    }
}
