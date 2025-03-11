package lua.xgwd.me.core.support;


import lua.xgwd.me.core.LuaMapperFactory;
import lua.xgwd.me.core.annotation.LuaMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gbl.huang
 * @date 2025/03/11 09:38
 **/
@Component
public class LuaMapperProxyFactory implements LuaMapperFactory {

    // 保存扫描到的代理对象，键为接口的 Class
    private Map<Class<?>, Object> mapperProxies = new ConcurrentHashMap<>();

    @Override
    public <T> T getMapper(Class<T> mapperInterface) {
        return (T) mapperProxies.get(mapperInterface);
    }

    @Override
    public void put(Class<?> clazz, Object proxy) {
        mapperProxies.put(clazz, proxy);
    }
}