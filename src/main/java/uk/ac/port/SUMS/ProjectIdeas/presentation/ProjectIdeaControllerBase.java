package uk.ac.port.SUMS.ProjectIdeas.presentation;
import java.io.*;
import java.text.*;
import javax.faces.application.*;
import javax.faces.context.*;
import uk.ac.port.SUMS.infrastructure.*;
import uk.ac.port.SUMS.ProjectIdeas.model.*;
import uk.ac.port.SUMS.ProjectIdeas.model.exceptions.*;

/**
@author Reciprocal
*/
abstract public class ProjectIdeaControllerBase extends ControllerBase implements Serializable{
 private String Title=null;
 protected ProjectIdea Model=null;
 protected final StringSanitizer StringSanitizerService=new StringSanitizer();
 protected ProjectIdeaControllerBase(){}
 
 /**
 @return The title parameter supplied in the query string
 */
 public String getTitle(){
  return Title;
 }
 public void setTitle(String Title){
  this.Title=StringSanitizerService.ProcessLine(DecodeQueryStringValue(Title));
 }
 /**
 Preconditions: The request is not a post-back
 @return If the title parameter has been specified in the query string, and is not empty
 */
 public boolean isTitleSpecified(){
  if(super.isPostBack()){throw new IllegalStateException();}
  return !(this.Title==null||this.Title.isEmpty());
 }
 public ProjectIdea getModel(){
  return this.Model;
 }
 
 /**
 Attempts to load the ProjectIdea model specified by the title parameter in the query string,
 creating an error message if there was a problem.
 Preconditions: isTitleSpecified, the request is not a post-back
 Postconditions: Model!=null on success, Model==null on failure (not idempotent)
 */
 protected void LoadModel(){
  if(!isTitleSpecified()){throw new IllegalStateException();}
  ProjectIdea Model;
  try{
   Model=ReadModel();
  }catch(NoEntityFoundException Error){
   super.setHTTPStatusCode(404);
   FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(
    FacesMessage.SEVERITY_ERROR,
    "Project Idea not found",
    MessageFormat.format("No Project Idea with the Title \"{0}\" exists",Title)
   ));
   return;
  }catch(NotAuthorizedException Error){
   super.setHTTPStatusCode(403);
   FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(
    FacesMessage.SEVERITY_ERROR,
    "Access Denied",
    MessageFormat.format("The current user ({0}) is not authorized to view or edit this Project Idea",GetCurrentUsername())
   ));
   return;
  }
  this.Model=Model;
 }
 
 protected String GetCurrentUsername(){
  return super.isUserAuthenticated()?getCurrentUser().getUsername():"guest";
 }
 
 /**
 Called once by LoadModel; should retrieve and return the ProjectIdea
 specified by the Title property, which will not be empty,
 or throw an exception.
 */
 abstract protected ProjectIdea ReadModel()throws NoEntityFoundException,NotAuthorizedException;
}