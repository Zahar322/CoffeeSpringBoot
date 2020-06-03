package com.controller.service;

import com.controller.entity.User;

public interface MessageService {

    void sendMessage(String email, String content, User user);
}
