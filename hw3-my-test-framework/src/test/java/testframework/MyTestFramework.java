package testframework;

import annotation.*;
import test.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.logging.*;

public class MyTestFramework {
    private static final Logger log = Logger.getLogger(MyTestFramework.class.getName());

    private static final List<Method> beforeMethods = new ArrayList<>();
    private static final List<Method> testMethods = new ArrayList<>();
    private static final List<Method> afterMethods = new ArrayList<>();
    private static int countSuccessTest;
    private static int countFailedTest;

    public static void main(String[] args) {
        setTestsClass(Arrays.asList(ArrayTest.class, ArrayTestFail.class));
    }

    private static void setTestsClass(List<Class<?>> classes) {
        for (Class<?> tClass : classes) {
            start(tClass);
        }
        log.info(countFailedTest + countSuccessTest + ":tests in total   " +
            countFailedTest + ":failed   " + countSuccessTest + ":success");
    }

    private static <T> void start(Class<T> type) {
        getMethodsWithAnnotation(type);
        for (Method testMethod : testMethods) {
            log.info("START TEST: " + testMethod.getName());
            try {
                startTest(type, testMethod);
                log.info("SUCCESS TEST: " + testMethod.getName());
                countSuccessTest ++;
            } catch (Exception ex) {
                countFailedTest ++;
                log.info("TEST FILED: " + testMethod);
            }
        }
    }

    private static <T> void startTest(Class<T> type, Method testMethod) {
        T instant = instantiate(type);

        for (Method beforeMethod : beforeMethods) {
            callMethod(instant, beforeMethod.getName());
        }

        try {
            callMethod(instant, testMethod.getName());
        } catch (RuntimeException ex) {
            afterMethodsCall(instant);
            throw ex;
        }

        afterMethodsCall(instant);
    }

    private static void cleanMethodsList() {
        beforeMethods.clear();
        afterMethods.clear();
        testMethods.clear();
    }

    private static <T> void afterMethodsCall(T instant) {
        for (Method afterMethod : afterMethods) {
            callMethod(instant, afterMethod.getName());
        }
    }

    private static <T> void getMethodsWithAnnotation(Class<T> type) {
        cleanMethodsList();
        Method [] methods = type.getMethods();
        for (Method method : methods) {
            if (method.getAnnotation(Before.class) != null) {
                beforeMethods.add(method);
            } else if (method.getAnnotation(Test.class) != null) {
                testMethods.add(method);
            } else if (method.getAnnotation(After.class) != null) {
                afterMethods.add(method);
            }
        }
    }

    private static Object callMethod(Object obj, String name, Object... args) {
        try {
            var method = obj.getClass().getDeclaredMethod(name, toClasses(args));
            method.setAccessible(true);
            return method.invoke(obj, args);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static <T> T instantiate(Class<T> type, Object... args) {
        try {
            if (args.length == 0) {
                return type.getDeclaredConstructor().newInstance();
            } else {
                Class<?>[] classes = toClasses(args);
                return type.getDeclaredConstructor(classes).newInstance(args);
            }
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    private static Class<?>[] toClasses(Object[] args) {
        return Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);
    }

    private static void setFieldValue(Object obj, String name, Object value) {
        try {
            var field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    private static Object getFieldValue(Object obj, String name) {
        try {
            var field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }
}
