package com.ssafy.bango.global.auth.security;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class AuthWhiteList {
    public static final List<String> AUTH_WHITELIST_DEFALUT = Arrays.asList(
            "/error", "/member/login", "/member/reissue", "/redis/test"
            , "/member/kakao", "/member/google"
    );

    public static final List<String> AUTH_WHITELIST_WILDCARD = Arrays.asList(
//            "/common/**", "/h2-console/**"
//            , "/**"
    );

    public static final String[] AUTH_WHITELIST = Stream.concat(
            AUTH_WHITELIST_DEFALUT.stream(),
            AUTH_WHITELIST_WILDCARD.stream()
    ).toArray(String[]::new);
}
