package uk.ac.port.SUMS.ProjectIdeas.application;
import java.util.*;
import javax.ejb.*;
import uk.ac.port.SUMS.ProjectIdeas.model.*;
import uk.ac.port.SUMS.ProjectIdeas.persistence.*;

/**
@author Reciprocal
*/
@Stateless
public class ViewCategories{
 @EJB
 private ProjectCategoryDAO DAO;
 public ViewCategories(){}
 
 public Set<ProjectCategory> Execute(){
  return DAO.ReadAll();
 }
}