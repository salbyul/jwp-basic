package core.di.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import com.google.common.collect.Lists;
import core.annotation.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;

public class BeanFactory {
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    private Set<Class<?>> preInstanticateBeans;

    private Map<Class<?>, Object> beans = Maps.newHashMap();

    public BeanFactory(Set<Class<?>> preInstanticateBeans) {
        this.preInstanticateBeans = preInstanticateBeans;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }

    public void initialize() {
        for (Class<?> clazz : preInstanticateBeans) {
            if (beans.get(clazz) == null) {
                instantiateClass(clazz);
            }
        }
    }

    private Object instantiateClass(Class<?> clazz) {
        Object bean = beans.get(clazz);
        if (bean != null) {
            return bean;
        }

        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(clazz);
        if (injectedConstructor == null) {
            bean = BeanUtils.instantiate(clazz);
            beans.put(clazz, bean);
            return bean;
        }

        logger.debug("Constructor: {}", injectedConstructor);
        bean = instantiateConstructor(injectedConstructor);
        beans.put(clazz, bean);
        return bean;
    }

    private Object instantiateConstructor(Constructor<?> constructor) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        ArrayList<Object> args = Lists.newArrayList();
        for (Class<?> parameterType : parameterTypes) {
            Class<?> concreteClass = BeanFactoryUtils.findConcreteClass(parameterType, preInstanticateBeans);
            if (!preInstanticateBeans.contains(concreteClass)) {
                throw new IllegalStateException(concreteClass + "는 Bean이 아니다.");
            }

            Object bean = beans.get(concreteClass);
            if (bean == null) {
                bean = instantiateClass(concreteClass);
            }
            args.add(bean);
        }
        return BeanUtils.instantiateClass(constructor, args.toArray());
    }

    public Map<Class<?>, Object> getControllers() {
        HashMap<Class<?>, Object> controllers = Maps.newHashMap();
        for (Class<?> clazz : preInstanticateBeans) {
            Controller annotation = clazz.getAnnotation(Controller.class);
            if (annotation != null) {
                controllers.put(clazz, beans.get(clazz));
            }
        }
        return controllers;
    }

//    public void initialize() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
//        for (Class<?> preInstanticateBean : preInstanticateBeans) {
//            if (beans.containsKey(preInstanticateBean)) {
//                continue;
//            }
//            registrationBean(preInstanticateBean);
//        }
//    }
//
//    private void registrationBean(final Class<?> preInstanticateBean) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
//        Constructor<?> constructor = BeanFactoryUtils.getInjectedConstructor(preInstanticateBean);
//        if (constructor == null) {
//            Object instance = preInstanticateBean.getConstructor().newInstance();
//            beans.put(preInstanticateBean, instance);
//            return;
//        }
//        Class<?>[] parameterTypes = constructor.getParameterTypes();
//        List<Object> parameters = new ArrayList<>();
//        for (Class<?> parameterType : parameterTypes) {
//            Class<?> concreteClass = BeanFactoryUtils.findConcreteClass(parameterType, preInstanticateBeans);
//            if (Objects.isNull(beans.get(concreteClass))) {
//                registrationBean(concreteClass);
//            }
//            parameters.add(beans.get(concreteClass));
//        }
//        Object instance = constructor.newInstance(parameters.toArray());
//        beans.put(preInstanticateBean, instance);
//    }
}
