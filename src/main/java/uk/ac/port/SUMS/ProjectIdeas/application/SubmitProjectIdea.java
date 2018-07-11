package uk.ac.port.SUMS.ProjectIdeas.application;
import javax.ejb.*;
import uk.ac.port.SUMS.ProjectIdeas.model.*;
import oyei.SUMS.Registration.entities.*;
import uk.ac.port.SUMS.ProjectIdeas.model.exceptions.*;

/**
@author Reciprocal
*/
@Stateless @TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class SubmitProjectIdea extends SubmitAmendProjectIdea{
 public SubmitProjectIdea(){}
 
 @TransactionAttribute(TransactionAttributeType.REQUIRED)
 public void Execute(ProjectIdea Model,Person By)throws AlreadyExistsException{
  if(DAO.Exists(Model.getTitle())){
   Transaction.setRollbackOnly();
   throw new AlreadyExistsException();
  }
  //TODO Error handling
  DAO.Create(Model,By);
 }
}
