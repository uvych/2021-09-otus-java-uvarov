package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        buildComponents(configClass);
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    private void buildComponents(Class<?> configClass) {
        var methods = getSortMethodWithAnnotationComponent(configClass);
        try {
            var configInstance = getInstance(configClass);
            buildComponents(methods, configInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buildComponents(List<Method> methods, Object configInstance) throws InvocationTargetException, IllegalAccessException {
        for (Method method : methods) {
            if (method.getParameterCount() == 0) {
                var component = method.invoke(configInstance);
                appComponents.add(component);
                appComponentsByName.put(getComponentName(method), component);
            } else {
                var inputArgs = getMethodParameter(method);
                var component = method.invoke(configInstance, inputArgs);
                appComponents.add(component);
                appComponentsByName.put(getComponentName(method), component);
            }
        }
    }

    private Object[] getMethodParameter(Method method) {
        var types = method.getParameterTypes();
        var inputArgs = new Object[types.length];
        for (int i = 0; i < types.length; i++) {
            inputArgs[i] = getAppComponent(types[i]);
        }
        return inputArgs;
    }

    private String getComponentName(Method method) {
        return method.getAnnotation(AppComponent.class).name();
    }

    private Object getInstance(Class<?> configClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return configClass.cast(configClass.getConstructor().newInstance());
    }

    private List<Method> getSortMethodWithAnnotationComponent(Class<?> configClass) {
        return Arrays.stream(configClass.getMethods())
            .filter(method -> method.isAnnotationPresent(AppComponent.class))
            .sorted(Comparator.comparingInt(method -> method.getAnnotation(AppComponent.class).order())).toList();
    }



    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        for (Object obj : appComponents) {
            if (componentClass.isAssignableFrom(obj.getClass())) {
                return (C)obj;
            }
        }
        throw new NotFoundComponentException();
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        Object obj = appComponentsByName.get(componentName);
        if (obj != null) {
            return (C)obj;
        }
        throw new NotFoundComponentException();
    }
}
