package com.zerobase.reservation.reservation.controller;

import com.zerobase.reservation.reservation.dto.ReservationDto;
import com.zerobase.reservation.reservation.service.ReservationService;
import com.zerobase.reservation.restaurant.dto.RestaurantDto;
import com.zerobase.reservation.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final RestaurantService restaurantService;
    private final ReservationService reservationService;

    @GetMapping("/list")
    public ResponseEntity<?> searchAll(final Pageable pageable) {

        Page<RestaurantDto> restaurants = restaurantService.findAllRestaurant(pageable);

        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/search/{restaurantName}")
    public ResponseEntity<?> searchByRestaurantName(@PathVariable String restaurantName) {

        List<RestaurantDto> restaurants = restaurantService.findByRestaurantName(restaurantName);

        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchByCategory(@RequestParam String category) {

        List<RestaurantDto> restaurants = restaurantService.findByCategory(category);

        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/detail/{restaurantId}")
    public ResponseEntity<?> detail(@PathVariable Long restaurantId) {

        RestaurantDto restaurant = restaurantService.getRestaurantDetail(restaurantId);

        return ResponseEntity.ok(restaurant);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/detail/{restaurantId}")
    public ResponseEntity<?> reserve(@PathVariable Long restaurantId, @RequestBody ReservationDto request) {

        ReservationDto reservation = reservationService.reserve(restaurantId, request);

        return ResponseEntity.ok(reservation);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/manage")
    public ResponseEntity<?> updateState(@RequestBody ReservationDto request) {

        ReservationDto reservation = reservationService.updateReservationState(request);

        return ResponseEntity.ok(reservation);
    }
}
