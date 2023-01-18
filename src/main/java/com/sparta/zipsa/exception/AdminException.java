package com.sparta.zipsa.exception;

public class AdminException extends RuntimeException{

    public static class AdminPasswordNotMatchException extends AdminException {}

    public static class InvalidAuthorityException extends AdminException {}

}
