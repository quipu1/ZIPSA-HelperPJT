package com.sparta.zipsa.exception.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum StatusCode {
    //400 BAD_REQUEST 잘못된 요청
    //401 UNAUTHORIZED 인증되지 않은 사용자
    //404 NOT_FOUND 잘못된 리소스 접근
    //409 CONFLICT 중복된 리소스

    //TOKEN
    TOKEN(BAD_REQUEST, "토큰 관련 오류입니다."),
    INVALID_TOKEN(UNAUTHORIZED, "권한 정보가 없는 토큰입니다."),

    //USER - 사용자 관련
    USER(BAD_REQUEST, "사용자 관련 오류입니다."),
    INVALID_AUTHORITY_USER(BAD_REQUEST, "권한이 없는 사용자입니다."),
    USER_PASSWORD_NOT_MATCH(BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    USER_NOT_FOUND(NOT_FOUND,  "회원을 찾을 수 없습니다."),
    USERNAME_DUPLICATE(CONFLICT, "중복된 username 입니다."),

    //BOARD
    BOARD(BAD_REQUEST, "게시글 관련 오류입니다."),
    BOARD_NOT_FOUND(NOT_FOUND, "게시글이 존재하지 않습니다."),

    //MATCH
    MATCH(BAD_REQUEST, "매칭 관련 오류입니다."),
    ALREADY_APPLY_MATCH(BAD_REQUEST, "이미 매칭을 신청한 상태입니다."),

    ALREADY_REJECTED_MATCH(BAD_REQUEST, "이미 거절된 상태입니다."),
    ALREADY_APPROVED_MATCH(BAD_REQUEST, "이미 수락된 상태입니다."),
    MATCH_NOT_FOUND(NOT_FOUND, "매칭글이 존재하지 않습니다."),
    ALREADY_MATCHBOARD_FOUND(BAD_REQUEST, "이미 게시글을 등록하셨습니다."),

    //HELPER
    HELPER(BAD_REQUEST, "HELPER 신청 관련 오류입니다."),
    ALREADY_APPLY_HELPER(BAD_REQUEST, "이미 HELPER 권한을 신청한 상태입니다."),
    ALREADY_HELPER(BAD_REQUEST, "이미 HELPER 권한인 사용자입니다."),
    ALREADY_CUSTOMER(BAD_REQUEST, "이미 CUSTOMER 권한인 사용자입니다."),

    HELPER_NOT_FOUND(NOT_FOUND, "HELPER 신청글이 존재하지 않습니다."),

    //ADMIN
    ADMIN(BAD_REQUEST, "ADMIN 관련 오류입니다."),
    ADMIN_PASSWORD_NOT_MATCH(BAD_REQUEST, "관리자 암호가 일치하지 않아 등록이 불가합니다."),
    INVALID_AUTHORITY(BAD_REQUEST, "존재하지 않는 권한입니다."),

    //SERVER
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버 에러입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
