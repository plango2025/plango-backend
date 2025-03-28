package com.example.plango.infra.redis.repository;

import com.example.plango.infra.redis.model.RedisKeyType;

import java.util.concurrent.TimeUnit;

public interface RedisRepository {
    void saveWithTTL(RedisKeyType keyType, String key, String value, long ttl, TimeUnit unit);
    boolean exists(RedisKeyType keyType, String key);
    Object get(RedisKeyType keyType, String key);
    void delete(RedisKeyType keyType, String key);
    boolean setTTL(RedisKeyType keyType, String key, long ttl, TimeUnit unit);
}
