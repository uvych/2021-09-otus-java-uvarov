package test;

import annotation.After;
import annotation.Before;
import annotation.Test;

import java.util.*;

public class ArrayTest {

    private static List<String> testList;

    @Before
    public void init() {
        System.out.println("[Before method2]");
        testList = new ArrayList<>(
            Arrays.asList("Before", "method", "run")
        );
    }

    @Test
    public void happyTest() {
        System.out.println("[Test]");
    }

    @Test
    public void secondHappyTest() {
        System.out.println("[Test2]");
    }

    @After
    public void after() {
        System.out.println("[After2]");
        System.out.println(testList.toString());
    }
}
