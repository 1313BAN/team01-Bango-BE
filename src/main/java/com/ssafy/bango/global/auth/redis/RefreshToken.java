package com.ssafy.bango.global.auth.redis;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@Getter
@RedisHash("refreshToken")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
  @Id
  private Long memberId;

  private String refreshToken;

  @TimeToLive(unit = TimeUnit.MINUTES)
  private Integer expiration;
}