package com.javastart.user.dto;

import com.javastart.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    private String name;
    private String surname;
    private String email;
    private String phone;

    public static UserResponseDTO to(User user) {
        return UserResponseDTO.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }

    public static List<UserResponseDTO> to(List<User> users) {
        return users.stream().map(UserResponseDTO::to).collect(Collectors.toList());
    }

}
