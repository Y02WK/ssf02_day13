package main.day13.config;

import java.util.Optional;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import main.day13.models.ContactModel;

@Configuration
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Optional<Integer> redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Value("${spring.redis.jedis.pool.max-active}")
    private Integer jedisPoolMaxActive;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private Integer jedisPoolMaxIdle;

    @Value("${spring.redis.jedis.pool.min-idle}")
    private Integer jedisPoolMinIdle;

    @Bean
    @Scope("singleton")
    public RedisTemplate<String, ContactModel> createRedisTemplate() {
        // configuring the database
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPassword(redisPassword);
        if (redisPort.isPresent()) {
            config.setPort(redisPort.get());
        }
        // setup poolConfig
        final GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(jedisPoolMaxActive);
        poolConfig.setMinIdle(jedisPoolMinIdle);
        poolConfig.setMaxTotal(jedisPoolMaxIdle);

        // create client and factory
        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
        jedisFac.afterPropertiesSet();

        // create template for
        final RedisTemplate<String, ContactModel> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisFac);
        template.setKeySerializer(new StringRedisSerializer()); // keys in utf-8
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(ContactModel.class));
        return template;
    }
}
