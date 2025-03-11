package lua.xgwd.me.conf;

import lombok.extern.slf4j.Slf4j;
import lua.xgwd.me.core.*;
import lua.xgwd.me.core.listener.RegisterListener;
import lua.xgwd.me.core.support.*;
import lua.xgwd.me.core.support.scanner.ReflectionClassScanner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author gbl.huang
 * @date 2025/03/11 11:38
 **/
@Configuration
@Slf4j
public class LuaScriptConfig {

    @Bean
    public LuaScriptStorage luaScriptStorage() {
        return new DefaultLuaScriptStorage();
    }


    @Bean
    @ConditionalOnMissingBean(RedisTemplate.class)
    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        RedisSerializer redisSerializer = new StringRedisSerializer();
        // 连接工厂
        redisTemplate.setConnectionFactory(factory);
        // 键序列化
        redisTemplate.setKeySerializer(redisSerializer);
        // 值序列化
        redisTemplate.setValueSerializer(redisSerializer);
        // key hashMap序列化
        redisTemplate.setHashKeySerializer(redisSerializer);
        // value hashMap序列化
        redisTemplate.setHashValueSerializer(redisSerializer);
        return redisTemplate;
    }


    @Bean
    public LuaScriptExecutor luaScriptExecutor(RedisTemplate redisTemplate, LuaScriptCacheManager luaScriptCacheManager) {
        return new DefaultLuaScriptExecutor(redisTemplate, luaScriptCacheManager);
    }
    @Bean
    public BootStrapLuaScriptLoader bootStrapLuaScriptLoader(ClassScanner classScanner, LuaScriptLoader luaScriptLoader
            ,LuaMapperFactory luaMapperFactory, LuaScriptExecutor luaScriptExecutor) {
        return new DefaultBootstrapLoader(classScanner, luaScriptLoader, luaMapperFactory, luaScriptExecutor);
    }
    @Bean
    public LuaScriptRegistry luaScriptRegistry(LuaScriptCacheManager cacheManager) {
        return new DefaultLuaScriptRegister(cacheManager);
    }
    @Bean
    public ClassScanner classScanner() {
        return new ReflectionClassScanner();
    }

    @Bean
    public RegisterListener registerListener(LuaScriptRegistry luaScriptRegistry) {
        return new RegisterListener(luaScriptRegistry);
    }
    @Bean
    public LuaScriptCacheManager luaScriptCacheManager(LuaScriptStorage luaScriptStorage) {
        return new DefaultLuaScriptCacheManager(luaScriptStorage);
    }
    @Bean
    public LuaScriptLoader luaScriptLoader() {
        return new DefaultLuaScriptLoader();
    }
    @Bean
    public LuaMapperFactory luaMapperFactory() {
        return new LuaMapperProxyFactory();
    }


}