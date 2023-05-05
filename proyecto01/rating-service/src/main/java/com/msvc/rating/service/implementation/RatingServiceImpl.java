package com.msvc.rating.service.implementation;

import com.msvc.rating.controller.request.RatingRequest;
import com.msvc.rating.entity.Rating;
import com.msvc.rating.repository.RatingRepository;
import com.msvc.rating.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    @Override
    public Rating save(RatingRequest request) {
        String id = UUID.randomUUID().toString();
        Rating rating = Rating.builder()
                .id(id)
                .userId(request.getUserId())
                .hotelId(request.getHotelId())
                .rating(request.getRating())
                .observations(request.getObservations())
                .build();
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public List<Rating> getAllRatingsByUserId(Long id) {
        return ratingRepository.findAllByUserId(id);
    }

    @Override
    public List<Rating> getAllRatingsByHotelId(Long id) {
        return ratingRepository.findAllByHotelId(id);
    }

}
