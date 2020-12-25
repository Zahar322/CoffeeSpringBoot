package com.controller.service.serviceImpl;

import com.controller.entity.Media;
import com.controller.service.MediaService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class MediaServiceImpl implements MediaService {

    private final String UPLOAD_FOLDER = "D:/Aspringframework/coffee-media/";

    @Override
    public void uploadFile(MultipartFile file) throws IOException {
        byte[] data = file.getBytes();
        Path path = Paths.get(UPLOAD_FOLDER + file.getOriginalFilename());
        Files.write(path, data);
    }

    @Override
    public Media createMedia(MultipartFile file) {
        Media media = new Media();
        media.setContentType(file.getContentType());
        media.setName(file.getName());
        media.setOriginalName(file.getOriginalFilename());
        return media;
    }

    @Override
    public byte[] getFile(String path) throws IOException {
        File file = new File(UPLOAD_FOLDER + path);
        return FileUtils.readFileToByteArray(file);
    }
}
