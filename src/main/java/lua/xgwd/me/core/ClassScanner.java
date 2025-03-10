package lua.xgwd.me.core;

import java.lang.annotation.Annotation;
import java.util.Set;

public interface ClassScanner {
    Set<Class<?>> scanForAnnotation(String basePackage, Class<? extends Annotation> annotationType);
}
