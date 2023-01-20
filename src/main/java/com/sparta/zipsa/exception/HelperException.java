package com.sparta.zipsa.exception;

public class HelperException extends RuntimeException{

    public static class AlreadyApplyHelperException extends HelperException {}

    public static class AlreadyHelperException extends HelperException {}

    public static class AlreadyCustomerException extends HelperException {}

    public static class HelperNotFoundException extends HelperException {}

}
