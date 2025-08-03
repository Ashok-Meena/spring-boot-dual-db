package com.ashokjeph.dualdb.security;

import java.io.IOException;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Autowired
//    private TokenService tokenService;

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    /*@Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7);

            try {
                // 1. Validate and extract username
                String username = jwtTokenUtil.getUsernameFromToken(jwtToken);

                // 2. Check if token is blacklisted
                if (tokenService.isTokenBlacklisted(jwtToken)) {
                    logger.warn("JWT token is blacklisted: {}", jwtToken);
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is blacklisted");
                    return;
                }

                // 3. Authenticate user if not already authenticated
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

                    if (jwtTokenUtil.validateToken(jwtToken, userDetails.getUsername())) {
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails, null, userDetails.getAuthorities());

                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }

            } catch (ExpiredJwtException e) {
                logger.warn("JWT token has expired: {}", jwtToken);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
                return;

            } catch (IllegalArgumentException | MalformedJwtException | SignatureException e) {
                logger.warn("Invalid JWT token: {}", e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;

            } catch (Exception e) {
                logger.error("Unexpected error during JWT validation", e);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication error");
                return;
            }
        }

        // Continue filter chain
        filterChain.doFilter(request, response);
    }*/

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain chain)
    throws ServletException, IOException {

        String uri = request.getRequestURI();

        // Allow public endpoints without authentication
        if (WebSecurityConfig.isPublicEndpoint(uri)) {
            chain.doFilter(request, response);
            return;
        }

        String subjectHeader = request.getHeader("X-Subject");
        String expiryHeader = request.getHeader("X-Expires-At");

        if (subjectHeader == null || subjectHeader.isBlank()) {
            sendErrorResponse(response, "Missing subject header", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (expiryHeader == null || expiryHeader.isBlank()) {
            sendErrorResponse(response, "Missing token expiry header", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            long expiryEpochMillis = Long.parseLong(expiryHeader);
            long currentTimeMillis = System.currentTimeMillis();

            if (currentTimeMillis > expiryEpochMillis) {
                sendErrorResponse(response, "Token expired", HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(subjectHeader);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
        } catch (NumberFormatException e) {
            sendErrorResponse(response, "Invalid token expiry format", HttpServletResponse.SC_BAD_REQUEST);
        } catch (UsernameNotFoundException e) {
            sendErrorResponse(response, "User not found", HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void sendErrorResponse(HttpServletResponse response, String message, int status) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
        response.getWriter().flush();
    }


}
