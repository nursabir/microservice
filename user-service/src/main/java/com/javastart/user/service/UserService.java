package com.javastart.user.service;

import com.javastart.user.entity.User;
import com.javastart.user.exception.AccountNotFoundException;
import com.javastart.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(()-> new AccountNotFoundException("Account not found id: " + id));
    }

    public Long createUser(String name, String surname, String phone, String email){
        User newUser = new User(name, surname, phone, email);
        return userRepository.save(newUser).getUserId();
    }
}
