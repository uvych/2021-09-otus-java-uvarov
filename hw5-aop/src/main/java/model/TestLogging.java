package model;

import annotation.*;

public class TestLogging implements Calculable{
    @Log
    @Override
    public void calculation(int param1) {
        Calculable.super.calculation(param1);
    }

    @Override
    public void calculation(int param1, int param2) {
        Calculable.super.calculation(param1, param2);
    }

    @Log
    @Override
    public void calculation(int param1, int param2, String param3) {
        Calculable.super.calculation(param1, param2, param3);
    }
}
