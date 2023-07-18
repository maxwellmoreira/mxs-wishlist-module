package com.mxs.whishlist.config;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class responsible for Cassandra database configuration settings.
 */
@Configuration
public class CassandraConfig {
    @Bean
    public CqlSession session() {
        return CqlSession.builder().withKeyspace("ecommerce").build();
    }
}
