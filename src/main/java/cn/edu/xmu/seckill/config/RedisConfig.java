package cn.edu.xmu.seckill.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    /**
     * redis序列化，方便存入Redis
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        //key值序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //hash里面key值序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //value值序列化
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //hash里面value的序列化
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        //注入连接工厂
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }
}
