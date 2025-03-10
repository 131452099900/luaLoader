package lua.xgwd.me.core.annotation;

import com.sun.org.apache.xerces.internal.dom.AbortException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gbl.huang
 * @date 2025/03/10 11:25
 * Lua脚本执行器接口
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LuaScript {
    // 脚本标识或者名称
    String value();
    // 是否需要缓存，默认true
    boolean cache() default true;
    // 超时时间
    long cacheTtl() default 0L;
    // 日志打印
    boolean isLog() default false;
}
