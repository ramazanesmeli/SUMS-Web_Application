package uk.ac.port.SUMS.ProjectIdeas.application;
import javax.ejb.*;
import java.util.logging.*;
import javax.inject.*;
import uk.ac.port.SUMS.ProjectIdeas.model.*;
import oyei.SUMS.Registration.entities.*;
import java.text.*;
import javax.persistence.*;
import uk.ac.port.SUMS.ProjectIdeas.model.exceptions.*;
import uk.ac.port.SUMS.ProjectIdeas.persistence.*;

/**
Note that ProjectIdea.canWithdrawAndUndo → ProjectIdea.canEdit;
thus presentation layers may re-use the reading logic from AmendProjectIdea to initially retrieve ProjectIdea models,
and incorporate the Withdrawing and UnWithdrawing use cases into presentations from the Amend ProjectIdea activity.
@author Reciprocal
*/
@Stateless
public class WithdrawProjectIdea{
 @EJB
 private ProjectIdeaDAO DAO;
 @Inject
 private Logger Log;
 public WithdrawProjectIdea(){}
 
 public ProjectIdea Execute(ProjectIdea Model,Person By)throws ConcurrencyException,NotAuthorizedException,OptimisticLockException{
  if(!Model.canWithdrawAndUndo(By)){
   throw new NotAuthorizedException();
  }
  if(!Model.isWithdrawable()){
   Log.warning(MessageFormat.format("Attempt to Withdraw ProjectIdea \"{0}\" by user {1} which is not in a withdrawable state",Model.getTitle(),By.getUsername()));
   return Model;
  }
  Model.Withdraw(By);
  //TODO What happens if not found; other error handling
  return DAO.Update(Model,By);
 }
 
 public ProjectIdea ExecuteUndo(ProjectIdea Model,Person By)throws ConcurrencyException,NotAuthorizedException,OptimisticLockException{
  if(!Model.canWithdrawAndUndo(By)){
   throw new NotAuthorizedException();
  }
  if(!Model.isUnWithdrawable()){
   Log.warning(MessageFormat.format("Attempt to UnWithdraw ProjectIdea \"{0}\" by user {1} which is not in an unwithdrawable state",Model.getTitle(),By.getUsername()));
   return Model;
  }
  Model.UnWithdraw(By);
  //TODO What happens if not found; other error handling
  return DAO.Update(Model,By);
 }
}