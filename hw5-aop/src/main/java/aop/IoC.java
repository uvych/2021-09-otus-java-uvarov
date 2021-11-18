package aop;

import annotation.*;
import model.*;

import java.lang.reflect.*;
import java.util.*;

public class IoC {

    private static final Map<String, Integer> methodMap = new HashMap<>();

    private IoC() {

    }

    public static Calculable createIoC(TestLogging obj) {
        Arrays.stream(obj.getClass().getMethods())
            .filter(method -> method.isAnnotationPresent(Log.class))
            .forEach(method -> methodMap.put(method.getName(), method.getParameterCount()));
        InvocationHandler invocationHandler = new DemoInvoke(obj);
        return (Calculable) Proxy.newProxyInstance(IoC.class.getClassLoader(), new Class<?>[]{Calculable.class}, invocationHandler);
    }

    record DemoInvoke(Calculable calculable) implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName = method.getName();
            int parameterCount = method.getParameterCount();
            if (methodMap.containsKey(methodName) && methodMap.get(methodName).equals(parameterCount)) {
                StringBuilder logMessage = new StringBuilder("executed method: calculation, ");
                for (int i = 0; i < args.length; i++) {
                    logMessage
                        .append("param")
                        .append(i)
                        .append(": ")
                        .append(args[i]);
                }
                System.out.println(logMessage);
            }
            return method.invoke(calculable, args);
        }
    }
}
