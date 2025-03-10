package lua.xgwd.me.core;

import lua.xgwd.me.core.bean.LuaScriptEntity;

/**
 * @author gbl.huang
 * @date 2025/03/10 11:25
 * lua脚本加载器
 */
public interface LuaScriptLoader {
    /**
     * 加载lua脚本
     * @param scriptId Lua脚本Id
     * @return lua脚本
     */
    LuaScriptEntity loadScript(String scriptId);
}
