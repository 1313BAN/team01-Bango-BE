package com.ssafy.ssafyhome.global.exception.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessType {

    /**
     * 200 OK
     */
    PROCESS_SUCCESS(HttpStatus.OK, "OK"),
    PRESIGNED_URL_SUCCESS(HttpStatus.OK, "Presigned Url이 생성되어 성공적으로 반환되었습니다."),

    LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공했습니다."),
    REISSUE_SUCCESS(HttpStatus.OK, "Access 토큰 재발급에 성공했습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃에 성공했습니다."),
    KAKAO_ACCESS_TOKEN_SUCCESS(HttpStatus.OK, "카카오 엑세스 토큰을 가져오는데 성공했습니다."),
    GOOGLE_ACCESS_TOKEN_SUCCESS(HttpStatus.OK, "구글 엑세스 토큰을 가져오는데 성공했습니다."),
    SET_MEMBER_NICKNAME_SUCCESS(HttpStatus.OK, "유저 닉네임을 설정하여 회원가입에 성공했습니다."),

    GET_MYPAGE_SUCCESS(HttpStatus.OK, "마이페이지 조회에 성공했습니다.");


    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}