package com.zerobase.reservation.restaurant.dto;

import com.zerobase.reservation.restaurant.entity.Restaurant;
import com.zerobase.reservation.restaurant.review.entity.Review;
import lombok.*;
import com.zerobase.reservation.restaurant.menu.entity.Menu;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDto {

    private Long id;
    private String restaurantName;
    private String address;
    private String contact;
    private List<Menu> menulist;

    @DateTimeFormat(pattern = "hh:mm")
    private LocalTime open;
    @DateTimeFormat(pattern = "hh:mm")
    private LocalTime close;

    private Long userId;
    private List<Review> reviews;

    private String category;

    public static RestaurantDto of(Restaurant restaurant) {
        return RestaurantDto.builder()
                .restaurantName(restaurant.getRestaurantName())
                .address(restaurant.getAddress())
                .contact(restaurant.getContact())
                .menulist(restaurant.getMenulist())
                .open(restaurant.getOpen())
                .close(restaurant.getClose())
                .userId(restaurant.getUserId())
                .reviews(restaurant.getReviews())
                .category(restaurant.getCategory())
                .build();
    }

    public static List<RestaurantDto> of(List<Restaurant> restaurants) {

        if (restaurants == null) {
            return null;
        }

        List<RestaurantDto> restaurantDtoList = new ArrayList<>();
        for(Restaurant x : restaurants) {
            restaurantDtoList.add(RestaurantDto.of(x));
        }
        return restaurantDtoList;

    }

}
