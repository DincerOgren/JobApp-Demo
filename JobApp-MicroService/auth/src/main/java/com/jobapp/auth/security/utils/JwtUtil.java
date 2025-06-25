package com.jobapp.auth.security.utils;

import com.jobapp.auth.security.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JwtUtil {

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.app.jwtExpirationMs}")
    private int jwtExpirationMs;


    //Get token from header

    public String getJwtTokenFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    // generate token
    public String generateJwtToken(UserDetails userDetails) {
        UserDetailsImpl user = (UserDetailsImpl) userDetails;
        String email = user.getUsername();


        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getAuthorities());
        claims.put("id", user.getId());
        claims.put("name",user.getName());

        return Jwts.builder()
                .subject(email)
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(key(),Jwts.SIG.HS256)
                .compact();
    }

    // get payload from token
    public Claims parseAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /** Generic claim extractor. */
    public <T> T getClaim(String token, Function<Claims,T> claimsResolver) {
        Claims claims = parseAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String getSubject(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public Long getUserId(String token) {
        return getClaim(token, c -> c.get("id", Long.class));
    }

    public String getName(String token) {
        return getClaim(token,c-> c.get("name", String.class));
    }

    public String getRole(String token) {
        return getClaim(token, c -> c.get("role", String.class));
    }


    //generate sign
    public SecretKey key() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    //validate jwt token
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().verifyWith(key()).build().parseSignedClaims(token);
            return true;
        }
        catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        }
        catch (ExpiredJwtException e){
            log.error("Expired JWT token: {}", e.getMessage());
        }
        catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token: {}", e.getMessage());
        }
        catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
