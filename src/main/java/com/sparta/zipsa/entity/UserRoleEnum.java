package com.sparta.zipsa.entity;

public enum UserRoleEnum {


    HELPER(Authority.HELPER),

    CUSTOMER(Authority.CUSTOMER),

    ADMIN(Authority.ADMIN);

    private final String authority;  // 시큐리티할 때 String값 필요하다고 해서 넣는..?

    UserRoleEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String HELPER = "ROLE_HELPER";
        public static final String CUSTOMER = "ROLE_CUSTOMER";
        public static final String ADMIN = "ROLE_ADMIN";
    }

    }

