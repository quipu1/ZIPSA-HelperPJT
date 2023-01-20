package com.sparta.zipsa.advice;

import com.sparta.zipsa.exception.*;
import com.sparta.zipsa.exception.UserException.*;
import com.sparta.zipsa.exception.TokenException.*;
import com.sparta.zipsa.exception.BoardException.*;
import com.sparta.zipsa.exception.HelperException.*;
import com.sparta.zipsa.exception.MatchException.*;
import com.sparta.zipsa.exception.AdminException.*;
import com.sparta.zipsa.exception.status.StatusCode;
import com.sparta.zipsa.exception.status.StatusResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(TokenException.class)
    public ResponseEntity<StatusResponse> tokenException(TokenException e) {
        log.error("TokenException: ", e.getMessage());

        if (e instanceof InvalidTokenException) {
            return StatusResponse.toResponseEntity(StatusCode.INVALID_TOKEN);
        }
        return StatusResponse.toResponseEntity(StatusCode.TOKEN);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<StatusResponse> userException(UserException e) {
        log.error("UserException: ", e.getMessage());

        if (e instanceof AuthorityException)
            return StatusResponse.toResponseEntity(StatusCode.INVALID_AUTHORITY_USER);
        else if (e instanceof PasswordNotMatchException) {
            return StatusResponse.toResponseEntity(StatusCode.USER_PASSWORD_NOT_MATCH);
        } else if (e instanceof UserNotFoundException) {
            return StatusResponse.toResponseEntity(StatusCode.USER_NOT_FOUND);
        } else if (e instanceof UsernameDuplicateException) {
            return StatusResponse.toResponseEntity(StatusCode.USERNAME_DUPLICATE);
        }
        return StatusResponse.toResponseEntity(StatusCode.USER);
    }

    @ExceptionHandler(BoardException.class)
    public ResponseEntity<StatusResponse> boardException(BoardException e) {
        log.error("BoardException: ", e.getMessage());

        if (e instanceof BoardNotFoundException)
            return StatusResponse.toResponseEntity(StatusCode.BOARD_NOT_FOUND);

        return StatusResponse.toResponseEntity(StatusCode.BOARD);
    }

    @ExceptionHandler(MatchException.class)
    public ResponseEntity<StatusResponse> matchException(MatchException e) {
        log.error("MatchException: ", e.getMessage());

        if (e instanceof MatchNotFoundException)
            return StatusResponse.toResponseEntity(StatusCode.MATCH_NOT_FOUND);
        else if (e instanceof AlreadyApplyMatchException) {
            return StatusResponse.toResponseEntity(StatusCode.ALREADY_APPLY_MATCH);
        } else if (e instanceof AlreadyRejectMatchException) {
            return StatusResponse.toResponseEntity(StatusCode.ALREADY_REJECTED_MATCH);
        } else if (e instanceof AlreadyApproveMatchException) {
            return StatusResponse.toResponseEntity(StatusCode.ALREADY_APPROVED_MATCH);
        }

        return StatusResponse.toResponseEntity(StatusCode.MATCH);
    }

    @ExceptionHandler(HelperException.class)
    public ResponseEntity<StatusResponse> helperException(HelperException e) {
        log.error("HelperException: ", e.getMessage());

        if (e instanceof AlreadyApplyHelperException)
            return StatusResponse.toResponseEntity(StatusCode.ALREADY_APPLY_HELPER);
        else if (e instanceof AlreadyHelperException) {
            return StatusResponse.toResponseEntity(StatusCode.ALREADY_HELPER);
        }
        else if (e instanceof AlreadyCustomerException) {
            return StatusResponse.toResponseEntity(StatusCode.ALREADY_CUSTOMER);
        } else if (e instanceof HelperNotFoundException) {
            return StatusResponse.toResponseEntity(StatusCode.HELPER_NOT_FOUND);
        }

        return StatusResponse.toResponseEntity(StatusCode.HELPER);
    }

    @ExceptionHandler(AdminException.class)
    public ResponseEntity<StatusResponse> adminException(AdminException e) {
        log.error("AdminException: ", e.getMessage());

        if (e instanceof AdminPasswordNotMatchException)
            return StatusResponse.toResponseEntity(StatusCode.ADMIN_PASSWORD_NOT_MATCH);
        else if (e instanceof InvalidAuthorityException) {
            return StatusResponse.toResponseEntity(StatusCode.INVALID_AUTHORITY);
        }

        return StatusResponse.toResponseEntity(StatusCode.ADMIN);
    }

}
