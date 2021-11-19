import aop.*;
import model.*;

public class App {
    public static void main(String[] args) {
        Calculable testLogging = IoC.createIoC(new TestLogging());
        Calculable secondTestLogging = IoC.createIoC(new SecondTestLogging());

        secondTestLogging.calculation(1);
        secondTestLogging.calculation(1, 3);
        secondTestLogging.calculation(1 ,3 , "Str");
        testLogging.calculation(1);
        testLogging.calculation(1,2);
        testLogging.calculation(1, 2, "Str");
    }
}
