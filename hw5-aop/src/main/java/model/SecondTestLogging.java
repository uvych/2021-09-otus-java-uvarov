package model;


public class SecondTestLogging implements Calculable{
    @Override
    public void calculation(int param1) {
        System.out.println(param1 + " SECOND_TEST");
    }

    @Override
    public void calculation(int param1, int param2) {
        System.out.println(param1 + " SECOND_TEST");
    }

    @Override
    public void calculation(int param1, int param2, String param3) {
        System.out.println(param1 + param2 + param3 + " SECOND_TEST");
    }
}
