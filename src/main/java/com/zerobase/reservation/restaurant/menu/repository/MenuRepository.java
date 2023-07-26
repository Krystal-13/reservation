package com.zerobase.reservation.restaurant.menu.repository;

import com.zerobase.reservation.restaurant.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByRestaurantId(Long RestaurantId);
}
