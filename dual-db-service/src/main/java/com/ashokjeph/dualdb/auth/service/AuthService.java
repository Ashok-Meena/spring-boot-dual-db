package com.ashokjeph.dualdb.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ashokjeph.dualdb.auth.model.AuthResponseModel;
import com.ashokjeph.dualdb.auth.model.LoginRequestModel;
import com.ashokjeph.dualdb.entity.primary.TokenRecord;
import com.ashokjeph.dualdb.security.JwtUserDetailsService;

@Service
public class AuthService {

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RefreshTokenService refreshTokenService;

    public AuthResponseModel authenticateUser(LoginRequestModel loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid credentials"); // or custom exception
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

//        final String accessToken = jwtTokenUtil.generateToken(userDetails.getUsername());
//        final String refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());
        final String accessToken = null;
        final String refreshToken = null;

        return new AuthResponseModel(accessToken, refreshToken);
    }


    public AuthResponseModel refreshAccessToken(String refreshTokenStr) {
        TokenRecord tokenRecord = refreshTokenService.findByToken(refreshTokenStr)
                .map(refreshTokenService::verifyExpiration)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

//        String newAccessToken = jwtTokenUtil.generateToken(tokenRecord.getUserId());
        String newAccessToken = null;
        return new AuthResponseModel(newAccessToken, tokenRecord.getToken());
    }
}

