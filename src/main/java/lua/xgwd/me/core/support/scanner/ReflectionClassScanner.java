package lua.xgwd.me.core.support.scanner;

import lua.xgwd.me.core.ClassScanner;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author gbl.huang
 * @date 2025/03/10 22:53
 **/
@Component
public class ReflectionClassScanner implements ClassScanner {
    @Override
    public Set<Class<?>> scanForAnnotation(String basePackage, Class<? extends Annotation> annotationType) {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(basePackage))
                .filterInputsBy(new FilterBuilder().includePackage(basePackage))
        );
        return reflections.getTypesAnnotatedWith(annotationType);
    }
}