package com.zerobase.reservation.user.service;

import com.zerobase.reservation.user.dto.UserDto;


public interface UserService {
    UserDto register(UserDto request);

    UserDto authenticate(UserDto request);

}
