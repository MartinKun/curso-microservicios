package com.msvc.rating.repository;

import com.msvc.rating.entity.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RatingRepository extends MongoRepository<Rating,String> {

    List<Rating> findAllByUserId(Long id);

    List<Rating> findAllByHotelId(Long id);
}
