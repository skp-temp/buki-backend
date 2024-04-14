package com.example.skptemp.global.configuration;

import com.example.skptemp.global.util.GlobalConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
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
//@RequiredArgsConstructor
public class JwtProvider {

    private final SecretKey secretKey;
    private static final String userIdKey = "userId";
    private final UserDetailsService userDetailsService;

    public JwtProvider(@Value("${jwt.password}") String secretKey, UserDetailsService userDetailsService){
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
        this.userDetailsService = userDetailsService;
    }

    public String createDefaultToken(UserDetails userDetails){
        return createToken(userDetails, GlobalConstants.TOKEN_EXPIRATION_TIME);
    }

    public String createTestToken(UserDetails userDetails){
        return createToken(userDetails, GlobalConstants.TEST_TOKEN_EXPIRATION_TIME);
    }

    public String createToken(UserDetails userDetails, Long expiredMs){
        Date now = new Date();
        Date expiration = new Date(System.currentTimeMillis() + expiredMs);

        log.info("now = " + now + " exp = " + expiration);
        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        String role = isAdmin ? "ADMIN" : "USER";
        Long userId = Long.parseLong(userDetails.getUsername());

        return Jwts.builder()
                .claims()
                .add("role", role)
                .add("userId", userId)
                .issuedAt(now)
                .expiration(expiration)
                .subject(userDetails.getUsername())
                .and()
                .signWith(secretKey)
                .compact();
    }

    public Long getUserId(String token){
        return Long.parseLong(
                Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get(userIdKey)
                .toString());
    }
    public Authentication getAuthentication(String token){
        Long userId = getUserId(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId.toString());
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
//        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        Date expiration = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
        return expiration.before(new Date());
    }
}
