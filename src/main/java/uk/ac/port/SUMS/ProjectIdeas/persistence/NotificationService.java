package uk.ac.port.SUMS.ProjectIdeas.persistence;
import uk.ac.port.SUMS.ProjectIdeas.model.*;
import oyei.SUMS.Registration.entities.*;

public interface NotificationService{
 /**
 Attempts to send a change notification e-mail message
 regarding changes to the specified ProjectIdea to the specified Person.
 A log warning message will be generated if there is a problem sending the notification.
 */
 void SendChangeNotification(Person To,ProjectIdea Changed,String MessageBody); 
}