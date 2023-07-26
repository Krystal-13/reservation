package com.zerobase.reservation.user.service;

import com.zerobase.reservation.user.entity.User;
import com.zerobase.reservation.user.dto.UserDto;
import com.zerobase.reservation.user.repository.UserRepository;
import com.zerobase.reservation.user.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
                .username(request.getName())
                .phone(request.getPhone())
                .roles(Role.USER)
                .build();
        userRepository.save(user);

        return UserDto.of(user);
    }

    @Override
    public UserDto authenticate(UserDto request) {
        // 인증키 발급이 되어야 (로그인이 되어야) 예약가능
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("error class 만들기!!!!!!!"));

        if (!this.passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            //todo throw new IncorrectPasswordException();
        }

        return UserDto.of(user);
    }
}
