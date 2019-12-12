package edu.cs544.mario477.service.impl;

import edu.cs544.mario477.domain.Media;
import edu.cs544.mario477.domain.Photo;
import edu.cs544.mario477.exception.AppException;
import edu.cs544.mario477.service.StorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageServiceImpl implements StorageService {

    @Value("${media.upload.dir}")
    public String uploadDir;

    @Override
    public void upload(MultipartFile[] files, Long postId) {

    }

    @Override
    public Media upload(MultipartFile file, Long postId, int number) {
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        String path;
        try {
            path = uploadDir + File.separator + postId + "_" + number + "." + ext;
            Path copyLocation = Paths.get(path);
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new AppException("Failed to store file " + e);
        }
        Media media = new Photo();
        media.setUrl(path);
        media.setFileFormat(ext);
        return media;
    }
}
