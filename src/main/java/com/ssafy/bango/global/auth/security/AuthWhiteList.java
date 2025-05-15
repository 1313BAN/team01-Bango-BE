package com.ssafy.bango.global.auth.security;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class AuthWhiteList {
    public static final List<String> AUTH_WHITELIST_DEFAULT = Arrays.asList(
            "/api/v1/member/login", "/api/v1/member/token",
            "/api/v1/rental",
            "/error", "/health",
            "/batch-job"
    );

    public static final List<String> AUTH_WHITELIST_WILDCARD = Arrays.asList(
        "/api/v1/rental/**",
        "/api/v1/rental/api/**"
    );

    public static final String[] AUTH_WHITELIST = Stream.concat(
            AUTH_WHITELIST_DEFAULT.stream(),
            AUTH_WHITELIST_WILDCARD.stream()
    ).toArray(String[]::new);
}
