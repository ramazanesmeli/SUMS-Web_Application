package uk.ac.port.SUMS.ProjectIdeas.model;
import javax.interceptor.*;
import javax.inject.*;
import java.text.*;
import java.util.logging.*;
import oyei.SUMS.Registration.entities.*;
import uk.ac.port.SUMS.ProjectIdeas.persistence.*;
import uk.ac.port.SUMS.infrastructure.*;

//A traditional Decorator may have been simpler, but nonetheless this serves as a practical example of the use of EE Interceptors
//This is also slightly uglier as code in the Persistence layer has to reference code in the Model layer
/**
Model-layer Decorator class for the ProjectIdea DAO in the Persistence layer,
for implementing business concerns relevant at the point where a ProjectIdea is saved,
notably issuing notifications upon changes to a ProjectIdea.
Implemented as an EE Interceptor.
*/
public class SavingProjectIdeaDecorator{
 @Inject @StringInjectionQualifier("Dummy")
 protected NotificationService Notifier;
 @Inject
 protected Logger Log;
 public SavingProjectIdeaDecorator(){}
 
 @AroundInvoke
 public Object Update(InvocationContext Decorated)throws Exception{
  Object[] parameters=Decorated.getParameters();
  ProjectIdea ToUpdate=(ProjectIdea)parameters[0];
  Person By=(Person)parameters[1];
  Log.fine(MessageFormat.format("Update of ProjectIdea \"{0}\" Intercepted",ToUpdate.getTitle()));
  onProjectIdeaUpdating(ToUpdate,By);
  Object Result=Decorated.proceed();
  onProjectIdeaUpdated(ToUpdate,By);
  return Result;
 }
 
 protected void onProjectIdeaUpdating(ProjectIdea ToUpdate,Person By){
  if(!By.isStaff()){
   onNonStaffUpdate(ToUpdate,By);
  }
 }
 
 protected void onProjectIdeaUpdated(ProjectIdea ToUpdate,Person By){
  Log.info(MessageFormat.format("ProjectIdea \"{0}\" updated; sending change notification to Owner {1}",ToUpdate.getTitle(),By.getUsername()));
  Notifier.SendChangeNotification(
   //TODO Owner may have changed
   ToUpdate.getOwner(),
   ToUpdate,
   MessageFormat.format(
    "Changes have been made to the Owned Project Idea with the Title \"{0}\"."
    +" To see the updated Project Idea, please visit the Project Idea''s web page."
    +"\nThe Status of the Project Idea at the time of the change is: {1}",
    ToUpdate.getTitle(),ToUpdate.getStatus().getDisplayString()
   )
  );
 }
 
 protected void onNonStaffUpdate(ProjectIdea ToUpdate,Person By){
  //Note we must check to see that the ProjectIdea's Status is also not Withdrawn; a non-staff user can legitimately change the Status of a ProjectIdea between Withdrawn and Provisional, via the Withdraw and UnWithdraw operations
  if(ToUpdate.getStatus()!=ProjectIdea.Statuses.Provisional && ToUpdate.getStatus()!=ProjectIdea.Statuses.Withdrawn){
   Log.info(MessageFormat.format("ProjectIdea \"{0}\" being changed by non-staff user {1} and Status is not Provisional or Withdrawn; Resetting Status",ToUpdate.getTitle(),By.getUsername()));
   ToUpdate.ResetStatus(By);
  }
 }
}