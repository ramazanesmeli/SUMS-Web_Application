package uk.ac.port.SUMS.ProjectIdeas.persistence;
import java.util.*;
import javax.persistence.*;
import javax.ejb.*;
import uk.ac.port.SUMS.ProjectIdeas.model.*;
import oyei.SUMS.Registration.entities.*;
import javax.interceptor.*;
import uk.ac.port.SUMS.ProjectIdeas.model.exceptions.*;

/**
DAO/Facade class for reading and writing ProjectIdea entities,
along with related value objects within the ProjectIdea aggregate root,
from and to the underlying data store.
@author Reciprocal
*/
@Stateless
public class ProjectIdeaDAO extends AbstractFacade<ProjectIdea>{
 @PersistenceContext(unitName="uk.ac.port.SUMS.PU")
 private EntityManager em;
 public ProjectIdeaDAO(){
  super(ProjectIdea.class);
 }
 
 protected @Override EntityManager getEntityManager(){
  return em;
 }

 public ProjectIdea Read(String Title)throws NoEntityFoundException{
  TypedQuery<ProjectIdea> Query=getEntityManager().createNamedQuery("ProjectIdea.Read",ProjectIdea.class);
  Query.setParameter("Title",Title);
  try{
   return Query.getSingleResult();
  }catch(NoResultException Error){
   throw new NoEntityFoundException();
  }
 }
 public ProjectIdea Read(long DatabaseID)throws NoEntityFoundException{
  return super.Read(DatabaseID);
 }
 
 /**
 Retrieves summary details of all ProjectIdea entities that do not have a Status of Withdrawn or Rejected.
 The results are not in any particular order.
 For good performance, the full entity objects are not returned; just summary information.
 To get the full ProjectIdea entity for a particular ProjectIdeaSummary instance,
 call one of the Read methods. For example:
 <p><code>Collection&lt;ProjectIdeaSummary&gt; ideas=DAO.ReadAllNonWithdrawn();
 <br/>ProjectIdea FirstIdea=DAO.Read(ideas[0].getDatabaseID());</code></p>
 @return An unordered collection referencing all ProjectIdea entities that are not Withdrawn or Rejected
 */
 public Collection<ProjectIdeaSummary> ReadAllNonWithdrawnRejected(){
  TypedQuery<ProjectIdeaSummary> Query=getEntityManager().createNamedQuery("ProjectIdea.ReadAllNonWithdrawnRejected",ProjectIdeaSummary.class);
  return Query.getResultList();
 }
 
 /**
 Returned list will be in descending SubmissionDate order.
 */
 public List<ProjectIdeaSummary> ReadOwned(Person OwnedBy){
  TypedQuery<ProjectIdeaSummary> Query=getEntityManager().createNamedQuery("ProjectIdea.ReadOwned",ProjectIdeaSummary.class);
  Query.setParameter("OwnedByUsername",OwnedBy.getUsername());
  return Query.getResultList();
 }
 
 public List<ProjectIdeaSummary> Search(String SearchString){
  TypedQuery<ProjectIdeaSummary> Query=getEntityManager().createNamedQuery("ProjectIdea.Search",ProjectIdeaSummary.class);
  String SearchStringLike="%"+EscapeLikeString(SearchString)+"%";
  //Query.setParameter("SearchString",SearchString);
  Query.setParameter("SearchStringLike",SearchStringLike);
  //Query.setParameter("SearchStringAsCategory",new ProjectCategory(SearchString));
  return Query.getResultList();
 }
 public List<ProjectIdeaSummary> SearchApproved(String SearchString){
  TypedQuery<ProjectIdeaSummary> Query=getEntityManager().createNamedQuery("ProjectIdea.SearchApproved",ProjectIdeaSummary.class);
  String SearchStringLike="%"+EscapeLikeString(SearchString)+"%";
  //Query.setParameter("SearchString",SearchString);
  Query.setParameter("SearchStringLike",SearchStringLike);
  //Query.setParameter("SearchStringAsCategory",new ProjectCategory(SearchString));
  return Query.getResultList();
 }
 public List<ProjectIdeaSummary> SearchApprovedAndOwned(String SearchString,Person Owner){
  TypedQuery<ProjectIdeaSummary> Query=getEntityManager().createNamedQuery("ProjectIdea.SearchApprovedAndOwned",ProjectIdeaSummary.class);
  String SearchStringLike="%"+EscapeLikeString(SearchString)+"%";
  //Query.setParameter("SearchString",SearchString);
  Query.setParameter("SearchStringLike",SearchStringLike);
  //Query.setParameter("SearchStringAsCategory",new ProjectCategory(SearchString));
  Query.setParameter("OwnerUsername",Owner.getUsername());
  return Query.getResultList();
 }
 
 public boolean Exists(String Title){
  TypedQuery<Integer> ExistsQuery=getEntityManager().createNamedQuery("ProjectIdea.Exists",Integer.class);
  ExistsQuery.setParameter("Title",Title);
  return ExistsQuery.getSingleResult()!=0;
 }
 
 //According to current Requirements, the creator will always be the ProjectIdea Owner, so the By parameter is, at present, redundant
 public void Create(ProjectIdea ToCreate,Person By){
  super.Create(ToCreate);
 }
 
 @Interceptors(SavingProjectIdeaDecorator.class)
 public ProjectIdea Update(ProjectIdea ToUpdate,Person By)throws ConcurrencyException{
  ProjectIdea Result=super.UpdateInternal(ToUpdate);
  //Ensure important non-persistent state information is maintained
  Result.PostMerge(ToUpdate);
  return Result;
 }
}
