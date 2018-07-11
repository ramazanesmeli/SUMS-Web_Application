package uk.ac.port.SUMS.ProjectIdeas.model;
import javax.persistence.*;
import oyei.SUMS.Registration.entities.*;
import uk.ac.port.SUMS.infrastructure.*;
import uk.ac.port.SUMS.infrastructure.constraints.*;

/**
Model layer Entity.
A ProjectIdea is associated with a set of ProjectCategory entities,
which are simply descriptive string names.
They are identified by their name.
Administrator users manage the collection of available ProjectCategory entities
that a ProjectIdea can be associated with.
@author Reciprocal
*/
@Entity @Access(AccessType.FIELD)
@NamedQuery(name="ProjectCategory.Exists",query=
 "SELECT CASE when COUNT(C.Name)>0 then true else false end from ProjectCategory C where C.Name=:CategoryName"
)
public class ProjectCategory implements java.io.Serializable{
 @Id
 private String Name;
 //transient is fine as long as this is only used in the constructor; otherwise should recreate upon deserialization
 private final transient StringSanitizer StringSanitizerService=new StringSanitizer();
 public ProjectCategory(String Name){
  this.Name=StringSanitizerService.ProcessLine(Name);
 }
 //For JPA
 private ProjectCategory(){}

 @NotEmpty
 public String getName(){
  return Name;
 }

 public boolean canCreate(Person User){
  return User.isAdministrator();
 }
 public boolean canDelete(Person User){
  return User.isAdministrator();
 }
 
 public @Override int hashCode(){
  return Name.hashCode();
 }
 public @Override boolean equals(Object object){
  if(!(object instanceof ProjectCategory)){
   return false;
  }
  ProjectCategory other=(ProjectCategory)object;
  if((this.Name==null&&other.Name!=null)||(this.Name!=null&&!this.Name.equals(other.Name))){
   return false;
  }
  return true;
 }
 public @Override String toString(){
  return "ProjectCategory["+Name+"]";
 }
}