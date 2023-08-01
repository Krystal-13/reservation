package com.zerobase.reservation.restaurant.controller;

import com.zerobase.reservation.restaurant.dto.RestaurantDto;
import com.zerobase.reservation.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/register")
    public ResponseEntity<?> register(Principal request) {

        boolean result = restaurantService.isPartner(Long.parseLong(request.getName()));

        return ResponseEntity.ok(result);
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerSubmit(@RequestBody RestaurantDto restaurantDto, Principal request) {

        RestaurantDto restaurant = restaurantService.register(restaurantDto, request.getName());

        return ResponseEntity.ok(restaurant);
    }
}
