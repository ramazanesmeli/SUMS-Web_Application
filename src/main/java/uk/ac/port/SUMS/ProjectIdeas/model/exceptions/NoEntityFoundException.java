package uk.ac.port.SUMS.ProjectIdeas.model.exceptions;
import javax.ejb.*;

/**
No transaction rollback.
Typically thrown when trying to retrieve an entity with a specific ID,
and the entity was not found.
@author Reciprocal
*/
@ApplicationException
public class NoEntityFoundException extends Exception{
 public NoEntityFoundException(){}
 public NoEntityFoundException(String msg){
  super(msg);
 }
}