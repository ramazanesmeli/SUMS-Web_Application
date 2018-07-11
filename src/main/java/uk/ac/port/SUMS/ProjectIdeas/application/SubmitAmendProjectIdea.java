package uk.ac.port.SUMS.ProjectIdeas.application;
import java.util.*;
import javax.annotation.*;
import javax.ejb.*;
import uk.ac.port.SUMS.ProjectIdeas.model.*;
import uk.ac.port.SUMS.ProjectIdeas.persistence.*;

/**
@author Reciprocal
*/
abstract public class SubmitAmendProjectIdea{
 @EJB
 protected ProjectIdeaDAO DAO;
 @EJB
 protected ProjectCategoryDAO DAOCategories;
 @Resource
 protected SessionContext Transaction;
 protected SubmitAmendProjectIdea(){}
 
 @TransactionAttribute(TransactionAttributeType.SUPPORTS)
 public Set<ProjectCategory> RetrieveAvailableCategories(){
  //TODO Error handling
  return DAOCategories.ReadAll();
 }
}