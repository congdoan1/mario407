package edu.cs544.mario477.notification;

import edu.cs544.mario477.domain.Post;

public interface Notification {
    void notifyNewPost(Post post);
    void notifyUnHealthyPost(Post post);
}
