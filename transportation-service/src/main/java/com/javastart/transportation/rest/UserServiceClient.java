package com.javastart.transportation.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @RequestMapping(value = "users/get/{userId}", method = RequestMethod.GET)
    UserResponseDTO getUserById(@PathVariable("userId") Long accountId);


}
