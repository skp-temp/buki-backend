package com.example.skptemp.global.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${jwt.password}")
    private String secretKey;
    private static final String userNameKey = "userName";
    private final UserDetailsService userDetailsService;

    public String createToken(UserDetails userDetails, Long expiredMs){
        Date now = new Date();
        Date expiration = new Date(System.currentTimeMillis() + expiredMs);

        log.info("now = " + now + " exp = " + expiration);
        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        String role = isAdmin ? "ADMIN" : "USER";
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        return Jwts.builder()
                .claims()
                .add("role", role)
                .add("userName", userDetails.getUsername())
                .issuedAt(now)
                .expiration(expiration)
                .subject(userDetails.getUsername())
                .and()
                .signWith(key)
                .compact();
    }

    public String getUserId(String token){
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get(userNameKey)
                .toString();
    }
    public Authentication getAuthentication(String token){
        String userId = this.getUserId(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        // UsernamePasswordAuthenticaionToken 만들어 반환
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    public String resolveTokenFromRequest(HttpServletRequest request){
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }

    public String bearerRemove(String authorization){
        return authorization.substring("Bearer ".length());
    }

    public boolean isExpired(String token){
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        Date expiration = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
        return expiration.before(new Date());
    }
}
