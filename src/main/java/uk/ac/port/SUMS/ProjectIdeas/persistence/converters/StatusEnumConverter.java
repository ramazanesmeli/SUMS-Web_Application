package uk.ac.port.SUMS.ProjectIdeas.persistence.converters;
import java.text.*;
import java.util.logging.*;
import javax.persistence.*;
import uk.ac.port.SUMS.ProjectIdeas.model.*;

@Converter(autoApply=true)
public class StatusEnumConverter implements AttributeConverter<ProjectIdea.Statuses,String>{
 protected final Logger Log=Logger.getLogger(this.getClass().getPackage().getName());
 public StatusEnumConverter(){}
 
 public @Override String convertToDatabaseColumn(ProjectIdea.Statuses value){
  return Character.toString(value.getDatabaseID());
 }

 public @Override ProjectIdea.Statuses convertToEntityAttribute(String _value){
  if(_value.length()!=1){
   String message=MessageFormat.format("Error while converting String to ProjectIdea.Status: The value \"{0}\" does not consist of a single char (UTF-16 Code Unit) value",_value);
   Log.severe(message);
   throw new IllegalArgumentException(message);
  }
  char value=_value.toCharArray()[0];
  try{
   return ProjectIdea.Statuses.FromDatabaseID(value);
  }catch(IllegalArgumentException Error){
   String message=MessageFormat.format("Error while converting char to ProjectIdea.Status: The value ''{0}'' (U+{1}) does not map to any Status value",value,Character.codePointAt(_value,0));
   Log.severe(message);
   throw new IllegalArgumentException(message);
  }
 }
 /*
 private ProjectIdea.Statuses AttemptConvertFromName(String value,String ErrorMessage){
  try{
   return ProjectIdea.Statuses.valueOf(value);
  }catch(IllegalArgumentException Error){
   Log.severe(ErrorMessage);
   throw new IllegalArgumentException(ErrorMessage);
  }
 }
 */
}