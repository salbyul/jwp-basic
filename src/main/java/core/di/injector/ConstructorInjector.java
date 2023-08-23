package core.di.injector;

import com.google.common.collect.Sets;
import core.di.factory.BeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class ConstructorInjector extends AbstractInjector {

    private static final Logger log = LoggerFactory.getLogger(ConstructorInjector.class);

    public ConstructorInjector(final BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    Set<?> getInjectedBeans(final Class<?> clazz) {
        return Sets.newHashSet();
    }

    @Override
    Class<?> getBeanClass(final Object injectedBean) {
        return null;
    }

    @Override
    void inject(final Object injectedBean, final Object bean, final BeanFactory beanFactory) {
    }
}
