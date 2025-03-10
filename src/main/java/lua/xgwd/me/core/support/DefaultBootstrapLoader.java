package lua.xgwd.me.core.support;

import lua.xgwd.me.core.BootStrapLuaScriptLoader;
import lua.xgwd.me.core.ClassScanner;
import lua.xgwd.me.core.annotation.LuaMapper;
import lua.xgwd.me.core.annotation.LuaScript;
import lua.xgwd.me.core.event.RegisterEvent;
import lua.xgwd.me.core.proxy.LuaMapperInvocationHandler;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author gbl.huang
 * @date 2025/03/10 17:45
 * 默认解析器 解析xxx.lua
 **/
@Component
public class DefaultBootstrapLoader implements BootStrapLuaScriptLoader,
        ApplicationContextAware, ApplicationListener<ApplicationReadyEvent> {

    ApplicationContext applicationContext;

    ClassScanner classScanner;

    @Override
    public void bootstrapScanLuaScript(String packageName) {

    }

    private String basePackage;
    // 保存扫描到的代理对象，键为接口的 Class
    private Map<Class<?>, Object> mapperProxies = new HashMap<>();

    public DefaultBootstrapLoader(ClassScanner classScanner) {
        this.classScanner = classScanner;
    }

    /**
     * 启动加载过程
     */
    public void load() {
        // 1. 扫描basePackage下所有带有@LuaMapper注解的接口
        Set<Class<?>> mapperInterfaces = classScanner.scanForAnnotation(basePackage, LuaMapper.class);

        // 2. 遍历每个接口，解析方法注解并生成代理对象
        for (Class<?> mapperInterface : mapperInterfaces) {
            System.out.println(mapperInterface.getName());
            // 解析该接口中每个方法上@LuaScript注解的脚本ID映射
            Map<Method, String> methodScriptMapping = new HashMap<>();
            for (Method method : mapperInterface.getDeclaredMethods()) {
                if (method.isAnnotationPresent(LuaScript.class)) {
                    LuaScript annotation = method.getAnnotation(LuaScript.class);
                    String scriptId = annotation.value();
                    System.out.println(scriptId);
                    applicationContext.publishEvent(new RegisterEvent(scriptId));
                    methodScriptMapping.put(method, scriptId);
                }
            }

            // TODO 交给代理Factory处理
            // 生成代理对象
            Object proxyInstance = Proxy.newProxyInstance(
                    mapperInterface.getClassLoader(),
                    new Class<?>[]{mapperInterface},
                    new LuaMapperInvocationHandler(methodScriptMapping)
            );
            // 保存代理对象，后续业务中可通过 getMapper 获取
            mapperProxies.put(mapperInterface, proxyInstance);
        }
    }

    //    /**
//     * 获取某个LuaMapper接口的代理实例
//     *
//     * @param mapperInterface 接口类型
//     * @param <T>             泛型类型
//     * @return 代理对象实例
//     */
    public <T> T getMapper(Class<T> mapperInterface) {
        return (T) mapperProxies.get(mapperInterface);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        String name = applicationReadyEvent.getSpringApplication().getMainApplicationClass().getPackage().getName();
        System.out.println(name);
        basePackage = name;
        System.out.println(basePackage);
        load();
    }
}