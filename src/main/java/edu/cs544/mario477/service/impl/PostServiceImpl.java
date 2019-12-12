package edu.cs544.mario477.service.impl;

import edu.cs544.mario477.common.Constants;
import edu.cs544.mario477.domain.Post;
import edu.cs544.mario477.domain.User;
import edu.cs544.mario477.repository.PostRepository;
import edu.cs544.mario477.repository.UserRepository;
import edu.cs544.mario477.service.PostService;
import edu.cs544.mario477.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Post> getPostByFollow(long id, int page) {
        Sort sort = Sort.by("postedDate");
        Pageable pageable = PageUtil.initPage(page, Constants.DEFAULT_SIZE, sort);
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return null;
        } else {
            System.out.println(user.get().getFollowings());
            return null;
//            return postRepository.findByOwnerIn()
        }
    }

    @Override
    public List<Post> getTimelineById(long id, int page) {
        return null;
    }
}
