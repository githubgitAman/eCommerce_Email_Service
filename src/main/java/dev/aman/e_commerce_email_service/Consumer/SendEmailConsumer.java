package dev.aman.e_commerce_email_service.Consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.aman.e_commerce_email_service.DTOs.SendEmailDTOs;
import org.springframework.stereotype.Service;

@Service
public class SendEmailConsumer {

    ObjectMapper objectMapper;
    public SendEmailConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    //Converting our Message to object
    public void handleSendEmailEvent(String message) throws JsonProcessingException {

        SendEmailDTOs sendEmailDTOs = objectMapper.readValue(message,
                SendEmailDTOs.class);

        String to = sendEmailDTOs.getTo();
        String subject =sendEmailDTOs.getSubject();
        String body = sendEmailDTOs.getBody();
    }
}
