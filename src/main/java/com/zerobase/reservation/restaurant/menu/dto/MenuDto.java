package com.zerobase.reservation.restaurant.menu.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto {

    private Long id;

    private String item;

    private Long restaurantId;

}
