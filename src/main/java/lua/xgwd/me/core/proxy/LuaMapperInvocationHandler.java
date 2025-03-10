package lua.xgwd.me.core.proxy;

/**
 * @author gbl.huang
 * @date 2025/03/10 22:56
 **/
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

public class LuaMapperInvocationHandler implements InvocationHandler {

    // 方法与Lua脚本ID的映射关系
    private final Map<Method, String> methodScriptMapping;

    public LuaMapperInvocationHandler(Map<Method, String> methodScriptMapping) {
        this.methodScriptMapping = methodScriptMapping;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 针对Object的方法（例如toString、hashCode）直接处理
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(this, args);
        }
        // 获取方法对应的Lua脚本ID
        String scriptId = methodScriptMapping.get(method);
        if (scriptId == null) {
            throw new UnsupportedOperationException("Method " + method.getName() + "没有配置@LuaScript注解");
        }
        // 示例逻辑：
        // 1. 利用参数解析器解析方法参数（可以调用一个LuaParameterResolver）
        // 2. 根据脚本ID通过Registry或者Loader获取Lua脚本
        // 3. 调用LuaScriptExecutor执行脚本并返回结果
        // 这里仅演示调用过程，具体实现需要依赖你现有的执行器、解析器等组件
        System.out.println("执行Lua脚本，ID：" + scriptId);
        // 例如：Object result = LuaScriptExecutorHolder.getExecutor().execute(scriptId, args);
        // 这里只是返回null作为示例
        return null;
    }
}
