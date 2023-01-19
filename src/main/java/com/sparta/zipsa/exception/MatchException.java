package com.sparta.zipsa.exception;

public class MatchException extends RuntimeException{

    public static class AlreadyApplyMatchException extends MatchException {}

    public static class AlreadyRejectMatchException extends MatchException {}

    public static class MatchNotFoundException extends MatchException {}

}
