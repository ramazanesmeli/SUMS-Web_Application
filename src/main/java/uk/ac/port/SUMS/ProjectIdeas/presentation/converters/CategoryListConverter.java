package uk.ac.port.SUMS.ProjectIdeas.presentation.converters;
import java.text.*;
import java.util.*;
import javax.faces.component.*;
import javax.faces.context.*;
import javax.faces.convert.*;
import uk.ac.port.SUMS.ProjectIdeas.model.*;

/**
JSF Converter for converting a collection of ProjectCategory entities into a delimited list,
delimiting entries with a comma-space by default.
Conversion is one way only. Null values are skipped.
@author Reciprocal
*/
@FacesConverter(value="CategoryList")
public class CategoryListConverter implements Converter{
 private final CommaSeparatedCollectionConverter Decorated=new CommaSeparatedCollectionConverter();
 public CategoryListConverter(){}
 
 /**
 @return The string to separate values in the collection with,
 by default a comma separated by a space
 */
 public String getDelimiter(){
  return Decorated.getDelimiter();
 }
 public void setDelimiter(String Delimiter){
  this.Decorated.setDelimiter(Delimiter);
 }
 
 public @Override String getAsString(FacesContext context,UIComponent component,Object _value){
  if(!(_value instanceof Collection)){
   throw new IllegalArgumentException(MessageFormat.format("Value \"{0}\" supplied to {1} is not a Collection",_value,this.getClass().getSimpleName()));
  }
  Collection<ProjectCategory> value=(Collection<ProjectCategory>)_value;
  List<String> Result=new ArrayList<>(value.size());
  for(ProjectCategory thisValue : value){
   if(thisValue==null){continue;}
   Result.add(thisValue.getName());
  }
  return Decorated.getAsString(context,component,Result);
 }
 
 public @Override Object getAsObject(FacesContext context,UIComponent component,String value){
  throw new UnsupportedOperationException();
 }
}