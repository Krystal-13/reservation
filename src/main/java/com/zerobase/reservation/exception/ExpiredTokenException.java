package com.zerobase.reservation.exception;

import org.springframework.http.HttpStatus;

public class ExpiredTokenException extends AbstractException {
    @Override
    public int getStatusCode() {
        return HttpStatus.REQUEST_TIMEOUT.value();
    }

    @Override
    public String getMessage() {
        return "인증 토큰이 만료되었습니다.";
    }
}
