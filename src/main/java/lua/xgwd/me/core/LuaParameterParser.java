package lua.xgwd.me.core;

import lua.xgwd.me.core.bean.LuaScriptParams;
import java.lang.reflect.Method;

/**
 * @author gbl.huang
 * @date 2025/03/10 11:25
 * Lua脚本参数解析器
 */
public interface LuaParameterParser {
    /**
     * 解析参数
     * @param method 接口方法
     * @return 解析后
     */
    LuaScriptParams resolve(Method method, Object[] args);
}
