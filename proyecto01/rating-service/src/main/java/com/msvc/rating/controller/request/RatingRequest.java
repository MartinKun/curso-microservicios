package com.msvc.rating.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingRequest {

    private Long userId;
    private Long hotelId;
    private int rating;
    private String observations;
}
