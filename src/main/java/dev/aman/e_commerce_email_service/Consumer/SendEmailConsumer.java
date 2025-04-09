package dev.aman.e_commerce_email_service.Consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.aman.e_commerce_email_service.DTOs.SendEmailDTOs;
import dev.aman.e_commerce_email_service.Utils.EmailUtil;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;


@Service
public class SendEmailConsumer {

    ObjectMapper objectMapper;
    public SendEmailConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    //Converting our Message to object
    @KafkaListener(topics = "sendEmail", groupId = "emailService")
    public void handleSendEmailEvent(String message) throws JsonProcessingException {

        SendEmailDTOs sendEmailDTOs = objectMapper.readValue(message,
                SendEmailDTOs.class);

        String to = sendEmailDTOs.getTo();
        String subject =sendEmailDTOs.getSubject();
        String body = sendEmailDTOs.getBody();

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("sharma.aman.a30@gmail.com"
                        , "bmmtovxclofdgxvq");
            }
        };
        Session session = Session.getInstance(props, auth);

        EmailUtil.sendEmail(session, to,subject, body);

    }
}
