package core.di.injector;

import core.di.factory.BeanFactory;
import core.di.factory.BeanFactoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Set;

public class FieldInjector extends AbstractInjector {

    private static final Logger log = LoggerFactory.getLogger(FieldInjector.class);

    public FieldInjector(final BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    Set<?> getInjectedBeans(final Class<?> clazz) {
        return BeanFactoryUtils.getInjectedFields(clazz);
    }

    @Override
    Class<?> getBeanClass(final Object injectedBean) {
        Field field = (Field) injectedBean;
        return field.getType();
    }

    @Override
    void inject(final Object injectedBean, final Object bean, final BeanFactory beanFactory) {
        Field field = (Field) injectedBean;
        try {
            field.setAccessible(true);
            field.set(beanFactory.getBean(field.getDeclaringClass()), bean);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            log.debug(e.getMessage());
        }
    }
}
