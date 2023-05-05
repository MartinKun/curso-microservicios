package com.msvc.user.service.implementation;

import com.msvc.user.UserRepository;
import com.msvc.user.controller.request.UserRequest;
import com.msvc.user.entity.User;
import com.msvc.user.exception.ResourceNotFoundException;
import com.msvc.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User saveUser(UserRequest request) {
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .information(request.getInformation())
                .build();
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                String.format("USER not found with ID: %d", id)
                        )
                );
    }
}
