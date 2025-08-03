package com.ashokjeph.dualdb.squid_admin;

import com.ashokjeph.dualdb.auth.service.JwtTokenUtil;
import com.ashokjeph.dualdb.entity.primary.TokenRecord;
import com.ashokjeph.dualdb.entity.primary.User;
import com.ashokjeph.dualdb.enums.LoginLockStatus;
import com.ashokjeph.dualdb.enums.UserStatus;
import com.ashokjeph.dualdb.exception.ApiError;
import com.ashokjeph.dualdb.repository.primary.TokenRecordRepository;
import com.ashokjeph.dualdb.repository.primary.UserRepository;
import com.ashokjeph.dualdb.security.JwtUserDetailsService;
import com.ashokjeph.dualdb.squid_admin.model.RefreshTokenRequestModel;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SquidService {

    @Value("${app.jwt.expiry.minutes}")
    private Long jwtExpiryMinutes;

    @Value("${app.jwt.expiry.minutes.merchant}")
    private Long jwtExpiryMinutesMerchant;

    private UserRepository userRepository;
    private JwtUserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private TokenRecordRepository tokenRecordRepository;

    public ResponseEntity<?> squidLogin(Map<String, Object> params) {
        String email = (String) params.get("email");
        if (email == null || email.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is required");
        }

        String inputRole = (String) params.get("role");
        if (inputRole == null || inputRole.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Role is required");
        }

        try {
            User user = userRepository.findUserByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

            // Override role if user is a child user
            String actualRole = user.getParentId() != null
                    ? user.getUserRole() != null && user.getUserRole().getRole() != null
                    ? user.getUserRole().getRole().getBehave()
                    : user.getRole().getBehave()
                    : user.getRole().getBehave();

            if (!actualRole.equalsIgnoreCase(inputRole)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid role for user");
            }

            // Check if user is ACTIVE if child or INVESTOR
            if ((user.getParentId() != null || "INVESTOR".equalsIgnoreCase(user.getRole().getName())) &&
                    user.getStatus() != UserStatus.ACTIVE) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not active");
            }

            // Update login timestamps
            Date now = new Date();
            user.setLoginTime(now);
            user.setLastLogin(now);

            // Token generation for Admin
            if ("Admin".equalsIgnoreCase(user.getRole().getBehave()) && user.getStatus() != UserStatus.BLOCKED) {
                Long expiryMinute = parseExpiry(params.get("expiryMinute"));
                UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
                String token = jwtTokenUtil.generateToken(userDetails.getUsername(), expiryMinute);
                user.setToken(token);
                user.setTokenExpiryMinute(expiryMinute);
            }

            // Reset failed login metadata
            user.setLastFailedLoginAttempt(null);
            user.setLockStatus(LoginLockStatus.UNLOCK);
            user.setLoginFailedCount(0);
            user.setOTPFailedCount(0);

            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);

        } catch (UsernameNotFoundException e) {
        	System.out.printf("Login failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user");
        } catch (Exception e) {
            System.out.printf("Unexpected error during login", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed due to server error");
        }
    }

    private Long parseExpiry(Object expiryObj) {
        try {
            if (expiryObj instanceof String expiryStr && !expiryStr.isBlank()) {
                return Long.parseLong(expiryStr);
            }
        } catch (Exception ignored) {
        }
        return jwtExpiryMinutes;
    }

    public ResponseEntity<?> refreshToken(RefreshTokenRequestModel requestModel, HttpServletRequest request) {
        String userId = requestModel.getUserId();
        String oldToken = requestModel.getOldToken();
        String subjectHeader = request.getHeader("X-Subject");

        if (subjectHeader == null || subjectHeader.isBlank()) {
            return ApiError.getApiResponse(false, "Missing user info in header", null, HttpStatus.UNAUTHORIZED);
        }

        Optional<User> optionalUser = userRepository.findUserByEmail(subjectHeader);
        if (optionalUser.isEmpty()) {
            return ApiError.getApiResponse(false, "User not found", null, HttpStatus.UNAUTHORIZED);
        }

        User user = optionalUser.get();

        if (!user.getId().equals(userId)) {
            return ApiError.getApiResponse(false, "Header user does not match userId", null, HttpStatus.UNAUTHORIZED);
        }

        if (oldToken == null || oldToken.isBlank()) {
            return ApiError.getApiResponse(false, "Old token is required", null, HttpStatus.BAD_REQUEST);
        }

        Optional<TokenRecord> tokenRecordOpt = tokenRecordRepository.findByToken(oldToken);
        if (tokenRecordOpt.isEmpty()) {
            return ApiError.getApiResponse(false, "Old token not found", null, HttpStatus.NOT_FOUND);
        }

        try {
            tokenRecordRepository.delete(tokenRecordOpt.get());

            Long expiryMinutes = "ADMIN".equalsIgnoreCase(user.getRole().getBehave())
                    ? jwtExpiryMinutes
                    : jwtExpiryMinutesMerchant;

            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            String newToken = jwtTokenUtil.generateToken(userDetails.getUsername(), expiryMinutes);

            return ApiError.getApiResponse(true, "New token generated", newToken, HttpStatus.OK);
        } catch (Exception e) {
            System.out.printf("[refreshToken] Exception occurred: ", e);
            return ApiError.getApiResponse(false, "Error while refreshing token", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> logout(HttpServletRequest request) {
        try {
            // Extract token from Authorization header
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);

                // Optionally delete from DB
                tokenRecordRepository.findByToken(token).ifPresent(tokenRecordRepository::delete);

                // Optionally send socket update
                String userId = jwtTokenUtil.getUsernameFromToken(token);
                jwtTokenUtil.writeSocketIfTokenExpired(userId, token);
            }

            return ResponseEntity.status(HttpStatus.OK).body("Logout successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Logout failed");
        }
    }
}
