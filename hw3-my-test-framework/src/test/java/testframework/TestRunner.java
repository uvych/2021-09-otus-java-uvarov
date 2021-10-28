package testframework;

import test.*;

import java.util.*;

public class TestRunner {
    public static void main(String[] args) {
            MyTestFramework.setClassesAndTest(Arrays.asList(ArrayTest.class, ArrayTestFail.class));
    }
}
