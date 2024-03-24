package com.javastart.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String phone;
    @Column
    private String email;

    @ElementCollection
    private List<Long> transportations;

    public User(String name, String surname, String phone, String email){
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.surname = surname;
    }


}