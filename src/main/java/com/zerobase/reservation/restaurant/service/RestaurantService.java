package com.zerobase.reservation.restaurant.service;

import com.zerobase.reservation.restaurant.dto.RestaurantDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RestaurantService {

    RestaurantDto register(RestaurantDto restaurantDto, String userEmail);

    boolean isPartner(Long userId);

    Page<RestaurantDto> findAllRestaurant(Pageable pageable);

    List<RestaurantDto> findByRestaurantName(String restaurantName);

    List<RestaurantDto> findByCategory(String category);

    RestaurantDto getRestaurantDetail(Long restaurantId);
}
