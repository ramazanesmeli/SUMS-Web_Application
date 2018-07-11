package uk.ac.port.SUMS.ProjectIdeas.presentation;
import java.text.*;
import java.util.*;
import java.util.logging.*;
import javax.ejb.*;
import javax.inject.*;
import javax.faces.application.*;
import javax.faces.context.*;
import javax.faces.view.*;
import javax.persistence.*;
import uk.ac.port.SUMS.ProjectIdeas.model.*;
import uk.ac.port.SUMS.ProjectIdeas.model.exceptions.*;
import uk.ac.port.SUMS.ProjectIdeas.application.*;

//TODO Possibly split this class into a base class and two derived classes, one for Submit and one for Amend
/**
@author Reciprocal
*/
@ViewScoped @Named(value="SAI") //Submit-Amend-Idea
public class SubmitAmendProjectIdeaController extends ProjectIdeaControllerBase{
 @EJB
 private SubmitProjectIdea ApplicationSubmit;
 @EJB
 private AmendProjectIdea ApplicationAmend;
 private transient SubmitAmendProjectIdea Application=null;
 @EJB
 private WithdrawProjectIdea ApplicationWithdraw;
 private Map<String,ProjectCategory> AvailableCategories=null;
 private boolean amending;
 @Inject
 private transient Logger Log;
 
 public SubmitAmendProjectIdeaController(){}
 @PostActivate
 private void onActivated(){
  if(!amending){
   this.Application=ApplicationSubmit;
  }else{
   this.Application=ApplicationAmend;
  }
  this.Log=Logger.getLogger(this.getClass().getPackage().getName());
 }
 
 /**
 Preconditions: LoadModel has been called
 */
 public boolean isAmending(){
  return amending;
 }
 
 /**
 Preconditions: LoadModel has been called, and LoadFailure is false.
 getUserCanChangeStatus → ¬getUserCanWithdraw ∧ ¬getUserCanUnWithdraw
 */
 public boolean getUserCanChangeStatus(){
  if(!super.isUserAuthenticated()){return false;}
  return getModel().canChangeStatus(getCurrentUser());
 }
 /**
 Preconditions: LoadModel has been called, and LoadFailure is false.
 getUserCanWithdraw → ¬getUserCanChangeStatus ∧ ¬getUserCanUnWithdraw
 */
 public boolean getUserCanWithdraw(){
  if(!super.isUserAuthenticated()){return false;}
  return isAmending() && !getUserCanChangeStatus() && getModel().canWithdraw(getCurrentUser());
 }
 /**
 Preconditions: LoadModel has been called, and LoadFailure is false.
 getUserCanUnWithdraw → ¬getUserCanChangeStatus ∧ ¬getUserCanWithdraw
 */
 public boolean getUserCanUnWithdraw(){
  if(!super.isUserAuthenticated()){return false;}
  return isAmending() && !getUserCanChangeStatus() && getModel().canUnWithdraw(getCurrentUser());
 }
 
 /**
 Preconditions: LoadModel has been called, and LoadFailure is false
 */
 public ProjectIdea.Statuses getModelStatus(){
  return this.Model.getStatus();
 }
 /**
 Preconditions: LoadModel has been called, and LoadFailure is false
 */
 public void setModelStatus(ProjectIdea.Statuses value){
  if(!getUserCanChangeStatus()){
   Log.warning(MessageFormat.format("Attempt by user \"{2}\" to change Status of ProjectIdea \"{0}\" to {1}",getModel().getTitle(),value,GetCurrentUsername()));
   return;
  }
  this.Model.ChangeStatus(value,getCurrentUser());
 }
 
 public List<ProjectIdea.Statuses> getProjectIdeaStatuses(){
  return Arrays.asList(ProjectIdea.Statuses.values());
 }
 /**
 Preconditions: LoadModel has been called, and LoadFailure is false
 */
 public Collection<ProjectCategory> getAvailableCategories(){
  return AvailableCategories.values();
 }
 
 /**
 Preconditions: LoadModel has been called, and LoadFailure is false
 */
 public Collection<String> getSelectedCategories(){
  Collection<String> Result=new ArrayList<>(getModel().getCategories().size());
  for(ProjectCategory thisCategory : getModel().getCategories()){
   Result.add(thisCategory.getName());
  }
  return Result;
 }
 /**
 Preconditions: LoadModel has been called, and LoadFailure is false
 */
 public void setSelectedCategories(Collection<String> value){
  Set<ProjectCategory> Categories=new HashSet<>(value.size());
  for(String CategoryName : value){
   ProjectCategory SelectedCategory=AvailableCategories.get(CategoryName);
   if(SelectedCategory==null){
    Log.warning(MessageFormat.format("Unknown ProjectCategory \"{0}\" selected in view",CategoryName));
    continue;
   }
   Categories.add(SelectedCategory);
  }
  getModel().setCategories(Categories);
 }
 
 /**
 Preconditions: LoadModel has been called
 */
 public boolean getLoadFailure(){
  return /*!super.isPostBack()&&*/(super.Model==null||this.AvailableCategories==null);
 }
 /**
 Preconditions: The request is not a post-back
 */
 public @Override void LoadModel(){
  this.amending=isTitleSpecified();
  if(!amending){
   if(!super.isUserAuthenticated()){
    String duplicate;
    FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(
     FacesMessage.SEVERITY_ERROR,
     duplicate="You must log in to submit a new Project Idea",
     duplicate
    ));
    return;
   }
   super.Model=new ProjectIdea(getCurrentUser());
   this.Application=ApplicationSubmit;
  }else{
   super.LoadModel();
   this.Application=ApplicationAmend;
   if(this.Model==null){return;}
  }
  Set<ProjectCategory> AvailableCategories=Collections.unmodifiableSet(Application.RetrieveAvailableCategories());
  this.AvailableCategories=new HashMap<>(AvailableCategories.size());
  for(ProjectCategory AvailableCategory : AvailableCategories){
   this.AvailableCategories.put(AvailableCategory.getName(),AvailableCategory);
  }
 }
 protected @Override ProjectIdea ReadModel()throws NoEntityFoundException,NotAuthorizedException{
  if(!isAmending()){throw new IllegalStateException();}
  if(!super.isUserAuthenticated()){
   throw new NotAuthorizedException();
  }
  return ApplicationAmend.RetrieveForAmending(getTitle(),getCurrentUser());
 }
 
 /**
 Preconditions: LoadFailure is false
 */
 public String SubmitProjectIdea(){
  if(isAmending()||getLoadFailure()){throw new IllegalStateException();}
  if(!super.isUserAuthenticated()){
   String duplicate;
   FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(
    FacesMessage.SEVERITY_ERROR,
    duplicate="You must log in to submit a new Project Idea",
    duplicate
   ));
   return null;
  }
  try{
   ApplicationSubmit.Execute(getModel(),getCurrentUser());
  }catch(AlreadyExistsException Error){
   String duplicate;
   FacesContext.getCurrentInstance().addMessage("Main:Title",new FacesMessage(
    FacesMessage.SEVERITY_ERROR,
    duplicate="A Project Idea with this Title already exists; specify a different Title",
    duplicate
   ));
   return null;
  }
  return FacesContext.getCurrentInstance().getExternalContext().encodeRedirectURL(
   "ProjectIdea.xhtml?faces-redirect=true",
   Collections.singletonMap("title",Collections.singletonList(getModel().getTitle()))
  );
 }
 
 /**
 Preconditions: LoadFailure is false
 */
 public String AmendProjectIdea(){
  if(!isAmending()||getLoadFailure()){throw new IllegalStateException();}
  try{
   if(!super.isUserAuthenticated()){
    throw new NotAuthorizedException();
   }
   super.Model=ApplicationAmend.Execute(getModel(),getCurrentUser());
  }catch(ConcurrencyException|OptimisticLockException Error){
   super.setHTTPStatusCode(409);
   FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(
    FacesMessage.SEVERITY_ERROR,
    "Modified By Other User",
    "This Project Idea has been concurrently modified, or possibly deleted, by another user; copy the changes made to an external application, and refresh the page without re-submitting any data"
   ));
   return null;
  }catch(NotAuthorizedException Error){
   FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(
    FacesMessage.SEVERITY_ERROR,
    "Access Denied",
    MessageFormat.format("The current user ({0}) is not authorized to amend this Project Idea",GetCurrentUsername())
   ));
   super.Model=null;
   return null;
  }
  //super.Redirect("ProjectIdea.xhtml?includeViewParams=true");
  return "ProjectIdea.xhtml?includeViewParams=true&faces-redirect=true";
 }
 
 /**
 Preconditions: LoadFailure is false
 */
 public String WithdrawProjectIdea(){
  if(!isAmending()||getLoadFailure()){throw new IllegalStateException();}
  try{
   if(!super.isUserAuthenticated()){
    throw new NotAuthorizedException();
   }
   super.Model=ApplicationWithdraw.Execute(getModel(),getCurrentUser());
  }catch(ConcurrencyException|OptimisticLockException Error){
   super.setHTTPStatusCode(409);
   FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(
    FacesMessage.SEVERITY_ERROR,
    "Modified By Other User",
    "This Project Idea has been concurrently modified, or possibly deleted, by another user; try refreshing the page and determining if the operation still needs doing"
   ));
   return null;
  }catch(NotAuthorizedException Error){
   FacesContext.getCurrentInstance().addMessage("Main:Withdraw",new FacesMessage(
    FacesMessage.SEVERITY_ERROR,
    "Access Denied",
    MessageFormat.format("The current user ({0}) is not authorized to withdraw this Project Idea",GetCurrentUsername())
   ));
   return null;
  }
  return null;
 }
 
 /**
 Preconditions: LoadFailure is false
 */
 public String UnWithdrawProjectIdea(){
  if(!isAmending()||getLoadFailure()){throw new IllegalStateException();}
  try{
   if(!super.isUserAuthenticated()){
    throw new NotAuthorizedException();
   }
   super.Model=ApplicationWithdraw.ExecuteUndo(getModel(),getCurrentUser());
  }catch(ConcurrencyException|OptimisticLockException Error){
   super.setHTTPStatusCode(409);
   FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(
    FacesMessage.SEVERITY_ERROR,
    "Modified By Other User",
    "This Project Idea has been concurrently modified, or possibly deleted, by another user; try refreshing the page and determining if the operation still needs doing"
   ));
   return null;
  }catch(NotAuthorizedException Error){
   FacesContext.getCurrentInstance().addMessage("Main:UnWithdraw",new FacesMessage(
    FacesMessage.SEVERITY_ERROR,
    "Access Denied",
    MessageFormat.format("The current user ({0}) is not authorized to unwithdraw this Project Idea",GetCurrentUsername())
   ));
   return null;
  }
  return null;
 }
}