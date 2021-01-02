package com.controller.service.serviceImpl;

import com.controller.entity.Description;
import com.controller.entity.User;
import com.controller.repo.UserRepository;
import com.controller.service.MediaService;
import com.controller.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MediaService mediaService;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByUsernameAndPassword(String username,String password) {
        return userRepository.findByUsernameAndPassword(username,password);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);

    }

    @Override
    @Transactional
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public User save(User user, MultipartFile file, String description) throws IOException {
        user.setDescription(new Description(description));
        if (file != null) {
            mediaService.uploadFile(file);
            user.setMedia(mediaService.createMedia(file));
        }
        user.setRole("USER_ROLE");
        return save(user);
    }
}
