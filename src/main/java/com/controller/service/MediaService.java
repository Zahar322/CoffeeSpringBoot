package com.controller.service;

import com.controller.entity.Media;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MediaService {

    void uploadFile(MultipartFile file) throws IOException;

    Media createMedia(MultipartFile file);

    byte[] getFile(String path) throws IOException;

}
