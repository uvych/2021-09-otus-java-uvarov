package annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Before {
}
