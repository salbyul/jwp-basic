package core.mvc;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static org.junit.Assert.*;

public class ControllerScannerTest {

    private static final Logger log = LoggerFactory.getLogger(ControllerScannerTest.class);

    private ControllerScanner scanner;

    @Before
    public void setUp() throws Exception {
        scanner = new ControllerScanner("core.nmvc");
    }

    @Test
    public void getControllers() {
        Map<Class<?>, Object> controllers = scanner.getControllers();
        for (Class<?> controller : controllers.keySet()) {
            log.debug("controller: {}", controller);
        }
    }
}