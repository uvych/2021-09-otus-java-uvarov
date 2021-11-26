import aop.*;
import model.*;

public class App {
    public static void main(String[] args) {
        Calculable calculable = IoC.createIoC(new TestLogging());

        calculable.calculation(1);
        calculable.calculation(1, 1);
        calculable.calculation(1, 1, "java");

        Calculable calculable1 = IoC.createIoC(new SecondTestLogging());

        calculable1.calculation(4);
        calculable1.calculation(4, 4);
        calculable1.calculation(4, 4, "java");
    }
}
