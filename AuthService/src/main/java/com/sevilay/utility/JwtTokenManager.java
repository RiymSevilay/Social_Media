package com.sevilay.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sevilay.exception.AuthServiceException;
import com.sevilay.exception.ErrorType;
import com.sevilay.utility.enums.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtTokenManager {
    /**
     * secretKey -> şifreleme için özel bir anahtar
     * Issuer -> jwt yi oluşturan, sahiplik
     * IssuerAt -> jwt nin oluşturulma zamanı
     * ExpiresAt -> jwt nin geçerlilik son zamanı
     * Sign -> jwt nin imzalanması, yani bir şifreleme algoritması ile şifrelenmesi
     */

//    private final String SECRETKEY = "q1HkSbQ89NA4S0l8vl8FdN62baygNKofhnzbdrHLWgT1TvshTd";
//
//    private final String ISSUER = "SocialMediaAuth";
//
//    private final Long EXDATE = 1000L * 30; //30 saniye

    /**
     * Kullanıcının authId si alınarak yeni bir jwt token üretilir
     *
     */
    //@Value("${jwt.secretkey}")
    String secretKey = "q1HkSbQ89NA4S0l8vl8FdN62baygNKofhnzbdrHLWgT1TvshTd";
   // @Value("${jwt.issuer}")
    String issuer = "Java11BoostAuth";
   // @Value("${jwt.audience}")
    String audience = "aaaaaa";

    Long expiration = System.currentTimeMillis() + (1000 * 60 * 5);


    public Optional<String> createToken(Long id) {
        String token = null;
        Date date = new Date(expiration);
        try {
            token = JWT.create()
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .withIssuedAt(new Date())
                    .withExpiresAt(date)
                    .withClaim("id", id)
                    .sign(Algorithm.HMAC512(secretKey));

            return Optional.of(token);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<String> createToken(Long id, Role role) {
        String token = null;
        Date date = new Date(expiration);
        try {
            token = JWT.create()
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .withIssuedAt(new Date())
                    .withExpiresAt(date)
                    .withClaim("id", id)
                    .withClaim("role", role.toString())
                    .sign(Algorithm.HMAC512(secretKey));
            return Optional.of(token);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    public Boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).withAudience(audience).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if (decodedJWT == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new AuthServiceException(ErrorType.INVALID_TOKEN);
        }
    }

    public Optional<Long> getIdFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).withAudience(audience).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if (decodedJWT == null) {
                throw new AuthServiceException(ErrorType.INVALID_TOKEN);
            }
            Long id = decodedJWT.getClaim("id").asLong();
            return Optional.of(id);
        } catch (Exception e) {
            e.getMessage();
            throw new AuthServiceException(ErrorType.INVALID_TOKEN);
        }
    }

    public Optional<String> getRoleFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).withAudience(audience).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if (decodedJWT == null) {
                throw new AuthServiceException(ErrorType.INVALID_TOKEN);
            }
            String role = decodedJWT.getClaim("role").asString();
            return Optional.of(role);
        } catch (Exception e) {
            e.getMessage();
            throw new AuthServiceException(ErrorType.INVALID_TOKEN);
        }
    }
}
