package lua.xgwd.me.core.support;

import lua.xgwd.me.core.LuaScriptCacheManager;
import lua.xgwd.me.core.LuaScriptExecutor;
import lua.xgwd.me.core.bean.LuaScriptEntity;
import lua.xgwd.me.core.bean.LuaScriptParams;
import lua.xgwd.me.core.exception.RedisScriptExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gbl.huang
 * @date 2025/03/11 10:17
 **/
public class DefaultLuaScriptExecutor implements LuaScriptExecutor {

    private RedisTemplate redisTemplate;
    private LuaScriptCacheManager luaScriptCacheManager;


    @Autowired
    public DefaultLuaScriptExecutor(RedisTemplate redisTemplate, LuaScriptCacheManager luaScriptCacheManager) {
        this.luaScriptCacheManager = luaScriptCacheManager;
        this.redisTemplate = redisTemplate;
    }
//    @Autowired
//    public DefaultLuaScriptExecutor(LuaScriptCacheManager luaScriptCacheManager) {
//        this.luaScriptCacheManager = luaScriptCacheManager;
//    }

    @Override
    public <T> T execute(Class<T> resType, String scriptId, LuaScriptParams params) {
        T res = null;
        try {
            LuaScriptEntity luaScriptEntity = luaScriptCacheManager.getById(scriptId);
            DefaultRedisScript<T> redisScript = new DefaultRedisScript<>(luaScriptEntity.getValue(), resType);
            List<String> keyList = Arrays.stream(params.getKeys()).collect(Collectors.toList());
            res = (T) redisTemplate.execute(redisScript, keyList, params.getArgs());
        } catch (Exception e) {
            throw new RedisScriptExecutionException("lua脚本执行失败", e);
        }
        return res;
    }
}