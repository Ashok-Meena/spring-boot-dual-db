package com.ashokjeph.dualdb.auth.service;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;

import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.ashokjeph.dualdb.entity.primary.TokenRecord;
import com.ashokjeph.dualdb.entity.primary.User;
import com.ashokjeph.dualdb.repository.primary.TokenRecordRepository;
import com.ashokjeph.dualdb.repository.primary.UserRepository;
import com.ashokjeph.dualdb.websocket.OutputMessage;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    private UserRepository userRepository;
    private SimpMessagingTemplate simpMessagingTemplate;
    private TokenRecordRepository tokenRecordRepository;

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey secretKey;

    public static long ACCESS_TOKEN_VALIDITY = 2 * 60 * 60 * 1000;  // 2 hours validity

    @PostConstruct
    public void init() {
        if (secret == null || secret.trim().isEmpty()) {
            throw new IllegalStateException("JWT secret is not configured!");
        }

        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("JWT secret must be at least 32 bytes for HS512");
        }

        secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    // Retrieve username from JWT token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Retrieve expiration date from JWT token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // For retrieving any information from token we need the secret key
    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            // You can also log this error
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    // Check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // Generate token for user
    public String generateToken(String username, Long expiryMinute) {

        String uuid = UUID.randomUUID().toString();
        Map<String, Object> claims = new HashMap<>();
        claims.put("UUID", uuid);

        List<String> audiences = Arrays.asList("venapp-api", "squid-api");

        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        if (expiryMinute != null && expiryMinute > 0)
            ACCESS_TOKEN_VALIDITY = expiryMinute * 60 * 1000;
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuer("squid-auth-service")
                .claim("aud", audiences)
//                .setAudience("venapp-api, squid-api")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        if (expiryMinute != null && expiryMinute > 0)
            ACCESS_TOKEN_VALIDITY = expiryMinute * 60 * 1000;

        TokenRecord tokenRecord = new TokenRecord();
        tokenRecord.setId(UUID.randomUUID().toString());
        tokenRecord.setStatus("Active");
        tokenRecord.setToken(token);
        tokenRecord.setTokenExpiryDate(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY));
        tokenRecord.setTokenIssueDate(new Date(System.currentTimeMillis()));
        tokenRecord.setUserId(username);
        tokenRecordRepository.save(tokenRecord);

        return token;
    }

    // Generate token for user with role based security
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role) // Custom claim
                .setIssuer("squid-auth-service")
                .setAudience("venapp-api,squid-api")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    // Validate token
    public Boolean validateToken(String token, String username) {
        final String usernameFromToken = getUsernameFromToken(token);
        return (usernameFromToken.equals(username) && !isTokenExpired(token));
    }

    public void writeSocketIfTokenExpired(String email, String token) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String socketDestination = "/Squid/TokenExpired";
            String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());

            OutputMessage message = new OutputMessage(user.getId(), "Expired", dateTime, token);
            simpMessagingTemplate.convertAndSend(socketDestination, message);
            System.out.printf("[JwtTokenUtil] Token expired socket notification sent for user ID: {}", user.getId());
        } else {
        	System.out.printf("[JwtTokenUtil] User not found for email: {}. Socket notification not sent.", email);
        }
    }
}
