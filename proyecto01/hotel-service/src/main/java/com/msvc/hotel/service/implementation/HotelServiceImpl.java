package com.msvc.hotel.service.implementation;

import com.msvc.hotel.controller.request.HotelRequest;
import com.msvc.hotel.entity.Hotel;
import com.msvc.hotel.exception.ResourceNotFoundException;
import com.msvc.hotel.repository.HotelRepository;
import com.msvc.hotel.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    @Override
    public Hotel saveHotel(HotelRequest request) {
        Hotel hotel = Hotel.builder()
                .name(request.getName())
                .location(request.getLocation())
                .information(request.getInformation())
                .build();
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getHotel(Long id) {
        return hotelRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("HOTEL not found with ID: %d", id)
                )
        );
    }
}
