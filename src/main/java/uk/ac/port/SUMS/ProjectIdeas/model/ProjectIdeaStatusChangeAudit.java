package uk.ac.port.SUMS.ProjectIdeas.model;
import java.util.*;
import java.time.*;
import javax.persistence.*;
import oyei.SUMS.Registration.entities.*;
import uk.ac.port.SUMS.infrastructure.*;

/**
Model layer Value Object, composition of a ProjectIdea entity.
Represents an audit of a change made to a ProjectIdea entity's Status property,
at a particular date–time, by a particular RegisteredUser.
As is custom with Value Objects, the class is immutable.
@author Reciprocal
*/
@Embeddable @Access(AccessType.FIELD)
public class ProjectIdeaStatusChangeAudit implements java.io.Serializable{
 //Audit records may be relied upon for accountability, and so should never be tampered with once created; mark fields as non-updatable
 @Convert(converter=ZonedDateTimeConverter.class) @Column(nullable=false,name="WhenMade",updatable=false)
 private ZonedDateTime When;
 @Enumerated(EnumType.STRING) @Column(nullable=false,name="ToStatus",updatable=false)
 private ProjectIdea.Statuses To;
 @JoinColumn(nullable=false,name="WhoBy",updatable=false)
 private Person By;
 public ProjectIdeaStatusChangeAudit(ZonedDateTime When,ProjectIdea.Statuses To,Person By){
  this.When=When;
  this.To=To;
  this.By=By;
 }
 //For JPA
 private ProjectIdeaStatusChangeAudit(){}
 
 public ZonedDateTime getWhen(){
  return When;
 }
 public ProjectIdea.Statuses getTo(){
  return To;
 }
 public Person getBy(){
  return By;
 }
 
 public @Override String toString(){
  return "StatusChangeAudit["+getWhen()+","+getBy()+"]";
 }
}