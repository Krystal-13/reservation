package com.zerobase.reservation.dto;

import com.zerobase.reservation.domain.User;
import com.zerobase.reservation.type.Role;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private String password;
    private String phone;
    private Role roles;

    public static UserDto of (User user) {

        return UserDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .phone(user.getPhone())
                .roles(user.getRoles())
                .build();
    }
}
