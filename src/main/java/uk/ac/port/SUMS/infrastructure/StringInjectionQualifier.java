package uk.ac.port.SUMS.infrastructure;
import java.lang.annotation.*;
import javax.inject.*;

/**
This annotation can be used at injection-time as a CDI Injection Qualifier.
It takes a single string value, which is used to distinguish the qualifier from others.
@author Reciprocal
*/
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.PARAMETER,ElementType.TYPE})
public @interface StringInjectionQualifier{
 String value();
}