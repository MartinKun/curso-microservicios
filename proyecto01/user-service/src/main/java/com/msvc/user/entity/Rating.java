package com.msvc.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rating {

    private String id;

    private Long userId;

    private Long hotelId;

    private int rating;

    private String observations;

    private Hotel hotel;
}
