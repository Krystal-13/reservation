package com.zerobase.reservation.user.controller;

import com.zerobase.reservation.reservation.dto.ReservationDto;
import com.zerobase.reservation.reservation.service.ReservationService;
import com.zerobase.reservation.restaurant.review.entity.Review;
import com.zerobase.reservation.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/about")
@RequiredArgsConstructor
public class UserController {

    private final ReservationService reservationService;

    @PostMapping("/my")
    public ResponseEntity<?> myReservation(@RequestBody UserDto request) {

        List<ReservationDto> myReservation = reservationService.getMyReservation(request);

        return ResponseEntity.ok(myReservation);
    }

    @PostMapping("/my/check")
    public ResponseEntity<?> checkIn(@RequestBody ReservationDto request) {

        ReservationDto myReservation = reservationService.checkIn(request);

        return ResponseEntity.ok(myReservation);
    }

    @PostMapping("/my/review")
    public ResponseEntity<?> review(@RequestBody Review request) {

        ReservationDto myReservation = reservationService.review(request);

        return ResponseEntity.ok(myReservation);
    }

}
