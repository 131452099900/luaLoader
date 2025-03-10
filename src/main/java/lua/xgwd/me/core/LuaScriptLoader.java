package lua.xgwd.me.core;

import lua.xgwd.me.core.annotation.LuaScript;

/**
 * @author gbl.huang
 * @date 2025/03/10 11:25
 * lua脚本加载器
 */
interface LuaScriptLoader {
    LuaScript loadScript(String scriptId);
}
