package com.zerobase.reservation.restaurant.service;

import com.zerobase.reservation.exception.CustomException;
import com.zerobase.reservation.reservation.entity.Reservation;
import com.zerobase.reservation.reservation.repository.ReservationRepository;
import com.zerobase.reservation.restaurant.entity.Menu;
import com.zerobase.reservation.restaurant.entity.type.Category;
import com.zerobase.reservation.restaurant.repository.MenuRepository;
import com.zerobase.reservation.restaurant.dto.RestaurantDto;
import com.zerobase.reservation.restaurant.entity.Restaurant;
import com.zerobase.reservation.restaurant.repository.RestaurantRepository;
import com.zerobase.reservation.restaurant.repository.ReviewRepository;
import com.zerobase.reservation.user.entity.User;
import com.zerobase.reservation.user.repository.UserRepository;
import com.zerobase.reservation.user.entity.type.Role;
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
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final ReviewRepository reviewRepository;


    /**
     * 레스토랑 등록, MANAGER 권한만 등록 가능, 레스토랑 주소로 중복 체크
     */
    @Override
    public RestaurantDto register(RestaurantDto restaurantDto, String userEmail) {

        Optional<User> optionalUser = userRepository.findByEmail(userEmail);

        if (optionalUser.isEmpty()) {
            throw new CustomException(USER_NOT_FOUND);
        }

        User user = optionalUser.get();
        if (!user.getRoles().equals(Role.MANAGER)) {
            throw new CustomException(USER_NOT_PARTNER);
        }

        Optional<Restaurant> optionalRestaurant =
                        restaurantRepository.findByAddress(restaurantDto.getAddress());

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
                .userId(user.getId())
                .category(Category.getCategory(restaurantDto.getCategory()))
                .build();
        restaurantRepository.save(restaurant);

        setMenu(list, restaurant.getId());

        return RestaurantDto.of(restaurant);
    }

    /**
     * menu DB 에 menu - restaurantId 매핑하여 저장
     */
    private void setMenu(List<Menu> menuList, Long restaurantId) {

        for (Menu m : menuList) {
            m.setRestaurantId(restaurantId);
            menuRepository.save(m);
        }
    }

    /**
     * User 가 Partner 인지 검증
     */
    @Override
    public boolean isPartner(String email) {

        User user = userRepository.findByEmail(email)
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


    /**
     * 레스토랑 정보 수정
     */
    @Override
    public RestaurantDto modify(RestaurantDto restaurantDto) {

        Optional<Restaurant> optionalRestaurant =
                restaurantRepository.findById(restaurantDto.getId());

        if (optionalRestaurant.isEmpty()) {
            throw new CustomException(DO_NOT_EXIST_RESTAURANT);
        }

        Restaurant restaurantDetail = optionalRestaurant.get();
        List<Menu> list = new ArrayList<>(restaurantDto.getMenulist());

        restaurantDetail.setRestaurantName(restaurantDto.getRestaurantName());
        restaurantDetail.setAddress(restaurantDto.getAddress());
        restaurantDetail.setContact(restaurantDto.getContact());
        restaurantDetail.setMenulist(list);
        restaurantDetail.setOpen(restaurantDto.getOpen());
        restaurantDetail.setClose(restaurantDto.getClose());
        restaurantDetail.setUserId(restaurantDto.getUserId());
        restaurantDetail.setCategory(Category.getCategory(restaurantDto.getCategory()));
        restaurantRepository.save(restaurantDetail);

        setMenu(list, restaurantDetail.getId());

        return RestaurantDto.of(restaurantDetail);
    }


    /**
     * 레스토랑 삭제, 아직 방문하지 않은 예약확정건이 있으면 삭제 불가
     */
    @Override
    public boolean delete(Long restaurantId) {

        Optional<Restaurant> optionalRestaurant =
                restaurantRepository.findById(restaurantId);

        if (optionalRestaurant.isEmpty()) {
            throw new CustomException(DO_NOT_EXIST_RESTAURANT);
        }

        List<Reservation> reservationList = reservationRepository.findAllByRestaurantIdAndVisitedFalse(restaurantId);

        if (reservationList.size() > 0) {
            throw new CustomException(EXIST_RESERVATION);
        }

        reservationRepository.deleteAllByRestaurantId(restaurantId);
        reviewRepository.deleteAllByRestaurantId(restaurantId);
        restaurantRepository.deleteById(restaurantId);

        return true;
    }
}
