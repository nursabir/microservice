package com.javastart.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor@ToString
public class UserRequestDTO {
    private String name;
    private String surname;
    private String email;
    private String phone;
}
