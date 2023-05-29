package com.msvc.hotel.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/staffs")
public class StaffController {

    @GetMapping
    public ResponseEntity<List<String>> listStaffs() {
        List<String> staffs = Arrays.asList("Christian", "Raul", "Martin");
        return new ResponseEntity<>(staffs, HttpStatus.OK);
    }
}
