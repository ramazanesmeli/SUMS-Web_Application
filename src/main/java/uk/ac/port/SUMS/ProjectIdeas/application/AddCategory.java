package uk.ac.port.SUMS.ProjectIdeas.application;
import javax.annotation.*;
import javax.ejb.*;
import uk.ac.port.SUMS.ProjectIdeas.model.*;
import oyei.SUMS.Registration.entities.*;
import uk.ac.port.SUMS.ProjectIdeas.model.exceptions.*;
import uk.ac.port.SUMS.ProjectIdeas.persistence.*;

/**
@author Reciprocal
*/
@Stateless
public class AddCategory{
 @EJB
 private ProjectCategoryDAO DAO;
 @Resource
 private SessionContext Transaction;
 public AddCategory(){}
 
 public void Execute(ProjectCategory Add,Person By)throws AlreadyExistsException,NotAuthorizedException{
  if(!Add.canCreate(By)){
   throw new NotAuthorizedException();
  }
  if(DAO.Exists(Add.getName())){
   Transaction.setRollbackOnly();
   throw new AlreadyExistsException();
  }
  DAO.Create(Add);
 }
}