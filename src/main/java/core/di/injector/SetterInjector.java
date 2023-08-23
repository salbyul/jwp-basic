package core.di.injector;

import core.di.factory.BeanFactory;
import core.di.factory.BeanFactoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class SetterInjector extends AbstractInjector {

    private static final Logger log = LoggerFactory.getLogger(SetterInjector.class);

    public SetterInjector(final BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    Set<?> getInjectedBeans(final Class<?> clazz) {
        return BeanFactoryUtils.getInjectedMethods(clazz);
    }

    @Override
    Class<?> getBeanClass(final Object injectedBean) {
        Method method = (Method) injectedBean;
        log.debug("invoke method: {}", method);
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length != 1) {
            throw new IllegalStateException("DI할 메소드 인자는 하나여야 합니다.");
        }
        return parameterTypes[0];
    }

    @Override
    void inject(final Object injectedBean, final Object bean, final BeanFactory beanFactory) {
        Method method = (Method) injectedBean;
        try {
            method.invoke(beanFactory.getBean(method.getDeclaringClass()), bean);
        } catch (IllegalArgumentException | IllegalAccessException |InvocationTargetException e) {
            log.error(e.getMessage());
        }
    }
}
