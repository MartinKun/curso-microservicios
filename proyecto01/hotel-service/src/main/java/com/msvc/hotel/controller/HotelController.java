package com.msvc.hotel.controller;

import com.msvc.hotel.controller.request.HotelRequest;
import com.msvc.hotel.entity.Hotel;
import com.msvc.hotel.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<Hotel> saveHotel(
            @RequestBody HotelRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(hotelService.saveHotel(request));
    }

    @GetMapping
    public ResponseEntity<List<Hotel>> listHotels() {
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotel(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(hotelService.getHotel(id));
    }
}
