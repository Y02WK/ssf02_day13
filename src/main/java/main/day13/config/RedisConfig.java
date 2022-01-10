package main.day13.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
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
        // create client and factory
        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
        jedisFac.afterPropertiesSet();

        // create template for
        final RedisTemplate<String, ContactModel> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisFac);
        template.setKeySerializer(new StringRedisSerializer()); // keys in utf-8
        template.setValueSerializer(new StringRedisSerializer()); // optional value serializer
        return template;
    }
}
