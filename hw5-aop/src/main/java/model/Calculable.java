package model;

public interface Calculable {
    default void calculation(int param1) {
        System.out.println(param1);
    }
    default void calculation(int param1, int param2) {
        System.out.println(param1 + param2);
    }
    default void calculation(int param1, int param2, String param3) {
        System.out.println(param1 + param2 + param3);
    }
}
