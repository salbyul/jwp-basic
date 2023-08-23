package core.nmvc;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.lang.reflect.InvocationTargetException;

public class AnnotationHandlerMappingTest {
    private AnnotationHandlerMapping handlerMapping;

    @Before
    public void setup() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        handlerMapping = new AnnotationHandlerMapping("core.nmvc");
        handlerMapping.initialize();
    }

    @Test
    public void getHandler() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/users/findUserId");
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerExecution execution = handlerMapping.getHandler(request);
        execution.handle(request, response);
    }
}
