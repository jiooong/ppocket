package com.example.peeppo.global.stringCode;

import lombok.Getter;

@Getter
public enum SuccessCodeEnum {

    USER_SIGNUP_SUCCESS("회원가입 성공"),
    USER_LOGIN_SUCCESS("로그인 성공"),
    CHECK_EMAIL_SUCCESS("사용가능한 이메일입니다.");

    private final String message;

    SuccessCodeEnum(String message) {
        this.message = message;
    }
}