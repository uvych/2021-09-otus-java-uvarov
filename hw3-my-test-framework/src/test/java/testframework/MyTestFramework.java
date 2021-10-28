package testframework;

import annotation.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.logging.*;

public class MyTestFramework {
    private static final Logger log = Logger.getLogger(MyTestFramework.class.getName());

    private static int countSuccessTest;
    private static int countFailedTest;

    static void setClassesAndTest(List<Class<?>> classes) {
        for (Class<?> tClass : classes) {
            startTestsClasses(tClass);
        }
        log.info(countFailedTest + countSuccessTest + ":tests in total   " +
            countFailedTest + ":failed   " + countSuccessTest + ":success");
    }

    private static <T> void startTestsClasses(Class<T> type) {
        TestModel testModel = getTestModel(type);
        for (Method testMethod : testModel.getTestMethod()) {
            log.info("START TEST: " + testMethod.getName());
            try {
                startAllTestsInClass(type, testMethod, testModel);
                log.info("SUCCESS TEST: " + testMethod.getName());
                countSuccessTest ++;
            } catch (Exception ex) {
                countFailedTest ++;
                log.info("TEST FILED: " + testMethod);
            }
        }
    }

    private static <T> void startAllTestsInClass(Class<T> type, Method testMethod, TestModel testModel) {
        T instant = instantiate(type);
        try {
            for (Method beforeMethod : testModel.getBeforeMethod()) {
                callMethod(instant, beforeMethod);
            }
            callMethod(instant, testMethod);
        } finally {
            for (Method afterMethod : testModel.getAfterMethod()) {
                callMethod(instant, afterMethod);
            }
        }
    }

    private static <T> TestModel getTestModel(Class<T> type) {
        TestModel testClass = TestModel.createTestModel();
        Method [] methods = type.getMethods();
        for (Method method : methods) {
            if (method.getAnnotation(Before.class) != null) {
                testClass.addBeforeMethod(method);
            } else if (method.getAnnotation(Test.class) != null) {
                testClass.addTestMethod(method);
            } else if (method.getAnnotation(After.class) != null) {
                testClass.addAfterMethod(method);
            }
        }
        return testClass;
    }

    private static Object callMethod(Object obj, Method method, Object... args) {
        try {
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
}
