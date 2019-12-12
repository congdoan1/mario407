package edu.cs544.mario477.service.impl;

import edu.cs544.mario477.dto.PostDTO;
import edu.cs544.mario477.service.AdminService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Override
    public List<PostDTO> filterPostByKeyword() {
        return null;
    }

    @Override
    public boolean setPostStatus(Long postId, boolean status) {
        return false;
    }

    @Override
    public boolean setUserStatus(Long userId, boolean status) {
        return false;
    }
}
