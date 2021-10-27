package test;

import annotation.*;

import java.util.*;

public class ArrayTestFail {

    private static List<String> testList;

    @Before
    public void init() {
        System.out.println("[Before method FAILED]");
        testList = new ArrayList<>(
            Arrays.asList("BeforeFAILED", "methodFAILED", "runFAILED")
        );
    }

    @Test
    public void failTest() throws Exception {
        throw new Exception();
    }

    @After
    public void after() {
        System.out.println("[AfterFAILED]");
        System.out.println(testList.toString());
    }
}
