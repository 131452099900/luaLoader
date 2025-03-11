package lua.xgwd.me.core.support;

import lua.xgwd.me.core.LuaParameterParser;
import lua.xgwd.me.core.bean.LuaScriptParams;
import lua.xgwd.me.core.exception.RedisScriptExecutionException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

/**
 * @author gbl.huang
 * @date 2025/03/11 09:47
 **/
@Component
public class DefaultParamParser implements LuaParameterParser {
    @Override
    public LuaScriptParams resolve(Method method, Object[] params) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (params.length < 2) {
            throw new RedisScriptExecutionException("mapper接口方法参数符合规范, 长度序为2: keys, args");
        }

        if (!params[0].getClass().isArray() || params[0].getClass().getComponentType() != String.class
                || !params[1].getClass().isArray() || params[1].getClass().getComponentType() != String.class) {
            throw new RedisScriptExecutionException("mapper接口方法参数不符合规范, 需为string数组  为2: keys, args");
        }

        String[] keys = (String[]) params[0];
        String[] args = (String[]) params[1];

        return new LuaScriptParams(keys, args);
    }
}