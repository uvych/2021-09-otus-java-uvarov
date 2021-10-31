package testframework;

import annotation.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.logging.*;

public class MyTestFramework {
    private static final Logger log = Logger.getLogger(MyTestFramework.class.getName());

    static void setClassesAndTest(Class<?> ...classes) {
        List<TestResult> resultList = new ArrayList<>();

        for (Class<?> tClass : classes) {
            resultList.addAll(startAllTestsInClass(tClass));
        }

        log.info(TestResult.countAllTests(resultList) + ":tests in total   " +
            TestResult.getFailedTests(resultList) + ":failed   " + TestResult.getSuccessTests(resultList) + ":success");
    }

    private static <T> List<TestResult> startAllTestsInClass(Class<T> type) {
        TestModel testModel = getTestModel(type);
        List<TestResult> resultList = new ArrayList<>();
        for (Method testMethod : testModel.getTestMethod()) {
            log.info("START TEST: " + testMethod.getName());
            try {
                startTestInClass(type, testMethod, testModel);
                log.info("SUCCESS TEST: " + testMethod.getName());
                resultList.add(TestResult.builder()
                        .testName(testMethod.getName()).build()
                );
            } catch (Exception ex) {
                resultList.add(TestResult.builder()
                        .testName(testMethod.getName())
                        .isSuccess(false)
                        .exMessage(ex.getMessage()).build()
                );
                log.info("TEST FILED: " + testMethod);
            }
        }
        return resultList;
    }

    private static <T> void startTestInClass(Class<T> type, Method testMethod, TestModel testModel) {
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

    private static void callMethod(Object obj, Method method, Object... args) {
        try {
            method.setAccessible(true);
            method.invoke(obj, args);
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
