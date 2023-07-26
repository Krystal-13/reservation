package com.zerobase.reservation.reservation.service;

import com.zerobase.reservation.reservation.dto.ReservationDto;
import com.zerobase.reservation.restaurant.review.entity.Review;
import com.zerobase.reservation.user.dto.UserDto;

import java.util.List;

public interface ReservationService {
    ReservationDto reserve(Long restaurantId, ReservationDto request);

    List<ReservationDto> getMyReservation(UserDto request);

    ReservationDto checkIn(ReservationDto request);

    ReservationDto review(Review request);

    ReservationDto updateReservationState(ReservationDto request);
}
