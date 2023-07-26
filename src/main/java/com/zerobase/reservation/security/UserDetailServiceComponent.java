package com.zerobase.reservation.security;

import com.zerobase.reservation.user.entity.User;
import com.zerobase.reservation.user.repository.UserRepository;
import com.zerobase.reservation.user.type.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class UserDetailServiceComponent implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = this.userRepository.findByEmail(username)
                .orElseThrow(RuntimeException::new);

        log.info("Success find member {}", user);

        //todo no user exception
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(user.getRoles().toString())
                .build();
    }
}
