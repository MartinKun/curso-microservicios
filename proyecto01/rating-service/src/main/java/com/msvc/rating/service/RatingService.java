package com.msvc.rating.service;

import com.msvc.rating.controller.request.RatingRequest;
import com.msvc.rating.entity.Rating;

import java.util.List;

public interface RatingService {

    Rating save(RatingRequest request);

    List<Rating> getAllRatings();

    List<Rating> getAllRatingsByUserId(Long id);

    List<Rating> getAllRatingsByHotelId(Long id);

}
