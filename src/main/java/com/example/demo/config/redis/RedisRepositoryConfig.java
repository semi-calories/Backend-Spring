package com.example.demo.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
@RequiredArgsConstructor
public class RedisRepositoryConfig {

    // redis와 연결 정보 설정 및 redis에 데이터 저장/조회하는데 사용되는 RedisTemplate 객체 생성
    // redis를 캐시로 사용하기 위한 설정

    @Value("${spring.data.redis.jedis.pool.host}")
    private String redisHost;

    @Value("${spring.data.redis.jedis.pool.port}")
    private int redisPort;


    // LettuceConnectionFactory 객체를 생성해 반환
    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        return new LettuceConnectionFactory(redisHost,redisPort);
    }

    // redis 작업을 수행하기 위해 RedisTemplate 객체를 생성해 반환
    @Bean
    public RedisTemplate<?,?> redisTemplate(){
        RedisTemplate<String,String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    // redis를 캐시로 사용하기 위한 CacheManager 빈 생성
    @Bean
    public CacheManager cacheManager(){
        // redisCacheManagerBuilder를 사용해 RedisConnectionFactory를 설정하고, RedisCacheConfiguration 구성
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory());
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                // redis의 key와 value의 직렬화 방식 설정
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        builder.cacheDefaults(configuration);
        return builder.build(); // cacheDefaults를 설정하여 만든 RedisCacheManager 반환

    }

}
