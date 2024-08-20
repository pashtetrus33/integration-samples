package com.example.service.integration_app.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "app.cache")
public class AppCacheProperties {

    private CacheType cacheType;

    private final List<String> cacheNames = new ArrayList<>();

    private final Map<String, CacheProperties> caches = new HashMap<>();

    @Data
    public static class CacheProperties {
        private Duration expiry = Duration.ZERO;
    }

    public interface CacheNames {
        String DATABASE_ENTITIES = "databaseEntities";
        String DATABASE_ENTITY_BY_NAME = "databaseEntityByName";
    }

    public enum CacheType {
        IN_MEMORY
    }
}
