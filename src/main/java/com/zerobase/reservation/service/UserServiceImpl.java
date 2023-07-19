package com.zerobase.reservation.service;

import com.zerobase.reservation.domain.User;
import com.zerobase.reservation.dto.UserDto;
import com.zerobase.reservation.repository.UserRepository;
import com.zerobase.reservation.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDto register(UserDto request) {

        boolean requestUser = userRepository.existsByEmail(request.getEmail());

        if (requestUser) {
            // todo exception ( alread exist user )
        }

        String encPassword =
                BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        User user = User.builder()
                .email(request.getEmail())
                .password(encPassword)
                .name(request.getName())
                .phone(request.getPhone())
                .roles(Role.USER)
                .build();
        userRepository.save(user);

        return UserDto.of(user);
    }

    @Override
    public UserDto authenticate(UserDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("error class 만들기!!!!!!!"));

        if (!this.passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            //todo throw new IncorrectPasswordException();
        }

        return UserDto.of(user);
    }
}
