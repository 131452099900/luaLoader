package lua.xgwd.me.core.support;

import lua.xgwd.me.core.LuaScriptCacheManager;
import lua.xgwd.me.core.LuaScriptRegistry;
import lua.xgwd.me.core.bean.LuaScriptEntity;

/**
 * @author gbl.huang
 * @date 2025/03/10 17:27
 **/
public class DefaultLuaScriptRegister implements LuaScriptRegistry {

    LuaScriptCacheManager cacheManager;

    public DefaultLuaScriptRegister(LuaScriptCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void register(LuaScriptEntity luaScriptEntity) {
        // TODO 拓展化前置接口
        cacheManager.addScript(luaScriptEntity);
        // TODO 拓展化后置接口

    }
}