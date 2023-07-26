package com.zerobase.reservation.restaurant.entity;

import com.zerobase.reservation.restaurant.menu.entity.Menu;
import com.zerobase.reservation.restaurant.review.entity.Review;
import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "restaurant")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String restaurantName;
    private String address;

    private String contact;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Menu> menulist;

    private LocalTime open;
    private LocalTime close;

    private Long userId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Review> reviews;

    private String category;

}
