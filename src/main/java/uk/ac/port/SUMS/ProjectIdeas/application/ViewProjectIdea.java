package uk.ac.port.SUMS.ProjectIdeas.application;
import javax.ejb.*;
import uk.ac.port.SUMS.ProjectIdeas.model.*;
import oyei.SUMS.Registration.entities.*;
import uk.ac.port.SUMS.ProjectIdeas.model.exceptions.*;
import uk.ac.port.SUMS.ProjectIdeas.persistence.*;

/**
Application layer EJB for retrieving an individual ProjectIdea model
for the user to view.
@author Reciprocal
*/
@Stateless
public class ViewProjectIdea{
 @EJB
 private ProjectIdeaDAO DAO;
 public ViewProjectIdea(){}
 
 public ProjectIdea Execute(String ProjectIdeaID)throws NoEntityFoundException,NotAuthorizedException{
  return Execute(ProjectIdeaID,null,false);
 }
 public ProjectIdea Execute(String ProjectIdeaID,Person ViewedBy)throws NoEntityFoundException,NotAuthorizedException{
  return Execute(ProjectIdeaID,ViewedBy,false);
 }
 public ProjectIdea ExecuteIncludeStatusChanges(String ProjectIdeaID,Person ViewedBy)throws NoEntityFoundException,NotAuthorizedException{
  return Execute(ProjectIdeaID,ViewedBy,true);
 }
 
 //Could not distinguish between no entity found and not authorized for extra security, but this may be overdoing things
 protected ProjectIdea Execute(String ProjectIdeaID,Person ViewedBy,boolean ViewingStatusChanges)throws NoEntityFoundException,NotAuthorizedException{
  //TODO Any other error handling
  ProjectIdea Model=DAO.Read(ProjectIdeaID);
  if(!Model.canEveryoneView()){
   if(ViewedBy==null||!Model.canView(ViewedBy)){
    throw new NotAuthorizedException();
   }
  }
  if(ViewingStatusChanges){
   if(ViewedBy==null||!Model.canViewStatusChanges(ViewedBy)){
    throw new NotAuthorizedException();
   }
  }
  return Model;
 }
}