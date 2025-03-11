package lua.xgwd.me.core.support;

import lombok.extern.slf4j.Slf4j;
import lua.xgwd.me.core.LuaScriptCacheManager;
import lua.xgwd.me.core.LuaScriptRegistry;
import lua.xgwd.me.core.bean.LuaScriptEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author gbl.huang
 * @date 2025/03/10 17:27
 **/
@Component
@Slf4j
public class DefaultLuaScriptRegister implements LuaScriptRegistry {

    @Autowired
    LuaScriptCacheManager cacheManager;

    public DefaultLuaScriptRegister() {

    }

    public DefaultLuaScriptRegister(LuaScriptCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void register(LuaScriptEntity luaScriptEntity) {
        // TODO 拓展化前置接口
        log.info("注册 {}", luaScriptEntity);
        cacheManager.addScript(luaScriptEntity);
        // TODO 拓展化后置接口

    }
}