package uk.ac.port.SUMS.ProjectIdeas.application;
import oyei.SUMS.Registration.entities.*;
import javax.ejb.*;
import javax.persistence.*;
import uk.ac.port.SUMS.ProjectIdeas.model.*;
import uk.ac.port.SUMS.ProjectIdeas.model.exceptions.*;

/**
@author Reciprocal
*/
@Stateless @TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AmendProjectIdea extends SubmitAmendProjectIdea{
 public AmendProjectIdea(){}
 
 public ProjectIdea RetrieveForAmending(String Title,Person For)throws NoEntityFoundException,NotAuthorizedException{
  ProjectIdea Result=DAO.Read(Title);
  if(!Result.canEdit(For)){
   throw new NotAuthorizedException();
  }
  return Result;
 }
 
 //For the JSF presentation consumer, NotAuthorizedException won't be thrown (assuming non-malicious use), but for other presentation layer technologies it might
 @TransactionAttribute(TransactionAttributeType.REQUIRED)
 public ProjectIdea Execute(ProjectIdea Model,Person By)throws ConcurrencyException,NotAuthorizedException,OptimisticLockException{
  if(!Model.canEdit(By)){
   throw new NotAuthorizedException();
  }
  //TODO What happens if not found; other error handling
  return DAO.Update(Model,By);
  //Transaction.setRollbackOnly();
 }
}
