package edu.cs544.mario477.util;


import edu.cs544.mario477.domain.Post;
import edu.cs544.mario477.domain.User;
import edu.cs544.mario477.dto.MailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Component
public class EmailUtil {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(MailDTO dto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(dto.getSubject());
        message.setFrom(dto.getFrom());
        message.setTo(dto.getTo());
        message.setText(dto.getText());
        javaMailSender.send(message);
    }

    public void sendNotificationUnHealthyPostToAdmin(Post post) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("luis.thong.phan@mail.com");
        message.setTo(post.getOwner().getEmail());
        message.setSubject("Unhealthy Post");
        message.setText("User " + post.getOwner().getUsername() + " has unhealthy Post: " + post.getText());
        javaMailSender.send(message);
    }

    public void sendNotificationUnHealthyPostToUser(Post post){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("admin@mail.com");
        message.setTo(post.getOwner().getEmail());
        message.setSubject("Lock account");
        message.setText("Your account was locked by unhealthy posts. Contact admin for more information");
        javaMailSender.send(message);
    }

}
