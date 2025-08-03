package com.ashokjeph.dualdb.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokjeph.dualdb.repository.primary.TokenRecordRepository;

@Service
public class TokenService {

    @Autowired
    private TokenRecordRepository tokenRecordRepository;

    public void blacklistToken(String token) {
        tokenRecordRepository.findByToken(token).ifPresent(record -> {
            record.setStatus("BLACKLISTED");
            tokenRecordRepository.save(record);
        });
    }

    public boolean isTokenBlacklisted(String token) {
        return tokenRecordRepository.findByToken(token)
                .map(record -> "BLACKLISTED".equalsIgnoreCase(record.getStatus()))
                .orElse(false);
    }
}

