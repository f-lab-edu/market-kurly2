package com.hello.kurly.common.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class JwtServiceTest {

  @Test
  @DisplayName("JWT HMAC-SHA 알고리즘을 사용하여 SecretKey를 생성한다")
  void generateSecretKey() {
    SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    System.out.println("secretKey = " + secretKey);

    String base64EncodedSecretKey = Encoders.BASE64.encode(secretKey.getEncoded());
    System.out.println("base64EncodedSecretKey = " + base64EncodedSecretKey);

    SecretKey base64DecodedSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64EncodedSecretKey));
    assertThat(base64DecodedSecretKey).isEqualTo(secretKey);
  }
}