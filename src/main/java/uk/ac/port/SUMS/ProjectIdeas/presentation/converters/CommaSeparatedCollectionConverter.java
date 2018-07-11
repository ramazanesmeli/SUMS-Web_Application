package uk.ac.port.SUMS.ProjectIdeas.presentation.converters;
import java.text.*;
import java.util.*;
import javax.faces.component.*;
import javax.faces.context.*;
import javax.faces.convert.*;

/**
JSF Converter for converting a collection of strings into a delimited list,
delimiting entries with a comma-space by default.
Conversion is one way only. Null values are skipped.
@author Reciprocal
*/
@FacesConverter(value="CommaSeparatedCollection")
public class CommaSeparatedCollectionConverter implements Converter{
 private String Delimiter=DefaultDelimiter;
 public CommaSeparatedCollectionConverter(){}
 
 /**
 @return The string to separate values in the collection with,
 by default a comma separated by a space
 */
 public String getDelimiter(){
  return Delimiter;
 }
 public void setDelimiter(String Delimiter){
  if(Delimiter==null){throw new IllegalArgumentException();}
  this.Delimiter=Delimiter;
 }
 
 public @Override String getAsString(FacesContext context,UIComponent component,Object _value){
  if(!(_value instanceof Collection)){
   throw new IllegalArgumentException(MessageFormat.format("Value \"{0}\" supplied to {1} is not a Collection",_value,this.getClass().getSimpleName()));
  }
  Collection<Object> value=(Collection<Object>)_value;
  StringBuilder Result=new StringBuilder();
  boolean first=true;
  for(Object thisValue : value){
   if(thisValue==null){continue;}
   if(first){first=false;}
   else{Result.append(this.Delimiter);}
   Result.append(String.valueOf(thisValue));
  }
  return Result.toString();
 }
 
 public @Override Object getAsObject(FacesContext context,UIComponent component,String value){
  throw new UnsupportedOperationException();
 }
 
 static private final String DefaultDelimiter=", ";
}