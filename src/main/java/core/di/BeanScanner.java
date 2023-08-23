package core.di;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import core.annotation.Repository;
import core.annotation.Service;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import core.annotation.Controller;

public class BeanScanner {
    private static final Logger log = LoggerFactory.getLogger(BeanScanner.class);

    private Reflections reflections;

    public BeanScanner(Object... basePackage) {
        reflections = new Reflections(basePackage);
    }

    @SuppressWarnings("unchecked")
    public Set<Class<?>> scan() {
        return getTypesAnnotatedWith(Controller.class, Repository.class, Service.class);
    }

    Map<Class<?>, Object> instantiateBeans(Set<Class<?>> preInitiatedBeans) {
        Map<Class<?>, Object> beans = Maps.newHashMap();
        try {
            for (Class<?> clazz : preInitiatedBeans) {
                beans.put(clazz, clazz.newInstance());
            }
        } catch (InstantiationException | IllegalAccessException e) {
            log.error(e.getMessage());
        }

        return beans;
    }

    @SuppressWarnings("unchecked")
    private Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation>... annotations) {
        Set<Class<?>> beans = Sets.newHashSet();
        for (Class<? extends Annotation> annotation : annotations) {
            beans.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        return beans;
    }
}
