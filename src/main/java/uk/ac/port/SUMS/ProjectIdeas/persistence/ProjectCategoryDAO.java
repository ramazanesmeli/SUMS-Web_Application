package uk.ac.port.SUMS.ProjectIdeas.persistence;
import java.util.*;
import javax.persistence.*;
import javax.ejb.*;
import uk.ac.port.SUMS.ProjectIdeas.model.*;
import uk.ac.port.SUMS.ProjectIdeas.model.exceptions.*;

/**
DAO/Facade class for reading and creating immutable ProjectCategory entities,
from and to the underlying data store.
@author Reciprocal
*/
@Stateless
public class ProjectCategoryDAO extends AbstractFacade<ProjectCategory>{
 @PersistenceContext(unitName="uk.ac.port.SUMS.PU")
 private EntityManager em;
 public ProjectCategoryDAO(){
  super(ProjectCategory.class);
 }
 
 protected @Override EntityManager getEntityManager(){
  return em;
 }

 public Set<ProjectCategory> ReadAll(){
  return new HashSet<>(super.ReadAll());
 }
 
 public boolean Exists(String ProjectCategoryName){
  TypedQuery<Integer> ExistsQuery=getEntityManager().createNamedQuery("ProjectCategory.Exists",Integer.class);
  ExistsQuery.setParameter("CategoryName",ProjectCategoryName);
  return ExistsQuery.getSingleResult()!=0;
 }
 
 public void Create(ProjectCategory ToCreate){
  super.Create(ToCreate);
 }
 public void Delete(ProjectCategory ToDelete)throws ConcurrencyException{
  super.DeleteInternal(ToDelete);
 }
}