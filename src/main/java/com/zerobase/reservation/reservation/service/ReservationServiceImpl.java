package com.zerobase.reservation.reservation.service;

import com.zerobase.reservation.exception.CustomException;
import com.zerobase.reservation.exception.ErrorCode;
import com.zerobase.reservation.reservation.dto.ReservationDto;
import com.zerobase.reservation.reservation.entity.Reservation;
import com.zerobase.reservation.reservation.repository.ReservationRepository;
import com.zerobase.reservation.reservation.type.State;
import com.zerobase.reservation.restaurant.entity.Restaurant;
import com.zerobase.reservation.restaurant.repository.RestaurantRepository;
import com.zerobase.reservation.restaurant.review.entity.Review;
import com.zerobase.reservation.restaurant.review.repository.ReviewRepository;
import com.zerobase.reservation.user.dto.UserDto;
import com.zerobase.reservation.user.entity.User;
import com.zerobase.reservation.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService{

    private final ReservationRepository reservationRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public ReservationDto reserve(Long restaurantId, ReservationDto request) {

        Reservation reservation = Reservation.builder()
                .date(request.getDate())
                .time(request.getTime())
                .num(request.getNum())
                .userName(request.getUserName())
                .userEmail(request.getUserEmail())
                .phone(request.getPhone())
                .specialRequest(request.getSpecialRequest())
                .restaurantId(restaurantId)
                .userId(request.getUserId())
                .visited(false)
                .state(State.REQUEST)
                .build();
        reservationRepository.save(reservation);

        return ReservationDto.of(reservation);
    }

    @Override
    public List<ReservationDto> getMyReservation(UserDto request) {

        return reservationRepository.findAllByUserId(request.getId());
    }

    @Override
    public ReservationDto checkIn(ReservationDto request) {

        Optional<Reservation> optionalReservation =
                reservationRepository.findById(request.getId());

        if (optionalReservation.isEmpty()) {
            throw new CustomException(ErrorCode.DO_NOT_EXIST_RESERVATION);
        }

        Reservation reservation = optionalReservation.get();

        if (!reservation.getState().equals(State.CONFIRM)) {
            throw new CustomException(ErrorCode.DO_NOT_CONFIRMED);
        }

        if (reservation.getTime().minusMinutes(10).isBefore(LocalTime.now())) {
            throw new CustomException(ErrorCode.RESERVATION_TIME_OVER);
        }

        reservation.setVisited(true);
        reservationRepository.save(reservation);

        return ReservationDto.of(reservation);
    }

    @Override
    public ReservationDto review(Review request) {

        Optional<Reservation> optionalReservation =
                reservationRepository.findByUserIdAndRestaurantIdAndVisitedTrue(
                                            request.getUserId(), request.getRestaurantId());

        if (optionalReservation.isEmpty()) {
            throw new CustomException(ErrorCode.DO_NOT_EXIST_RESERVATION);
        }

        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(request.getRestaurantId());

        if (optionalRestaurant.isEmpty()) {
            throw new CustomException(ErrorCode.DO_NOT_EXIST_RESTAURANT);
        }

        Optional<User> optionalUser = userRepository.findById(request.getUserId());

        if (optionalUser.isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        User user = optionalUser.get();

        Review review = Review.builder()
                .userId(request.getId())
                .username(user.getUsername())
                .regDt(LocalDateTime.now())
                .restaurantId(request.getRestaurantId())
                .review(request.getReview())
                .build();
        reviewRepository.save(review);

        Restaurant restaurant = optionalRestaurant.get();
        restaurant.getReviews().add(review);
        restaurantRepository.save(restaurant);

        return ReservationDto.of(optionalReservation.get());
    }

    @Override
    public ReservationDto updateReservationState(ReservationDto request) {

        Optional<Reservation> optionalReservation = reservationRepository.findById(request.getId());

        if (optionalReservation.isEmpty()) {
            throw new CustomException(ErrorCode.DO_NOT_EXIST_RESERVATION);
        }

        Reservation reservation = optionalReservation.get();
        reservation.setState(request.getState());
        reservationRepository.save(reservation);

        return ReservationDto.of(reservation);
    }
}
