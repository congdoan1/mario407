package edu.cs544.mario477.notification.impl;

import edu.cs544.mario477.domain.Post;
import edu.cs544.mario477.domain.User;
import edu.cs544.mario477.enumerable.NotificationExchange;
import edu.cs544.mario477.enumerable.NotificationRoute;
import edu.cs544.mario477.notification.Notification;
import edu.cs544.mario477.repository.UserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.security.krb5.internal.tools.Klist;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NotificationImpl implements edu.cs544.mario477.notification.Notification {

    private RabbitTemplate rabbitTemplate;

    private UserRepository userRepository;

    @Autowired
    public NotificationImpl(RabbitTemplate rabbitTemplate, UserRepository userRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.userRepository = userRepository;
    }

    @Override
    public void notifyNewPost(Post post) {
        List<Long> followers = post.getOwner().getFollowers().stream().map(User::getId).collect(Collectors.toList());
        rabbitTemplate.convertAndSend(
                NotificationExchange.POST.getValue(),
                NotificationRoute.NEW_POST.getValue(),
                followers);

    }

    @Override
    public void notifyUnHealthyPost(Post post) {
        rabbitTemplate.convertAndSend(
                NotificationExchange.POST.getValue(),
                NotificationRoute.NEW_POST.getValue(),
                post.getId());
    }
}