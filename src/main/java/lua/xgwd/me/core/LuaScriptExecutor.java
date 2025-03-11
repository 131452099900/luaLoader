package lua.xgwd.me.core;

import lua.xgwd.me.core.bean.LuaScriptParams;

/**
 * @author gbl.huang
 * @date 2025/03/10 11:25
 * Lua脚本执行器接口
 */
public interface LuaScriptExecutor {
    /**
     * 看看这里返回什么结果好，一般redis执行lua脚本的话其实就3种
     * Long boolean int ListMap
     * @return
     */
    <T> T execute(Class<T> resType, String scriptId, LuaScriptParams params);
}