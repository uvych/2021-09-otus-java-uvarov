package model;

import lombok.*;

import java.lang.reflect.*;
import java.util.*;

@Builder
@Getter
@EqualsAndHashCode
public class MethodInfo {
    private String aClass;
    private int countParam;
    private String name;
    private Class<?> [] paramType;

    public boolean isMethodExists(Method method) {
        return countParam == method.getParameterCount() &&
            name.equals(method.getName()) && Arrays.equals(paramType, method.getParameterTypes());
    }
}
