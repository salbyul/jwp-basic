package core.nmvc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Maps;

import com.google.common.collect.Sets;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.ControllerScanner;
import core.mvc.HandlerMapping;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationHandlerMapping implements HandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final Object[] basePackage;

    private final Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        ControllerScanner scanner = new ControllerScanner(basePackage);
        Map<Class<?>, Object> controllers = scanner.getControllers();
        Set<Method> methods = getRequestMappingMethod(controllers.keySet());
        for (Method method : methods) {
            RequestMapping annotation = method.getAnnotation(RequestMapping.class);
            log.debug("register handlerExecution : url is {}, method is {}", annotation.value(), annotation.method());
            handlerExecutions.put(createHandlerKey(annotation), new HandlerExecution(controllers.get(method.getDeclaringClass()), method));
        }
    }

    @SuppressWarnings("unchecked")
    private Set<Method> getRequestMappingMethod(final Set<Class<?>> controllers) {
        Set<Method> requestMappingMethod = Sets.newHashSet();
        for (Class<?> controller : controllers) {
            requestMappingMethod.addAll(ReflectionUtils.getAllMethods(controller, ReflectionUtils.withAnnotation(RequestMapping.class)));
        }
        return requestMappingMethod;
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
        log.debug("requestUri : {}, requestMethod : {}", request, rm);
        return handlerExecutions.get(new HandlerKey(requestUri, rm));
    }

    private HandlerKey createHandlerKey(RequestMapping rm) {
        return new HandlerKey(rm.value(), rm.method());
    }
}
