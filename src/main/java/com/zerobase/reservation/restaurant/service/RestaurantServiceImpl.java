package com.zerobase.reservation.restaurant.service;

import com.zerobase.reservation.exception.CustomException;
import com.zerobase.reservation.restaurant.menu.entity.Menu;
import com.zerobase.reservation.restaurant.menu.repository.MenuRepository;
import com.zerobase.reservation.restaurant.dto.RestaurantDto;
import com.zerobase.reservation.restaurant.entity.Restaurant;
import com.zerobase.reservation.restaurant.repository.RestaurantRepository;
import com.zerobase.reservation.user.entity.User;
import com.zerobase.reservation.user.repository.UserRepository;
import com.zerobase.reservation.user.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.zerobase.reservation.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService{

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    @Override
    public RestaurantDto register(RestaurantDto restaurantDto, String userEmail) {

        Optional<User> optionalUser = userRepository.findByEmail(userEmail);

        if (optionalUser.isEmpty()) {
            throw new CustomException(USER_NOT_FOUND);
        }

        User user = optionalUser.get();
        Optional<Restaurant> optionalRestaurant =
                        restaurantRepository.findById(user.getId());

        if (optionalRestaurant.isPresent()) {
            throw new CustomException(ALREADY_EXIST_RESTAURANT);
        }

        List<Menu> list = new ArrayList<>(restaurantDto.getMenulist());

        Restaurant restaurant = Restaurant.builder()
                .restaurantName(restaurantDto.getRestaurantName())
                .address(restaurantDto.getAddress())
                .contact(restaurantDto.getContact())
                .menulist(list)
                .open(restaurantDto.getOpen())
                .close(restaurantDto.getClose())
                .userId(restaurantDto.getUserId())
                .category(restaurantDto.getCategory())
                .build();
        restaurantRepository.save(restaurant);

        setMenu(list, restaurant.getId());

        return RestaurantDto.of(restaurant);
    }

    private void setMenu(List<Menu> menuList, Long restaurantId) {

        for (Menu m : menuList) {
            m.setRestaurantId(restaurantId);
            menuRepository.save(m);
        }
    }

    @Override
    public boolean isPartner(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if (!Role.MANAGER.equals(user.getRoles())) {
            throw new CustomException(USER_NOT_PARTNER);
        }

        return true;
    }

    @Override
    public Page<RestaurantDto> findAllRestaurant(Pageable pageable) {

        return restaurantRepository.findAll(pageable).map(RestaurantDto::of);
    }

    @Override
    public List<RestaurantDto> findByRestaurantName(String restaurantName) {

        Optional<List<Restaurant>> optionalRestaurants =
                restaurantRepository.findByRestaurantName(restaurantName);

        return optionalRestaurants.map(RestaurantDto::of).orElse(null);
    }

    @Override
    public List<RestaurantDto> findByCategory(String category) {

        Optional<List<Restaurant>> optionalRestaurants =
                restaurantRepository.findByCategory(category);

        return optionalRestaurants.map(RestaurantDto::of).orElse(null);
    }

    @Override
    public RestaurantDto getRestaurantDetail(Long restaurantId) {

        Optional<Restaurant> optionalRestaurant =
                restaurantRepository.findById(restaurantId);

        return optionalRestaurant.map(RestaurantDto::of).orElse(null);
    }
}
