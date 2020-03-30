package edu.cs544.mario477.notification.impl;

import edu.cs544.mario477.domain.Post;
import edu.cs544.mario477.domain.User;
import edu.cs544.mario477.enumerable.NotificationExchange;
import edu.cs544.mario477.enumerable.NotificationRoute;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NotificationImpl implements edu.cs544.mario477.notification.Notification {

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public NotificationImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void notifyNewPost(Post post) {
        List<Long> followers = post.getOwner().getFollowers().stream().map(User::getId).collect(Collectors.toList());
        rabbitTemplate.convertAndSend(
                NotificationExchange.POST.getValue(),
                NotificationRoute.NEW_POST.getValue(),
                followers);

        rabbitTemplate.convertAndSend(
                NotificationExchange.POST.getValue(),
                NotificationRoute.CHECK_UNHEALTHY_POST.getValue(),
                post.getId());

    }

    @Override
    public void notifyUnHealthyPost(Post post) {

    }
}