package com.ssafy.bango.global.auth.security;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class AuthWhiteList {
    public static final List<String> AUTH_WHITELIST_DEFAULT = Arrays.asList(
            "/member/signup", "/member/login", "/member/token",
            "/error", "/health",
            "/api/v1/rental",
            "/api/v1/rental/**",
            "/api/v1/rental/api/geocoding",
            "/api/v1/rental/api/facilities/nearby"
    );

    public static final List<String> AUTH_WHITELIST_WILDCARD = Arrays.asList(
//            "/common/**", "/h2-console/**"
//            , "/**"
    );

    public static final String[] AUTH_WHITELIST = Stream.concat(
            AUTH_WHITELIST_DEFAULT.stream(),
            AUTH_WHITELIST_WILDCARD.stream()
    ).toArray(String[]::new);
}
