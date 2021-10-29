package testframework;

import test.*;

public class TestRunner {
    public static void main(String[] args) {
            MyTestFramework.setClassesAndTest(ArrayTest.class, ArrayTestFail.class);
    }
}
