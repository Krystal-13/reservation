package com.zerobase.reservation.reservation.repository;

import com.zerobase.reservation.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByUserEmail(String email);

    Optional<Reservation> findByUserEmailAndRestaurantIdAndVisitedTrue(String email, Long restaurantId);

    List<Reservation> findAllByRestaurantIdAndVisitedFalse(Long restaurantId);

    @Transactional
    void deleteAllByRestaurantId(Long restaurantId);
}
