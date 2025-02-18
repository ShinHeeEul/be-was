package webserver.server;

import java.lang.reflect.Method;

public class ControllerConfig {
    private final Object object;
    private final Method method;


    public ControllerConfig(Object object, Method method) {
        this.object = object;
        this.method = method;
    }
    public Object getObject() {
        return object;
    }

    public Method getMethod() {
        return method;
    }
}
