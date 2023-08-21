package core.mvc.view;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

    private View view;
    private Map<String, Object> model = new HashMap<>();

    public ModelAndView(final View view) {
        this.view = view;
    }

    public ModelAndView addObject(final String attributeName, final Object attributeValue) {
        model.put(attributeName, attributeValue);
        return this;
    }

    public Map<String, Object> getModel() {
        return Collections.unmodifiableMap(model);
    }

    public View getView() {
        return this.view;
    }
}
