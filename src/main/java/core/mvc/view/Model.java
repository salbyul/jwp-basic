package core.mvc.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Model {

    private final Map<String, Object> models;

    public Model() {
        this.models = new HashMap<>();
    }

    public void add(final String key, final Object value) {
        this.models.put(key, value);
    }

    public Set<Map.Entry<String, Object>> getEntrySet() {
        return models.entrySet();
    }
}
