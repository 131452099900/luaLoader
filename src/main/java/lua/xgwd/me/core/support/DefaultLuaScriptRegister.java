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
@Slf4j
public class DefaultLuaScriptRegister implements LuaScriptRegistry {

    LuaScriptCacheManager cacheManager;


    @Autowired
    public DefaultLuaScriptRegister(LuaScriptCacheManager luaScriptCacheManager) {
        this.cacheManager = luaScriptCacheManager;
    }

    @Override
    public void register(LuaScriptEntity luaScriptEntity) {
        // TODO 拓展化前置接口
        log.info("注册lua脚本-------------》 {} 《-------------", luaScriptEntity.getId());
        cacheManager.addScript(luaScriptEntity);
        // TODO 拓展化后置接口

    }
}