package lua.xgwd.me.core;

import lua.xgwd.me.core.bean.LuaScriptEntity;

/**
 * @author gbl.huang
 * @date 2025/03/10 11:25
 * lua脚本注册器 - 主要用于管理lua脚本的生命周期拓展
 */
public interface LuaScriptRegistry {
    void register(LuaScriptEntity luaScriptEntity);
}
