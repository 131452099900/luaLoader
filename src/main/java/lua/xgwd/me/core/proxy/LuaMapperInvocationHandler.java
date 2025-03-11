package lua.xgwd.me.core.proxy;

/**
 * @author gbl.huang
 * @date 2025/03/10 22:56
 **/
import lua.xgwd.me.core.LuaParameterParser;
import lua.xgwd.me.core.LuaScriptExecutor;
import lua.xgwd.me.core.bean.LuaScriptParams;
import lua.xgwd.me.core.support.DefaultParamParser;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;


public class LuaMapperInvocationHandler implements InvocationHandler {

    // 方法与Lua脚本ID的映射关系
    private final Map<Method, String> methodScriptMapping;

    LuaParameterParser parameterParser = new DefaultParamParser();

    LuaScriptExecutor luaScriptExecutor;

    public LuaMapperInvocationHandler(Map<Method, String> methodScriptMapping, LuaScriptExecutor executor) {
        this.methodScriptMapping = methodScriptMapping;
        luaScriptExecutor = executor;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // method是抽象方法
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(this, args);
        }
        // 获取方法对应的Lua脚本ID
        String scriptId = methodScriptMapping.get(method);
        if (scriptId == null) {
            throw new UnsupportedOperationException("Method " + method.getName() + "没有配置@LuaScript注解");
        }

        LuaScriptParams params = parameterParser.resolve(method, args);

        Class<?> returnType = method.getReturnType();

        // 例如：Object result = LuaScriptExecutorHolder.getExecutor().execute(scriptId, args);
        // 这里只是返回null作为示例
        return luaScriptExecutor.execute(returnType, scriptId, params);
    }
}
