package com.example.skptemp.global.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorization = jwtProvider.resolveTokenFromRequest(request);

        if(authorization == null || !authorization.startsWith("Bearer ")){
            log.error("authorization token error");
            log.error("authorization: " + authorization);
            filterChain.doFilter(request, response);
            return;
        }

        String token = jwtProvider.bearerRemove(authorization);

        if(jwtProvider.isExpired(token)){
            log.error("token is expired!!");
            filterChain.doFilter(request, response);
            return;
        }
        String userId = jwtProvider.getUserId(token);
        Authentication authentication = jwtProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

}
