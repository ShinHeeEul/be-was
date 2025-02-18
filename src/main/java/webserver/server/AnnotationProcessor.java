package webserver.server;

import annotation.Controller;
import annotation.RequestMapping;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Method;
import java.util.*;


public class AnnotationProcessor {
    
    private static final Logger logger = LoggerFactory.getLogger(AnnotationProcessor.class);
    private static final AnnotationProcessor annotationProcessor = new AnnotationProcessor();
    private Map<String, ControllerConfig> requestMapper;

    private AnnotationProcessor() {
        String canonicalName = WebServer.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        List<String> list = getList(canonicalName, canonicalName);
        requestMapper = ImmutableMap.<String,ControllerConfig>builder().build();
        HashMap<String, ControllerConfig> annotationMap = new HashMap<>();

        for (String s : list) {
            try {
                Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass(s.replace("/", ".").substring(1));
                if(clazz.isAnnotationPresent(Controller.class)) {
                    getRequestMappingMethod(clazz, annotationMap);
                }
            } catch (Exception exception) {
                logger.error(exception.getMessage());
            }
        }
        requestMapper = ImmutableMap.<String, ControllerConfig>builder().putAll(annotationMap).build();
    }

    private List<String> getList(final String root, final String path) {
        List<String> result = new ArrayList<>();
        File file = new File(path);
        for (String s : Objects.requireNonNull(file.list())) {
            if (s.contains(".class")) {
                if(path.equals(root)) {
                    continue;
                }
                result.add(path.split(root)[1] + "/" + s.replace(".class", ""));
            }
            if (!s.contains(".class")) {
                result.addAll(getList(root, path + "/" + s));
            }
        }
        return result;
    }

    private static void getRequestMappingMethod(Class<?> clazz, HashMap<String, ControllerConfig> annotationMap) throws Exception {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                annotationMap.put(method.getAnnotation(RequestMapping.class).path(), new ControllerConfig(clazz.getConstructor().newInstance(), method));
            }
        }
    }

    public ControllerConfig getHandler(String url) {
        return requestMapper.get(url);
    }

    public static AnnotationProcessor createAnnotationProcessor() {
        return annotationProcessor;
    }
}
