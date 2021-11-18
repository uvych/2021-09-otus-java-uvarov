import aop.*;
import model.*;

public class App {
    public static void main(String[] args) {
        Calculable calculable = IoC.createIoC(new TestLogging());

        calculable.calculation(1);
        calculable.calculation(1,2);
        calculable.calculation(1, 2, "Str");
    }
}
