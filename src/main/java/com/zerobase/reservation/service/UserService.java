package com.zerobase.reservation.service;

import com.zerobase.reservation.dto.UserDto;
import org.springframework.stereotype.Service;


public interface UserService {
    UserDto register(UserDto request);

    UserDto authenticate(UserDto request);

}
