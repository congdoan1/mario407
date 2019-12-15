package edu.cs544.mario477.service;

import edu.cs544.mario477.domain.Media;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {

    void upload(MultipartFile[] files, Long postId);

    Media upload(MultipartFile file, Long postId, int number) throws IOException;
}
