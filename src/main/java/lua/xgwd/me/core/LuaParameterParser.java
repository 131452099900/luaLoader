package lua.xgwd.me.core;

import lua.xgwd.me.core.bean.LuaScriptParams;
import java.lang.reflect.Method;

/**
 * @author gbl.huang
 * @date 2025/03/10 11:25
 * Lua脚本参数解析器
 */
interface LuaParameterParser {
    /**
     * 解析参数
     * @param method 接口方法
     * @param keys   lua keys
     * @param args   lua args
     * @return 解析后
     */
    LuaScriptParams resolve(Method method, String[] keys, Object[] args);
}
