package com.sparta.zipsa.exception;

public class UserException extends RuntimeException{

    public static class AuthorityException extends UserException {}

    public static class PasswordNotMatchException extends UserException {}

    public static class UserNotFoundException extends UserException {}

    public static class UsernameDuplicateException extends UserException {}
}
