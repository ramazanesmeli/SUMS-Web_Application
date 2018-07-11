package uk.ac.port.SUMS.ProjectIdeas.persistence;
import javax.ejb.*;
import javax.inject.*;
import java.text.*;
import java.util.logging.*;
import uk.ac.port.SUMS.ProjectIdeas.model.*;
import oyei.SUMS.Registration.entities.*;
import uk.ac.port.SUMS.infrastructure.*;

/**
Dummy implementation of the NotificationService service for sending e-mail notifications to users.
Simply writes the message provided to the log.
In a production environment, this would be replaced with an implementation that integrates
with the application server container's e-mail messaging functionality
to service the delivery e-mail messages.
*/
@Stateless @StringInjectionQualifier("Dummy")
public class NotificationServiceDummy implements NotificationService{
 @Inject
 protected Logger Log;
 public NotificationServiceDummy(){}
 
 public @Override void SendChangeNotification(Person To,ProjectIdea Changed,String MessageBody){
  //TODO Are there any particular situations where a Person might have no e-mail addresses?
  if(To.getEmails().size()<=0){
   Log.warning(MessageFormat.format("Unable to send change notification to user {0}, as they have no defined e-mail addresses",To.getUsername()));
   return;
  }
  String ToEmail=To.getEmails().get(0).getAddress();
  Log.info(
   MessageFormat.format(
    "To: {1}"
    +"\nTitle: Notification of Change to Project Idea \"{0}\""
    +"\nBody:\n",
    Changed.getTitle(),ToEmail
   )+MessageBody
  );
 }
}