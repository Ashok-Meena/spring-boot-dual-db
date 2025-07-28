package com.ashokjeph.dualdb.cronjobs;

import com.ashokjeph.dualdb.auth.service.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ashokjeph.dualdb.entity.primary.TokenRecord;
import com.ashokjeph.dualdb.repositories.primary_repository.TokenRecordRepository;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class TokenCleanupJob {

    private final JwtTokenUtil jwtTokenUtil;
    private final TokenRecordRepository tokenRecordRepository;

    /**
     * Cron expression: Runs every 7 minutes
     * Format: second, minute, hour, day of month, month, day(s) of week
     */
    @Scheduled(cron = "0 0/7 * * * ?")
    public void cleanupExpiredTokens() {
        log.info("[TokenCleanupJob] Starting cleanup at {}", new Date());
        try {
            List<TokenRecord> expiredTokens = tokenRecordRepository.findAllByTokenExpiryDateLessThan(new Date());
            for (TokenRecord token : expiredTokens) {
                try {
                    log.info("[TokenCleanupJob] Expired token for user {}: {}", token.getUserId(), token.getTokenExpiryDate());
                    jwtTokenUtil.writeSocketIfTokenExpired(token.getUserId(), token.getToken());
                    tokenRecordRepository.delete(token);
                } catch (Exception ex) {
                    log.error("[TokenCleanupJob] Error processing token for user {}: {}", token.getUserId(), ex.getMessage());
                }
            }
        } catch (Exception e) {
            log.error("[TokenCleanupJob] Failed to run cleanup job: {}", e.getMessage(), e);
        }
    }
}

