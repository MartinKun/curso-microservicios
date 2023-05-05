package com.msvc.user.service;

import com.msvc.user.controller.request.UserRequest;
import com.msvc.user.entity.User;

import java.util.List;

public interface UserService {

    User saveUser(UserRequest request);

    List<User> getAllUsers();

    User getUser(Long id);
}
