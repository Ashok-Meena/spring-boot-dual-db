package com.ashokjeph.dualdb.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokjeph.dualdb.entity.primary.TokenRecord;
import com.ashokjeph.dualdb.repository.primary.TokenRecordRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private TokenRecordRepository tokenRecordRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public static final long REFRESH_TOKEN_VALIDITY = 7 * 24 * 60 * 60 * 1000; // 7 days

    public String createRefreshToken(String userName) {
//        String token = jwtTokenUtil.generateToken(userName);
        String token = null;
        TokenRecord tokenRecord = new TokenRecord();
        tokenRecord.setId(UUID.randomUUID().toString());
//        tokenRecord.setToken(token);
        tokenRecord.setUserId(userName);
        tokenRecord.setTokenIssueDate(new Date());
        tokenRecord.setTokenExpiryDate(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY));
        tokenRecord.setStatus("ACTIVE");
        tokenRecordRepository.save(tokenRecord);

        return token;
    }

    public TokenRecord verifyExpiration(TokenRecord tokenRecord) {
        if (tokenRecord.getTokenExpiryDate().before(new Date())) {
            tokenRecord.setStatus("EXPIRED");
            tokenRecordRepository.save(tokenRecord);
            throw new RuntimeException("Refresh token expired.");
        }
        return tokenRecord;
    }

    public Optional<TokenRecord> findByToken(String token) {
        return tokenRecordRepository.findByToken(token);
    }

    public void revokeTokensByUserId(String userId) {
        List<TokenRecord> tokens = tokenRecordRepository.findByUserId(userId);
        for (TokenRecord token : tokens) {
            token.setStatus("REVOKED");
        }
        tokenRecordRepository.saveAll(tokens);
    }
}

