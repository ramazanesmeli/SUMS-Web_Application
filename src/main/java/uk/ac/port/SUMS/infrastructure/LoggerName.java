package uk.ac.port.SUMS.infrastructure;

/**
When injecting an instance of Logger,
this annotation can also be specified, which specifies what the injected Logger's name should be.
@author Reciprocal
*/
public @interface LoggerName{
 String value();
}