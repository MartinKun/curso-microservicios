package com.msvc.hotel.service;

import com.msvc.hotel.controller.request.HotelRequest;
import com.msvc.hotel.entity.Hotel;

import java.util.List;

public interface HotelService {

    Hotel saveHotel(HotelRequest request);

    List<Hotel> getAllHotels();

    Hotel getHotel(Long id);
}
