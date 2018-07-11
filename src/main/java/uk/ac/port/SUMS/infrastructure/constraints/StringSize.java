package uk.ac.port.SUMS.infrastructure.constraints;
import java.lang.annotation.*;
import javax.validation.*;

/**
Specifies that the number of glyphs in a string must fall into the specified range.
A minimum of 0 specifies no minimum; a negative maximum specifies no maximum.
Null values are allowed.
A glyph is a character that is not a combining character and not an unpaired surrogate;
note this includes unassigned and private-use characters.
A character is a Unicode Code Point.
@author Reciprocal
*/
@Documented
@Constraint(validatedBy=StringSizeValidator.class)
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface StringSize{
 /**
 The string must be at least this many glyphs long, inclusive; 0 specifies no minimum
 */
 int Minimum() default 0;
 /**
 The string must be at most this many glyphs long, inclusive; negative values specify no maximum
 */
 int Maximum() default -1;
 String message() default "Specify a string of a valid length";
 Class<?>[] groups() default {};
 Class<? extends Payload>[] payload() default {};
}