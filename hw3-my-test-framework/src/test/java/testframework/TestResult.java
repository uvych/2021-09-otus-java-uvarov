package testframework;

import lombok.*;

import java.util.*;

@Builder
@Getter(AccessLevel.PRIVATE)
public class TestResult {
        @Builder.Default
        private boolean isSuccess = true;
        @Builder.Default()
        private String exMessage = "";
        private String testName;

        public static long getSuccessTests(List<TestResult> resultList) {
                return resultList.stream().filter(r -> r.isSuccess).count();
        }

        public static long getFailedTests(List<TestResult> resultList) {
                return resultList.stream().filter(r -> !r.isSuccess).count();
        }

        public static int countAllTests(List<TestResult> resultList) {
                return resultList.size();
        }
}
