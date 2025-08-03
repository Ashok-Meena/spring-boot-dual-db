package com.ashokjeph.dualdb.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity(
        prePostEnabled = true,     // for @PreAuthorize / @PostAuthorize
        securedEnabled = true,     // for @Secured
        jsr250Enabled = true       // for @RolesAllowed
)
public class MethodSecurityConfig {
    // No additional code is required here unless customizing.
}
