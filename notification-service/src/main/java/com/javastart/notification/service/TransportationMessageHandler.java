package com.javastart.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javastart.notification.config.RabbitMQConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class TransportationMessageHandler {

    private final JavaMailSender javaMailSender;

    @Autowired
    public TransportationMessageHandler(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_TRANSPORTATION)
    public void receive(Message message) throws JsonProcessingException {
        System.out.println(message);
        byte[] body = message.getBody();
        String jsonBody = new String(body);
        ObjectMapper objectMapper = new ObjectMapper();
        TransportationResponseDTO transportationResponseDTO = objectMapper.readValue(jsonBody, TransportationResponseDTO.class);
        System.out.println(transportationResponseDTO);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(transportationResponseDTO.getEmail());
        mailMessage.setFrom("lori@cat.xyz");

        mailMessage.setSubject("Transportation");

        mailMessage.setText("Ваша перевозка от " + transportationResponseDTO.getTransportationTo() + " до " + transportationResponseDTO.getTransportationTo() + " одобрена!!!");

        try {
            javaMailSender.send(mailMessage);
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
