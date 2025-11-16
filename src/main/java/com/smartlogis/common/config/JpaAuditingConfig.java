package com.smartlogis.common.config;

import com.smartlogis.common.infrastructure.audit.AuditorAwareImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
public class JpaAuditingConfig {

    @Bean
    @ConditionalOnMissingBean
    public AuditorAware auditorAwareImpl() {
        return new AuditorAwareImpl();
    }
}
