package uk.ac.port.SUMS.ProjectIdeas.application;
import javax.ejb.*;
import uk.ac.port.SUMS.ProjectIdeas.model.*;
import oyei.SUMS.Registration.entities.*;
import uk.ac.port.SUMS.ProjectIdeas.model.exceptions.*;
import uk.ac.port.SUMS.ProjectIdeas.persistence.*;

/**
@author Reciprocal
*/
@Stateless
public class RemoveCategory{
 @EJB
 private ProjectCategoryDAO DAO;
 public RemoveCategory(){}
 
 public void Execute(ProjectCategory Remove,Person By)throws NotAuthorizedException{
  if(!Remove.canDelete(By)){
   throw new NotAuthorizedException();
  }
  try{
   DAO.Delete(Remove);
  }catch(ConcurrencyException Error){
   //ProjectCategory entities are immutable; should only be thrown if the ProjectCategory has been concurrently deleted
  }
 }
}