package edu.cs544.mario477.util;


import edu.cs544.mario477.dto.MailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Component
public class EmailUtil {
    @Autowired
    private JavaMailSender javaMailSender;

    public  void sendMail(MailDTO dto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(dto.getFrom());
        message.setTo(dto.getTo());
        message.setText(dto.getText());
        javaMailSender.send(message);
    }
}
