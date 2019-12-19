package edu.cs544.mario477.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import edu.cs544.mario477.domain.Media;
import edu.cs544.mario477.domain.Photo;
import edu.cs544.mario477.domain.Video;
import edu.cs544.mario477.exception.AppException;
import edu.cs544.mario477.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private Cloudinary cloudinary;

    @Value("${cloudinary.folder}")
    private String folder;

    @Override
    public void upload(MultipartFile[] files, Long postId) {
        //TODO
    }

    @Override
    public Media upload(MultipartFile file, Long postId, int number) throws IOException {
        String mimeType = file.getContentType();
        Map uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap(
                        "public_id", postId + "_" + number,
                        "folder", folder + postId,
                        "resource_type", mimeType.split("/")[0]
                ));
        if (uploadResult == null) {
            throw new AppException("Error in uploading media file(s)");
        }
        Media media;
        if (uploadResult.get("resource_type").toString().equals("image")) {
            media = new Photo(postId + "_" + number, uploadResult.get("url").toString(), uploadResult.get("format").toString());
        } else if (uploadResult.get("resource_type").toString().equals("video")) {
            media = new Video(postId + "_" + number, uploadResult.get("url").toString(), uploadResult.get("format").toString(), 0);
        } else {
            media = new Photo(postId + "_" + number, uploadResult.get("url").toString(), uploadResult.get("format").toString());
        }
        return media;
    }

    @Override
    public String upload(MultipartFile file, Long userId) {
        try {
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "public_id", "avatar_" + userId,
                            "folder", folder + "avatar"
                    ));
            if (uploadResult == null) {
                throw new AppException("Error in uploading media file(s)");
            }
            return uploadResult.get("url").toString();
        } catch (IOException e) {
            throw new AppException(e.getLocalizedMessage());
        }
    }
}
