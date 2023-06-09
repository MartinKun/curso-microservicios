package com.msvc.user.controller.request;

import com.msvc.user.external.request.HotelRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    private String name;
    private String email;
    private String information;
    private HotelRequest hotel;
}
