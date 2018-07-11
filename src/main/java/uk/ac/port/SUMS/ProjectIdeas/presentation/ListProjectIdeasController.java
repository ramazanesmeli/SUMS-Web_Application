package uk.ac.port.SUMS.ProjectIdeas.presentation;
import java.util.*;
import javax.ejb.*;
import javax.inject.*;
import javax.enterprise.context.*;
import javax.validation.constraints.*;
import uk.ac.port.SUMS.ProjectIdeas.model.*;
import uk.ac.port.SUMS.ProjectIdeas.application.*;
import uk.ac.port.SUMS.infrastructure.constraints.*;

/**
@author Reciprocal
*/
@RequestScoped @Named(value="IL") //Idea List
public class ListProjectIdeasController extends ControllerBase{
 @EJB
 private ViewOwnProjectIdeas ApplicationOwned;
 @EJB
 private SearchProjectIdeas ApplicationSearch;
 private String SearchString=null;
 private List<ProjectIdeaSummary> Model;
 public ListProjectIdeasController(){}
 
 //TODO Validation is performed on the value passed to the setter, rather than re-reading the property and validating the result
 //@StringSize(Minimum=3,message="Search terms should be at least {Minimum} characters long")
 public String getSearchString(){
  return this.SearchString;
 }
 public void setSearchString(String value){
  this.SearchString=ApplicationSearch.NormalizeSearchString(DecodeQueryStringValue(value));
 }
 public boolean isSearchStringDefined(){
  return SearchString!=null&&!SearchString.isEmpty();
 }
 public List<ProjectIdeaSummary> getModel(){
  return this.Model;
 }
 /**
 Preconditions: LoadFailure is false
 */
 public boolean isModelEmpty(){
  return this.Model.isEmpty();
 }
 
 /**
 Preconditions: LoadModel has been called, or the request is a post-back
 @return true if there was an error whilst loading the model, otherwise false
 */
 public boolean getLoadFailure(){
  return !super.isPostBack()&&this.Model==null;
 }
 /**
 Postconditions: ¬SearchStringDefined ∧ ¬LoadFailure → UserAuthenticated
 (If isSearchStringDefined is false and LoadFailure is false after this call, then getCurrentUser will always return a value;
 there will always be a load failure if no search string is defined and so we are retrieving the current user's ProjectIdea entities, and there is no current user)
 */
 public void LoadModel(){
  if(!isSearchStringDefined()){
   LoadOwnedProjectIdeas();
  }else{
   LoadSearchResults();
  }
 }
 
 private void LoadOwnedProjectIdeas(){
  if(!super.isUserAuthenticated()){
   super.Redirect("");
   return;
  }
  this.Model=ApplicationOwned.Execute(getCurrentUser());
 }
 
 private void LoadSearchResults(){
  if(super.isUserAuthenticated()){
   this.Model=ApplicationSearch.Execute(getSearchString(),getCurrentUser());
  }else{
   this.Model=ApplicationSearch.Execute(getSearchString());
  }
 }
}