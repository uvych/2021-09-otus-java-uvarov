package testframework;

import lombok.*;

import java.lang.reflect.*;
import java.util.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TestModel {
    private List<Method> beforeMethod;
    private List<Method> afterMethod;
    private List<Method> testMethod;

    public static TestModel createTestModel() {
        return new TestModel(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public void addBeforeMethod(Method method) {
        this.beforeMethod.add(method);
    }

    public void addAfterMethod(Method method) {
        this.afterMethod.add(method);
    }

    public void addTestMethod(Method method) {
        this.testMethod.add(method);
    }


}
