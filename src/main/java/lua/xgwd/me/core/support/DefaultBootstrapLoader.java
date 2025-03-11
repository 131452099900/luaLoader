package lua.xgwd.me.core.support;

import lua.xgwd.me.core.*;
import lua.xgwd.me.core.annotation.LuaMapper;
import lua.xgwd.me.core.annotation.LuaScript;
import lua.xgwd.me.core.bean.LuaScriptEntity;
import lua.xgwd.me.core.event.RegisterEvent;
import lua.xgwd.me.core.proxy.LuaMapperInvocationHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
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

    @Autowired
    LuaScriptLoader luaScriptLoader;

    @Override
    public void bootstrapScanLuaScript(String packageName) {

    }

    private String basePackage;

    @Autowired
    private LuaMapperFactory factory;

    @Autowired
    private LuaScriptExecutor executor;

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

                    LuaScriptEntity luaScriptEntity = luaScriptLoader.loadScript(scriptId);
                    applicationContext.publishEvent(new RegisterEvent(scriptId, luaScriptEntity));
                    methodScriptMapping.put(method, scriptId);
                }
            }

            // TODO 交给代理Factory处理
            // 生成代理对象
            Object proxyInstance = Proxy.newProxyInstance(
                    mapperInterface.getClassLoader(),
                    new Class<?>[]{mapperInterface}, // 父类接口
                    new LuaMapperInvocationHandler(methodScriptMapping, executor)
            );

            // 保存代理对象，后续业务中可通过 getMapper 获取
            factory.put(mapperInterface, proxyInstance);

            // 注入容器
            injectContext(proxyInstance);
        }
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

    private void injectContext(Object obj) {
        Class<?> aClass = obj.getClass();
        DefaultListableBeanFactory beanFactory =(DefaultListableBeanFactory ) applicationContext.getAutowireCapableBeanFactory();
        // 获取 BeanFactory
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) applicationContext;
        System.out.println(obj.getClass().getName() + " hahahah   ");
        System.out.println(obj.getClass().getGenericInterfaces()[0].getTypeName());
        // 代理的父类是接口
        beanFactory.registerSingleton(obj.getClass().getName(), obj);

    }
}