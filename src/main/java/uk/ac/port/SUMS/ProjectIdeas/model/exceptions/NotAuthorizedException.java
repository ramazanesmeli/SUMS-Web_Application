package uk.ac.port.SUMS.ProjectIdeas.model.exceptions;
import javax.ejb.*;

/**
Causes transaction rollback.
Thrown when a user attempts to do or view something
for which they have no authorization.
@author Reciprocal
*/
@ApplicationException(rollback=true)
public class NotAuthorizedException extends Exception{
 public NotAuthorizedException(){}
 public NotAuthorizedException(String msg){
  super(msg);
 }
}