package uk.ac.port.SUMS.ProjectIdeas.presentation;
import javax.ejb.*;
import javax.inject.*;
import javax.enterprise.context.*;
import uk.ac.port.SUMS.ProjectIdeas.model.*;
import uk.ac.port.SUMS.ProjectIdeas.model.exceptions.*;
import uk.ac.port.SUMS.ProjectIdeas.application.*;

/*
Does not currently support post-backs,
due to the preconditions on the can–navigate predicates
*/
/**
@author Reciprocal
*/
@RequestScoped @Named(value="I") //Idea
public class ViewProjectIdeaController extends ProjectIdeaControllerBase{
 @EJB
 private ViewProjectIdea Application;
 public ViewProjectIdeaController(){}
 
 /**
 Preconditions: LoadModel has been called, and LoadFailure is false
 */
 public boolean getCanNavigateToAmendIdea(){
  if(!super.isUserAuthenticated()){return false;}
  return getModel().canEdit(getCurrentUser());
 }
 /**
 Preconditions: LoadModel has been called, and LoadFailure is false
 */
 public boolean getCanNavigateToStatusChanges(){
  if(!super.isUserAuthenticated()){return false;}
  return getModel().canViewStatusChanges(getCurrentUser());
 }
 
 /**
 Preconditions: LoadModel has been called, or the request is a post-back
 @return true if there was an error whilst loading the model, otherwise false
 */
 public boolean getLoadFailure(){
  return !super.isPostBack()&&this.Model==null;
 }
 /**
 Will issue a redirect if the query string does not identify a ProjectIdea to retrieve,
 otherwise as for the base method.
 Preconditions: The request is not a post-back
 */
 public @Override void LoadModel(){
  if(!isTitleSpecified()){
   super.Redirect("project-ideas/ProjectIdeas.xhtml");
   return;
  }
  super.LoadModel();
 }
 protected @Override ProjectIdea ReadModel()throws NoEntityFoundException,NotAuthorizedException{
  if(super.isUserAuthenticated()){
   return Application.Execute(getTitle(),getCurrentUser());
  }else{
   return Application.Execute(getTitle());
  }
 }
}