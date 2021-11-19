package model;

import annotation.*;

public class SecondTestLogging implements Calculable{
    @Override
    public void calculation(int param1) {

    }

    @Log
    @Override
    public void calculation(int param1, int param2) {
        System.out.println(param1 + " NEW");
    }

    @Override
    public void calculation(int param1, int param2, String param3) {

    }
}
