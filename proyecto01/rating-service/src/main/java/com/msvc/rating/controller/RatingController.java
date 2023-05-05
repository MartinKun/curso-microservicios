package com.msvc.rating.controller;

import com.msvc.rating.controller.request.RatingRequest;
import com.msvc.rating.entity.Rating;
import com.msvc.rating.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    public ResponseEntity<Rating> saveRating(
            @RequestBody RatingRequest ratingRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ratingService.save(ratingRequest));
    }

    @GetMapping
    public ResponseEntity<List<Rating>> listAllRatings() {
        return ResponseEntity.ok(ratingService.getAllRatings());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<Rating>> listRatingsByUserId(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(ratingService.getAllRatingsByUserId(id));
    }

    @GetMapping("/hotels/{id}")
    public ResponseEntity<List<Rating>> listRatingsByHotelId(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(ratingService.getAllRatingsByHotelId(id));
    }
}
