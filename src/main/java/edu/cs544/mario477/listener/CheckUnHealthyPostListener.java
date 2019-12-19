package edu.cs544.mario477.listener;

import edu.cs544.mario477.domain.Post;
import edu.cs544.mario477.domain.User;
import edu.cs544.mario477.repository.PostRepository;
import edu.cs544.mario477.repository.UserRepository;
import edu.cs544.mario477.util.EmailUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = {"UNHEALTHY_POST"})
public class CheckUnHealthyPostListener {

    private PostRepository postRepository;

    private UserRepository userRepository;

    private EmailUtil emailUtil;


    @Autowired
    public CheckUnHealthyPostListener(PostRepository postRepository,
                                      EmailUtil emailUtil,
                                      UserRepository userRepository) {
        this.postRepository = postRepository;
        this.emailUtil = emailUtil;
        this.userRepository = userRepository;
    }

    @RabbitHandler
    public void receiveMessage(Long postId) {

        Post post = postRepository.findById(postId).orElse(null);

        if (post != null) {
            if (postRepository.checkHealthyPost(post.getText()) > 0) {
                post.setHealthy(false);
                postRepository.saveAndFlush(post);
                emailUtil.sendNotificationUnHealthyPostToAdmin(post);

                User user = post.getOwner();
                if (postRepository.countUnhealthyPost(user.getId()) >= 20) {
                    user.setEnabled(false);
                    userRepository.saveAndFlush(user);
//                    emailUtil.sendNotificationUnHealthyPostToUser(post);
                }

            }
        }
    }

}
