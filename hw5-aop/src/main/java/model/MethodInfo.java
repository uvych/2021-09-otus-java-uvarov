package model;

import lombok.*;

import java.lang.reflect.*;
import java.util.*;

@Builder
@Getter
@EqualsAndHashCode
public class MethodInfo {
    private String aClass;
    private String interfaceClass;
    private int countParam;
    private String name;
    private Class<?> [] paramType;

    public boolean hasSomeMethod(Method method) {
        return countParam == method.getParameterCount() &&
            name.equals(method.getName()) && Arrays.equals(paramType, method.getParameterTypes());
    }

    public boolean hasSomeClass(Class<?> clazz) {
        return aClass.equals(clazz.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Method method) {
            return countParam == method.getParameterCount() &&
                name.equals(method.getName()) && Arrays.equals(paramType, method.getParameterTypes());
        }
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodInfo that = (MethodInfo) o;
        return countParam == that.countParam && Objects.equals(aClass, that.aClass) &&
            Objects.equals(interfaceClass, that.interfaceClass) &&
            Objects.equals(name, that.name) &&
            Arrays.equals(paramType, that.paramType);
    }
}
