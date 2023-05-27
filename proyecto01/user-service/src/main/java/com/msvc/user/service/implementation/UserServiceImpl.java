package com.msvc.user.service.implementation;

import com.msvc.user.UserRepository;
import com.msvc.user.controller.request.UserRequest;
import com.msvc.user.entity.Hotel;
import com.msvc.user.entity.Rating;
import com.msvc.user.entity.User;
import com.msvc.user.exception.ResourceNotFoundException;
import com.msvc.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    private final RestTemplate restTemplate;
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
        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                String.format("USER not found with ID: %d", id)
                        )
                );
        ArrayList<Rating> userRatings = restTemplate.exchange(
                "http://RATING-SERVICE/api/v1/ratings/users/" + user.getId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ArrayList<Rating>>() {
                }
        ).getBody();

        List<Rating> ratings = userRatings.stream()
                .map(rating -> {
                    ResponseEntity<Hotel> forEntity = restTemplate.getForEntity(
                            "http://HOTEL-SERVICE/api/v1/hotels/" + rating.getHotelId(),
                            Hotel.class
                    );
                    Hotel hotel = forEntity.getBody();
                    rating.setHotel(hotel);
                    return rating;
                }).collect(Collectors.toList());

        user.setRatings(ratings);
        return user;
    }
}
