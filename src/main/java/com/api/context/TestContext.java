package com.api.context;

import java.util.HashMap;
import java.util.Map;

public class TestContext {

    private Map<Context, Object> contextMap = new HashMap<Context, Object>();

    public void setContext(Context context, Object value) {
        contextMap.put(context, value);
    }

    public Object getContext(Context context) {
        return contextMap.get(context);
    }
}
