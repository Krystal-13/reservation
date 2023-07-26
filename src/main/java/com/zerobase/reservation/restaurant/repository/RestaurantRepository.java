package com.zerobase.reservation.restaurant.repository;

import com.zerobase.reservation.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Optional<List<Restaurant>> findByRestaurantName(String restaurantName);

    Optional<List<Restaurant>> findByCategory(String category);
}
