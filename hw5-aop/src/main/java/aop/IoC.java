package aop;

import annotation.*;
import model.*;

import java.lang.reflect.*;
import java.util.*;

public class IoC {

    private IoC() {

    }

    public static Calculable createIoC(Calculable obj) {
        var methodsContainer = getMethodContainer(obj);
        if (methodsContainer.isEmpty()) return obj;
        InvocationHandler invocationHandler = new DemoInvoke(obj, obj.getClass(), methodsContainer);
        return (Calculable) Proxy.newProxyInstance(IoC.class.getClassLoader(), new Class<?>[]{Calculable.class}, invocationHandler);
    }

    private static Set<Method> getMethodContainer(Calculable obj) {
        Set<Method> methodsContainer = new HashSet<>(){
            @Override
            public boolean contains(Object o) {
                if (!(o instanceof Method method)) return false;

                for (Method m : this) {
                    if (method.getName().equals(m.getName())
                        && Arrays.equals(method.getParameterTypes(), m.getParameterTypes())
                        && method.getParameterCount() == m.getParameterCount()) {
                        return true;
                    }
                }
                return false;
            }
        };

        Arrays.stream(obj.getClass().getMethods())
            .filter(method -> method.isAnnotationPresent(Log.class))
            .forEach(methodsContainer::add);

        return methodsContainer;
    }

    record DemoInvoke(Calculable calculable, Class<?> methodClass, Set<Method> methodsContainer) implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (methodsContainer.contains(method)) {
                System.out.println(getLogMessage(method, args));
            }
            return method.invoke(calculable, args);
        }

        private String getLogMessage(Method method, Object [] args) {
            var logMessage = new StringBuilder("executed method: ");
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

            return logMessage.toString();
        }
    }
}
