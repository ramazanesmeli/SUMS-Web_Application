package uk.ac.port.SUMS.ProjectIdeas.model;

import java.time.*;
import java.util.*;

/**
Incomplete/Partial representation of a ProjectIdea,
containing enough information to identify a ProjectIdea to a user.
Useful for listings of many ProjectIdea entities, such as search results,
rather than returning collections of full entity objects.
@author Reciprocal
*/
public class ProjectIdeaSummary{
 private final String Title;
 private final long DatabaseID;
 private final String OwnerFullName;
 private final ZonedDateTime SubmissionDate;
 private final ProjectIdea.Statuses Status;
 public ProjectIdeaSummary(String Title,long DatabaseID,String OwnerNameFore,String OwnerNameLast,ZonedDateTime SubmissionDate,ProjectIdea.Statuses Status){
  this.Title=Title;
  this.DatabaseID=DatabaseID;
  this.OwnerFullName=OwnerNameFore+" "+OwnerNameLast;
  this.SubmissionDate=SubmissionDate;
  this.Status=Status;
 }

 public String getTitle(){
  return Title;
 }
 public long getDatabaseID(){
  return DatabaseID;
 }
 public String getOwnerFullName(){
  return OwnerFullName;
 }
 public ZonedDateTime getSubmissionDate(){
  return SubmissionDate;
 }
 public ProjectIdea.Statuses getStatus(){
  return Status;
 }
}