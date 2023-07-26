package com.zerobase.reservation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND("사용자가 없습니다."),
    ALREADY_EXIST_RESTAURANT("이미 존재하는 음식점입니다."),
    DO_NOT_EXIST_RESTAURANT("존재하지 않는 음식점입니다."),
    DO_NOT_EXIST_RESERVATION("예약 정보가 존재하지 않습니다."),
    RESERVATION_TIME_OVER("예약 시간이 지났습니다."),
    DO_NOT_CHECKED_VISITED("방문 확인이 되지 않았습니다."),
    DO_NOT_CONFIRMED("예약확정이 되지 않았습니다."),
    USER_NOT_PARTNER("파트너 미가입 회원입니다."),
    INVALID_REQUEST("잘못된 요청입니다."),
    EXPIRED_TOKEN("인증 토큰이 만료되었습니다.");

    private final String description;
}
