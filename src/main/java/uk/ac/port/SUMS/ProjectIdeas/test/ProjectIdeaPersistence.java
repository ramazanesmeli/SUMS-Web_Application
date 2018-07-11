package uk.ac.port.SUMS.ProjectIdeas.test;
import oyei.SUMS.Registration.sessions.PersonFacade;
import javax.inject.*;
import javax.enterprise.context.*;
import uk.ac.port.SUMS.ProjectIdeas.model.*;
import oyei.SUMS.Registration.entities.*;
import uk.ac.port.SUMS.ProjectIdeas.persistence.*;
import Sessions.*;

/**
@author Reciprocal
*/
@RequestScoped @Named(value="ProjectIdeaPersistence")
public class ProjectIdeaPersistence{
 @Inject
 private ProjectIdeaDAO DAO;
 @Inject
 private PersonFacade DAOUser;
 public ProjectIdeaPersistence(){}
 
 public String ExecuteCreate(){
  try{
   Person Owner=DAOUser.find((long)5);
   ProjectIdea Model=new ProjectIdea(Owner);
   Model.setTitle("The Title");
   Model.setDescription("The Description");
   Model.setAcademicQuestion("The Question");
   Model.setAimsAndObjectives("The Objectives");
   DAO.Create(Model,Owner);
   return "";
  }catch(Exception ex){
   throw new RuntimeException(ex);
  }
 }
}
