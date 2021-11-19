package aop;

import annotation.*;
import model.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.*;

public class IoC {

    private static final Set<MethodInfo> methodsContainer = new HashSet<>();

    private IoC() {

    }

    public static Calculable createIoC(Calculable obj) {
        Arrays.stream(obj.getClass().getMethods())
            .filter(method -> method.isAnnotationPresent(Log.class))
            .forEach(method -> methodsContainer.add(
                MethodInfo.builder()
                    .aClass(method.getDeclaringClass().getName())
                    .countParam(method.getParameterCount())
                    .name(method.getName())
                    .paramType(method.getParameterTypes())
                    .build())
            );
        InvocationHandler invocationHandler = new DemoInvoke(obj, obj.getClass());
        return (Calculable) Proxy.newProxyInstance(IoC.class.getClassLoader(), new Class<?>[]{Calculable.class}, invocationHandler);
    }

    record DemoInvoke(Calculable calculable, Class<?> methodClass) implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            for (MethodInfo methodInfo : methodsContainer) {
                if (methodInfo.isMethodExists(method) && methodInfo.getAClass().equals(methodClass.getName())) {
                    StringBuilder logMessage = new StringBuilder("executed method: ");
                    logMessage
                        .append(method.getName())
                        .append(methodClass);
                    for (int i = 0; i < args.length; i++) {
                        logMessage
                            .append(" param")
                            .append(i)
                            .append("=")
                            .append(args[i]);
                    }
                    System.out.println(logMessage);
                }
            }
            return method.invoke(calculable, args);
        }
    }
}
