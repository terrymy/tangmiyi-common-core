package com.tangmiyi.future.core.config;//package com.bitao.api.core.config;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.interceptor.KeyGenerator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.cache.RedisCacheWriter;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisPassword;
//import org.springframework.data.redis.connection.RedisSentinelConfiguration;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import java.lang.reflect.Method;
//import java.time.Duration;
//import java.util.Set;
//
//@Configuration
//@EnableCaching
//public class RedisClientConfig extends CachingConfigurerSupport {
//
//    @Value("${spring.redis.sentinel.nodes}")
//    private Set<String> nodes;
//
//    @Value("${spring.redis.host}")
//    private String host;
//
//    @Value("${spring.redis.port}")
//    private int port;
//
//    @Value("${spring.redis.sentinel.master}")
//    private String master;
//
//    @Value("${spring.redis.database}")
//    private int database;
//
//    @Value("${spring.redis.timeout}")
//    private long timeout;
//    @Value("${spring.redis.password}")
//    private String password;
//    @Value("${spring.redis.lettuce.pool.max-idle}")
//    private int maxIdle;
//    @Value("${spring.redis.lettuce.pool.min-idle}")
//    private int minIdle;
//    @Value("${spring.redis.lettuce.pool.max-wait}")
//    private long maxWait;
//    @Value("${spring.redis.lettuce.pool.max-active}")
//    private int maxActive;
//
//    @Bean
//    @Override
//    public KeyGenerator keyGenerator() {
//        return new KeyGenerator() {
//            @Override
//            public Object generate(Object target, Method method, Object... params) {
//                StringBuilder sb = new StringBuilder();
//                sb.append(target.getClass().getName());
//                sb.append(method.getName());
//                for (Object obj : params) {
//                    sb.append(obj.toString());
//                }
//                return sb.toString();
//            }
//        };
//    }
//
//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
//        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
//        // 初始化一个RedisCacheWriter
//        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
//        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(30));
//        defaultCacheConfig.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer)).disableCachingNullValues();
//        // 初始化RedisCacheManager
//        RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, defaultCacheConfig);
//        return cacheManager;
//    }
//
//    @Bean
//    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setDatabase(database);
//        redisStandaloneConfiguration.setHostName(host);
//        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
//        redisStandaloneConfiguration.setPort(port);
//        return redisStandaloneConfiguration;
//    }
//
//    @Bean
//    public RedisSentinelConfiguration redisSentinelConfiguration() {
//        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration(master, nodes);
//        redisSentinelConfiguration.setPassword(RedisPassword.of(password.toCharArray()));
//        return redisSentinelConfiguration;
//    }
//
//    @Bean
//    public RedisConnectionFactory lettuceConnectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration,
//                                                           RedisSentinelConfiguration redisSentinelConfiguration) {
//        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
//        genericObjectPoolConfig.setMaxIdle(maxIdle);
//        genericObjectPoolConfig.setMinIdle(minIdle);
//        genericObjectPoolConfig.setMaxTotal(maxActive);
//        genericObjectPoolConfig.setMaxWaitMillis(maxWait);
//        LettucePoolingClientConfiguration lettucePoolingClientConfiguration = LettucePoolingClientConfiguration.builder()
//                .poolConfig(genericObjectPoolConfig)
//                .build();
//        LettuceConnectionFactory lettuceConnectionFactory;
//        // 根据host是否有值获取不同配置
//        if(StringUtils.isNotBlank(host)){
//            lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration, lettucePoolingClientConfiguration);
//        }else{
//            lettuceConnectionFactory = new LettuceConnectionFactory(redisSentinelConfiguration, lettucePoolingClientConfiguration);
//        }
//        return lettuceConnectionFactory;
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        //解决查询缓存转换异常的问题
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//
//        // 设置值（value）的序列化采用FastJsonRedisSerializer。
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
//        // 设置键（key）的序列化采用StringRedisSerializer。
//        redisTemplate.setKeySerializer(redisSerializer);
//        redisTemplate.setHashKeySerializer(redisSerializer);
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
//}
