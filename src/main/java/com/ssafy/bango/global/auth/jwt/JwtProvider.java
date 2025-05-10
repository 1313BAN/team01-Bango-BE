package com.ssafy.bango.global.auth.jwt;

import com.ssafy.bango.global.auth.redis.RefreshToken;
import com.ssafy.bango.global.auth.redis.RefreshTokenRepository;
import com.ssafy.bango.global.exception.CustomException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

import static com.ssafy.bango.global.exception.enums.ErrorType.*;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    private static final Long ACCESS_TOKEN_EXPIRATION_TIME = 60 * 1000L * 15; // Access Token 만료시간 15분
    private static final Long REFRESH_TOKEN_EXPIRATION_TIME = 60 * 1000L * 60 * 24 * 7; // Refresh Token 만료시간 7일

    @Value("${jwt.secret}")
    private String JWT_SECRET;
    private final RefreshTokenRepository tokenRepository;

    // Access Token, Refresh Token을 발급하는 메서드
    public TokenDTO issueToken(Authentication authentication) {
        return TokenDTO.of(
                generateAccessToken(authentication),
                generateRefreshToken(authentication)
        );
    }

    private String generateAccessToken(Authentication authentication) {
        Claims claims = makeClaims(authentication, ACCESS_TOKEN_EXPIRATION_TIME);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .signWith(getSignKey())
                .compact();
    }

    private String generateRefreshToken(Authentication authentication) {
        Claims claims = makeClaims(authentication, REFRESH_TOKEN_EXPIRATION_TIME);

        String refreshToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .signWith(getSignKey())
                .compact();

        tokenRepository.save(
                RefreshToken.builder()
                        .memberId(Long.parseLong(authentication.getPrincipal().toString()))
                        .refreshToken(refreshToken)
                        .expiration(REFRESH_TOKEN_EXPIRATION_TIME.intValue() / 1000)
                        .build()
        );

        return refreshToken;
    }

    public void deleteRefreshToken(Long memberId) {
        if (tokenRepository.existsById(memberId)) {
            tokenRepository.deleteById(memberId);
        } else {
            throw new CustomException(NOT_FOUND_REFRESH_TOKEN_ERROR);
        }
    }

    // expirationTime 만큼의 유효기간, memberId를 가진 Claims 생성하는 메서드
    private Claims makeClaims(Authentication authentication, Long expirationTime) {
        final Date now = new Date();
        final Date expiry = new Date(now.getTime() + expirationTime);

        final Claims claims = Jwts.claims()
                .setIssuedAt(now)
                .setExpiration(expiry);

        claims.put("memberId", authentication.getPrincipal());

        return claims;
    }

    // 암호화에 필요한 키는 디코딩된 키어야 함.
    private SecretKey getSignKey() {
        byte[] decodedKey = Base64.getDecoder().decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public boolean validateAccessToken(String accessToken) {
        try {
            final Claims claims = getJwtBody(accessToken);
            return true;
        } catch (MalformedJwtException ex) { // JWT 구조 이상 및 손상
            throw new CustomException(INVALID_JWT_TOKEN);
        } catch (ExpiredJwtException ex) { // 유효기간 만료
            throw new CustomException(EXPIRED_JWT_TOKEN);
        } catch (UnsupportedJwtException ex) { //지원하지 않는 JWT 형식
            throw new CustomException(UNSUPPORTED_JWT_TOKEN);
        } catch (IllegalArgumentException ex) { // null 또는 ""
            throw new CustomException(EMPTY_JWT_TOKEN);
        } catch (SecurityException ex) { // JWT 서명 검증 실패
            throw new CustomException(INVALID_JWT_SIGNATURE);
        } catch (Exception e) { // 등등
            throw new CustomException(UNKNOWN_JWT_ERROR);
        }
    }

    public Long validateRefreshToken(String refreshToken) {
        // Redis에 해당 토큰이 존재하지 않을 경우 -> 만료된 토큰
        Long memberId = getMemberIdFromJwt(refreshToken);
        if (tokenRepository.existsById(memberId)) {
            return memberId;
        } else {
            throw new CustomException(EXPIRED_JWT_TOKEN);
        }
    }

    public Long getMemberIdFromJwt(String token) {
        Claims claims = getJwtBody(token);
        return Long.parseLong(claims.get("memberId").toString());
    }

    private Claims getJwtBody(final String token) {
        // 만료된 토큰에 대해 parseClaimsJws()을 수행 시, ExpiredJwtException 발생
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
