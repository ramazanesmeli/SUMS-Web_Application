package uk.ac.port.SUMS.ProjectIdeas.model.exceptions;
import javax.ejb.*;

/**
No transaction rollback.
Thrown when updating an entity that has been modified elsewhere, or deleted,
since the originally read entity was read.
@author Reciprocal
*/
@ApplicationException(rollback=false)
public class ConcurrencyException extends Exception{
 public ConcurrencyException(){}
 public ConcurrencyException(String msg){
  super(msg);
 }
}