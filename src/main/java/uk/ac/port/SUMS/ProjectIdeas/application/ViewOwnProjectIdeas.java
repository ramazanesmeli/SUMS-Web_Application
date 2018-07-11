package uk.ac.port.SUMS.ProjectIdeas.application;
import java.util.*;
import javax.ejb.*;
import uk.ac.port.SUMS.ProjectIdeas.model.*;
import oyei.SUMS.Registration.entities.*;
import uk.ac.port.SUMS.ProjectIdeas.persistence.*;

/**
@author Reciprocal
*/
@Stateless
public class ViewOwnProjectIdeas{
 @EJB
 private ProjectIdeaDAO DAO;
 public ViewOwnProjectIdeas(){}
 
 public List<ProjectIdeaSummary> Execute(Person OwnedBy){
  //TODO Any other error handling
  return DAO.ReadOwned(OwnedBy);
 }
}