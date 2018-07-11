package uk.ac.port.SUMS.ProjectIdeas.model.exceptions;
import javax.ejb.*;

/**
No transaction rollback.
Typically thrown when trying to create an entity that is not unique,
according to some constraint.
@author Reciprocal
*/
@ApplicationException(rollback=false)
public class AlreadyExistsException extends Exception{
 public AlreadyExistsException(){}
 public AlreadyExistsException(String msg){
  super(msg);
 }
}