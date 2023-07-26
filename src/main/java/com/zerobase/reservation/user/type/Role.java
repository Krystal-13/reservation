package com.zerobase.reservation.user.type;

import lombok.Getter;

@Getter
public enum Role {
    USER,
    MANAGER,
    ADMIN
//
//    private final String role;
//
//    Role(final String role) {
//        this.role = role;
//    }
    //("서비스 관리자")("매장 관리자")("일반 사용자")
}
