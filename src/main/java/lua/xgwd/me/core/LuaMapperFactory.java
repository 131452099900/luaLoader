package lua.xgwd.me.core;

/**
 * @author gbl.huang
 * @date 2025/03/11 09:37
 **/
public interface LuaMapperFactory {
    /**
     * 获取代理对象
     * @param mapperInterface 接口名称
     * @param <T>   代理对象
     * @return 代理对象
     */
    <T> T getMapper(Class<T> mapperInterface);

    void put(Class<?> clazz, Object proxy);
}