import com.google.common.base.*;

import java.util.*;
import java.util.stream.*;

public class HelloOtus {
    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>();
        stringList.add("Hello");
        stringList.add("Otus!");
        System.out.println(joinString(stringList));
    }

    private static String joinString(List<String> stringList) {
        return Joiner.on(", ").skipNulls().join(Collections.singleton(stringList));
    }
}
