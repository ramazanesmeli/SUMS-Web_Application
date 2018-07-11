package uk.ac.port.SUMS.ProjectIdeas.model;
import java.util.*;
import java.text.*;
import java.time.*;
import java.util.logging.*;
import javax.persistence.*;
import oyei.SUMS.Registration.entities.*;
import javax.validation.constraints.*;
import uk.ac.port.SUMS.infrastructure.*;
import uk.ac.port.SUMS.infrastructure.constraints.*;

/**
Model layer Entity.
A ProjectIdea represents an idea a RegisteredUser has had for a Project.
A ProjectIdea is identified by its Title.
Alongside various properties describing the nature of the ProjectIdea,
it has a Status, defining whether it has been approved as a prospective Project or not.
@author Reciprocal
*/
@Entity @Access(AccessType.FIELD)
//Would ideally place this on the persistence layer ProjectIdea DAO
@NamedQueries({
 @NamedQuery(name="ProjectIdea.Read",query=
  "SELECT PI from ProjectIdea PI where PI.Title=:Title"
 ),@NamedQuery(name="ProjectIdea.Exists",query=
  "SELECT CASE when COUNT(PI.Title)>0 then true else false end from ProjectIdea PI where PI.Title=:Title"
 ),@NamedQuery(name="ProjectIdea.ReadAllNonWithdrawnRejected",query=
  "SELECT new uk.ac.port.SUMS.ProjectIdeas.model.ProjectIdeaSummary(PI.Title,PI.DatabaseID,PI.Owner.forername,PI.Owner.surname,PI.SubmissionDate,PI.Status)"
  +" from ProjectIdea PI where PI.Status!=uk.ac.port.SUMS.ProjectIdeas.model.ProjectIdea.Statuses.Withdrawn and PI.Status!=uk.ac.port.SUMS.ProjectIdeas.model.ProjectIdea.Statuses.Rejected"
 ),@NamedQuery(name="ProjectIdea.ReadOwned",query=
  "SELECT new uk.ac.port.SUMS.ProjectIdeas.model.ProjectIdeaSummary(PI.Title,PI.DatabaseID,PI.Owner.forername,PI.Owner.surname,PI.SubmissionDate,PI.Status)"
  +" from ProjectIdea PI where PI.Owner.username=:OwnedByUsername Order by PI.SubmissionDate DESC,PI.Title ASC"
 ),@NamedQuery(name="ProjectIdea.Search",query=
  "SELECT new uk.ac.port.SUMS.ProjectIdeas.model.ProjectIdeaSummary(PI.Title,PI.DatabaseID,PI.Owner.forername,PI.Owner.surname,PI.SubmissionDate,PI.Status)"
  +" from ProjectIdea PI left join PI.Categories C where ("+ProjectIdea.SearchQueryWhere+")"
  +" Order by PI.SubmissionDate DESC,PI.Title ASC"
 ),@NamedQuery(name="ProjectIdea.SearchApproved",query=
  "SELECT new uk.ac.port.SUMS.ProjectIdeas.model.ProjectIdeaSummary(PI.Title,PI.DatabaseID,PI.Owner.forername,PI.Owner.surname,PI.SubmissionDate,PI.Status)"
  +" from ProjectIdea PI left join PI.Categories C"
  +" where PI.Status=uk.ac.port.SUMS.ProjectIdeas.model.ProjectIdea.Statuses.Approved"
  +" AND ("+ProjectIdea.SearchQueryWhere+")"
  +" Order by PI.SubmissionDate DESC,PI.Title ASC"
 ),@NamedQuery(name="ProjectIdea.SearchApprovedAndOwned",query=
  "SELECT new uk.ac.port.SUMS.ProjectIdeas.model.ProjectIdeaSummary(PI.Title,PI.DatabaseID,PI.Owner.forername,PI.Owner.surname,PI.SubmissionDate,PI.Status)"
  +" from ProjectIdea PI left join PI.Categories C"
  +" where (PI.Status=uk.ac.port.SUMS.ProjectIdeas.model.ProjectIdea.Statuses.Approved OR PI.Owner.username=:OwnerUsername)"
  +" AND ("+ProjectIdea.SearchQueryWhere+")"
  +" Order by PI.SubmissionDate DESC,PI.Title ASC"
 )
})
public class ProjectIdea implements java.io.Serializable{
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="ID")
 private long DatabaseID;
 @Column(unique=true,nullable=false)
 private String Title;
 @ManyToOne(fetch=FetchType.EAGER,optional=false)
 private Person Owner;
 @Convert(converter=ZonedDateConverter.class) @Column(nullable=false,updatable=false)
 private ZonedDateTime SubmissionDate=null;
 //Mapped with StatusEnumConverter
 @Column(nullable=false)
 private Statuses Status=Statuses.Provisional;
 @Transient
 private Statuses OriginalStatus;
 @Transient
 private Person StatusChangedBy=null;
 //TODO Readonly-ness of collections
 @ManyToMany(fetch=FetchType.EAGER)
 //Mark the database relationship for cascading deletes, so that ProjectCategory entities may be deleted even if they are still associated with ProjectIdea entities
 //TODO Warn users deleting ProjectCategory entities still in use that the associations to them will be severed
 //@JoinTable(inverseJoinColumns={@JoinColumn(foreignKey=@ForeignKey(foreignKeyDefinition="FOREIGN KEY (CATEGORIES_NAME) references PROJECTCATEGORY(\"NAME\") on delete cascade"))})
 @JoinTable(inverseForeignKey=@ForeignKey(
  name="FK_PROJECTCATEGORY",
  foreignKeyDefinition="FOREIGN KEY (CATEGORIES_NAME) references PROJECTCATEGORY(\"NAME\") on delete cascade"
 ),foreignKey=@ForeignKey(
  //Possible bug/"feature"; setting inverse foreign key removes the other foreign key
  name="FK_PROJECTIDEA",
  foreignKeyDefinition="FOREIGN KEY (PROJECTIDEA_ID) references PROJECTIDEA(ID)"
 ))
 private Set<ProjectCategory> Categories=Collections.EMPTY_SET;
 @Column(nullable=false)
 private String Description="";
 @Column(nullable=false)
 private String AimsAndObjectives="";
 @Column(nullable=false)
 private String AcademicQuestion="";
 @Column(nullable=false)
 private String IntendedFor="";
 /*
 @ManyToMany(fetch=FetchType.EAGER)
 private Set<StudentUser> IntendedFor=Collections.EMPTY_SET;
 */
 //Use explicit order column to avoid dependency on system clock
 @ElementCollection(fetch=FetchType.LAZY) /*@OrderBy("when DESC")*/ @OrderColumn(name="ChangeOrder",nullable=false)
 private List<ProjectIdeaStatusChangeAudit> StatusChanges=new ArrayList(1);
 @Transient
 private List<ProjectIdeaStatusChangeAudit> StatusChangesDescending=null;
 @Version @Column(nullable=false)
 private long version;
 @Transient
 protected final StringSanitizer StringSanitizerService=new StringSanitizer();
 @Transient
 protected final Logger Log=Logger.getLogger(this.getClass().getPackage().getName());
 
 public ProjectIdea(Person CreatedBy){
  this.OriginalStatus=Status;
  this.Owner=CreatedBy;
  this.StatusChangedBy=CreatedBy;
 }
 //For JPA
 private ProjectIdea(){}
 
 @PostLoad
 private void onLoaded(){
  this.OriginalStatus=Status;
 }
 /**
 Intended to be called by the persistence layer DAO only,
 to ensure certain non-persistent state for managing Status property changes and audits
 is maintained across JPA merge operations.
 */
 public void PostMerge(ProjectIdea MergedFrom){
  this.OriginalStatus=MergedFrom.OriginalStatus;
  this.StatusChangedBy=MergedFrom.StatusChangedBy;
  Log.fine(MessageFormat.format(
   "ProjectIdea \"{0}\" merged for update; Status: {1}; OriginalStatus: {2}; StatusChangedBy: {3}",
   this.getTitle(),this.Status,this.OriginalStatus,
   this.StatusChangedBy!=null?this.StatusChangedBy.getUsername():"<null>"
  ));
 }

 @NotEmpty
 public String getTitle(){
  return Title;
 }
 public void setTitle(String Title){
  //Design issue — Must remember to use StringSanitizer in all relevant places; could use special data type but this adds complexity
  Title=StringSanitizerService.ProcessLine(Title);
  Log.info(MessageFormat.format("Changing ProjectIdea Title from {0} to {1}",this.Title,Title));
  this.Title=Title;
 }
 public long getDatabaseID(){
  return DatabaseID;
 }
 
 /**
 Usually the Owner of a ProjectIdea is the Person that originally created/submitted it.
 However, an Administrator can re-assign ProjectIdea entities to new Owners,
 which is normally done when a Person leaves.
 */
 @NotNull
 public Person getOwner(){
  return Owner;
 }
 public void setOwner(Person Owner){
  this.Owner=Owner;
 }
 
 /**
 The date (note, not date–time) that this ProjectIdea was originally created/submitted.
 */
 public ZonedDateTime getSubmissionDate(){
  return SubmissionDate;
 }
 
 @NotNull
 public Statuses getStatus(){
  return Status;
 }
 
 public Set<ProjectCategory> getCategories(){
  return Categories;
 }
 public void setCategories(Set<ProjectCategory> Categories){
  this.Categories=Categories;
 }
 
 @NotEmpty
 public String getDescription(){
  return Description;
 }
 public void setDescription(String Description){
  Description=StringSanitizerService.ProcessParagraph(Description);
  if(Description==null){Description="";}
  this.Description=Description;
 }
 
 @NotEmpty
 public String getAimsAndObjectives(){
  return AimsAndObjectives;
 }
 public void setAimsAndObjectives(String AimsAndObjectives){
  AimsAndObjectives=StringSanitizerService.ProcessParagraph(AimsAndObjectives);
  this.AimsAndObjectives=AimsAndObjectives;
 }
 
 @NotEmpty
 public String getAcademicQuestion(){
  return AcademicQuestion;
 }
 public void setAcademicQuestion(String AcademicQuestion){
  AcademicQuestion=StringSanitizerService.ProcessParagraph(AcademicQuestion);
  this.AcademicQuestion=AcademicQuestion;
 }
 
 /**
 The listing of Students that this ProjectIdea is aimed at;
 usually this is blank, indicating it is open to be allocated to any Student.
 */
 public String getIntendedFor(){
  return IntendedFor;
 }
 public void setIntendedFor(String IntendedFor){
  IntendedFor=StringSanitizerService.ProcessLine(IntendedFor);
  if(IntendedFor==null){IntendedFor="";}
  this.IntendedFor=IntendedFor;
 }
 //TODO Enhance IntendedFor property from a simple string back to a set of Students
 /*
 The set of Students that this ProjectIdea is aimed at;
 usually this is empty, indicating it is open to be allocated to any Student.
 public Set<StudentUser> getIntendedFor(){
  return IntendedFor;
 }
 public void setIntendedFor(Set<StudentUser> IntendedFor){
  this.IntendedFor=IntendedFor;
 }
 If this is false, IntendedFor will detail the particular Students that this ProjectIdea is aimed at.
 public boolean isIntendedForEveryone(){
  return IntendedFor.isEmpty();
 }
 */

 /**
 The returned readonly list will be in descending chronological order,
 with the most recent change first.
 */
 public List<ProjectIdeaStatusChangeAudit> getStatusChanges(){
  if(this.StatusChangesDescending==null){
   this.StatusChangesDescending=new ArrayList<>(this.StatusChanges.size());
   for(int index=this.StatusChanges.size()-1;index>=0;--index){
    StatusChangesDescending.add(StatusChanges.get(index));
   }
  }
  return Collections.unmodifiableList(this.StatusChangesDescending);
 }
 
 /**
 @return true if any user, including unauthenticated users, may view this ProjectIdea, otherwise false
 */
 public boolean canEveryoneView(){
  return Statuses.Approved.equals(this.getStatus());
 }
 /**
 @return true if the supplied user may view this ProjectIdea, otherwise false
 */
 public boolean canView(Person User){
  return this.canEveryoneView() || User.equals(this.getOwner()) || User.isStaff()
  || User.isAdministrator();
 }
 /**
 @return true if the supplied user may amend this ProjectIdea, otherwise false
 */
 public boolean canEdit(Person User){
  return User.equals(this.getOwner()) || User.isCoordinator()
  || User.isAdministrator();
 }
 /**
 @return true if the supplied user may view the Status change history of this ProjectIdea, otherwise false
 */
 public boolean canViewStatusChanges(Person User){
  return User.equals(this.getOwner()) || User.isStaff()
  || User.isAdministrator();
 }
 /**
 @return true if the supplied user may change a new or existing ProjectIdea's Status, otherwise false
 */
 public boolean canChangeStatus(Person User){
  return User.isStaff()
  || User.isAdministrator();
 }
 
 /**
 canWithdrawAndUndo → canEdit
 Note that Withdrawing an idea simply sets its Status, with all the usual side effects;
 thus a user that has the canChangeStatus permission can effectively Withdraw a ProjectIdea.
 @return true if the supplied user has the permissions required to Withdraw and UnWithdraw this ProjectIdea, otherwise false;
 note this method is not reflective of whether such operations are valid
 */
 public boolean canWithdrawAndUndo(Person User){
  return User.equals(this.getOwner())
  || User.isAdministrator();
 }
 /**
 @return true if this ProjectIdea may be Withdrawn by a user with the required permissions, otherwise false
 */
 public boolean isWithdrawable(){
  return getStatus()!=Statuses.Withdrawn;
 }
 /**
 @return true if this ProjectIdea may be UnWithdrawn by a user with the required permissions, otherwise false
 */
 public boolean isUnWithdrawable(){
  return getStatus()==Statuses.Withdrawn;
 }
 /**
 canWithdraw → ¬canUnWithdraw
 @return true if this ProjectIdea may be Withdrawn by the supplied user, otherwise false; canWithdrawAndUndo(By) ∧ isWithdrawable
 */
 public boolean canWithdraw(Person User){
  return canWithdrawAndUndo(User) && isWithdrawable();
 }
 /**
 canUnWithdraw → ¬canWithdraw
 @return true if this ProjectIdea may be UnWithdrawn by the supplied user, otherwise false; canWithdrawAndUndo(By) ∧ isUnWithdrawable
 */
 public boolean canUnWithdraw(Person User){
  return canWithdrawAndUndo(User) && isUnWithdrawable();
 }
 
 /**
 Preconditions: canChangeStatus
 Postconditions: Status == By
 */
 public void ChangeStatus(Statuses Status,Person By){
  if(!canChangeStatus(By)){throw new IllegalArgumentException();}
  _ChangeStatus(Status,By);
 }
 
 /**
 Postconditions: Status == Provisional
 */
 public void ResetStatus(Person By){
  _ChangeStatus(Statuses.Provisional,By);
 }
 
 /**
 Preconditions: canWithdraw / canWithdrawAndUndo ∧ isWithdrawable
 Postconditions: Status == Withdrawn
 */
 public void Withdraw(Person By){
  if(!canWithdrawAndUndo(By)){throw new IllegalArgumentException();}
  /*
  if(getStatus()==Statuses.Withdrawn){
   Log.warning(MessageFormat.format("Attempt made to Withdraw a ProjectIdea (\"{0}\") that is already Withdrawn by user {1}",getTitle(),By.getUsername()));
   return;
  }
  */
  _ChangeStatus(Statuses.Withdrawn,By);
 }
 /**
 Preconditions: canUnWithdraw / canWithdrawAndUndo ∧ isUnWithdrawable
 Postconditions: Status == Provisional
 */
 public void UnWithdraw(Person By){
  if(!canWithdrawAndUndo(By)){throw new IllegalArgumentException();}
  /*
  if(getStatus()!=Statuses.Withdrawn){
   Log.warning(MessageFormat.format("Attempt made to UnWithdraw a ProjectIdea (\"{0}\") that is not Withdrawn (Status is {2}) by user {1}",getTitle(),By.getUsername(),getStatus()));
   return;
  }
  */
  _ChangeStatus(Statuses.Provisional,By);
 }
 
 protected void _ChangeStatus(Statuses value,Person By){
  if(this.Status==value){return;}
  this.Status=value;
  this.StatusChangedBy=By;
 }
 
 @PrePersist
 private void onCreating(){
  ZonedDateTime Now=ZonedDateTime.now(ZoneId.of("Z"));
  //Calendar Now=Calendar.getInstance(TimeZone.getTimeZone(ZoneId.of("Z")),Locale.ROOT);
  Log.info(MessageFormat.format("New ProjectIdea \"{0}\" being created; setting SubmissionDate",getTitle()));
  if(this.SubmissionDate!=null){
   Log.warning(MessageFormat.format("Newly created ProjectIdea \"{1}\" already has a SubmissionDate of {0}",this.SubmissionDate,getTitle()));
  }else{
   this.SubmissionDate=Now;
  }
  if(this.StatusChangedBy==null){
   throw new RuntimeException(MessageFormat.format("Creating new ProjectIdea \"{0}\" in the underlying data store which has not been created with the public constructor, or has already been added to the underlying data store",this.getTitle()));
  }
  CommitStatusChangeAudit();
 }
 
 @PreUpdate
 private void onUpdating(){
  if(this.Status!=this.OriginalStatus){
   CommitStatusChangeAudit();
  }
 }
 
 private void CommitStatusChangeAudit(){
  ZonedDateTime Now=ZonedDateTime.now(ZoneId.of("Z"));
  //Calendar Now=Calendar.getInstance(TimeZone.getTimeZone(ZoneId.of("Z")),Locale.ROOT);
  ProjectIdeaStatusChangeAudit StatusChange=new ProjectIdeaStatusChangeAudit(Now,this.Status,this.StatusChangedBy);
  Log.info(MessageFormat.format("Updating/Creating ProjectIdea \"{0}\" with changed Status; adding audit record of {1} by user \"{2}\"",getTitle(),StatusChange.getTo().getDisplayString(),StatusChange.getBy().getUsername()));
  this.StatusChanges.add(StatusChange);
  this.StatusChangesDescending=null;
  this.OriginalStatus=this.Status;
  this.StatusChangedBy=null;
 }
 
 public @Override int hashCode(){
  int hash=0;
  hash+=(getTitle()!=null?getTitle().hashCode():0);
  return hash;
 }
 public @Override boolean equals(Object object){
  if(!(object instanceof ProjectIdea)){
   return false;
  }
  ProjectIdea other=(ProjectIdea)object;
  if((this.getTitle()==null&&other.getTitle()!=null)||(this.getTitle()!=null&&!this.Title.equals(other.Title))){
   return false;
  }
  return true;
 }
 public @Override String toString(){
  return "ProjectIdea["+getTitle()+"]";
 }

 static public final String SearchQueryWhere=
  //UPPER is used rather than LOWER to implement case-insensitive searching, as there are less multiple uppercase mappings than lowercase
  "UPPER(PI.Title) like UPPER(:SearchStringLike) escape '\\'"
  +" OR UPPER(CONCAT(PI.Owner.forername,' ',PI.Owner.surname)) like UPPER(:SearchStringLike) escape '\\'"
  +" OR UPPER(PI.Owner.username) like UPPER(:SearchStringLike) escape '\\'"
  +" OR UPPER(PI.Owner.organisation.Name) like UPPER(:SearchStringLike) escape '\\'"
  //+" OR (:SearchStringAsCategory member of PI.Categories)"
  +" OR UPPER(C.Name) like UPPER(:SearchStringLike) escape '\\'"
  +" OR UPPER(PI.Description) like UPPER(:SearchStringLike) escape '\\'"
  +" OR UPPER(PI.AcademicQuestion) like UPPER(:SearchStringLike) escape '\\'"
 ;
 
 public static enum Statuses{
  /**
  The ProjectIdea has been created by non-Staff,
  or the ProjectIdea has been modified by non-Staff,
  and is awaiting approval;
  this is typically the initial state for a ProjectIdea
  */
  Provisional('P',"Provisional"),
  /**
  The ProjectIdea has been reviewed and approved by a Staff member
  */
  Approved('A',"Approved"),
  /**
  An Approved ProjectIdea that has been allocated to the maximum number of Students,
  and so is no longer available for further Students to undertake
  */
  Allocated('L',"Allocated"),
  /**
  The ProjectIdea has been reviewed and not approved by a Staff member
  */
  Rejected('W',"Rejected"), //'W' used to be for Withdrawn, which now has slightly different semantics
  /**
  The Owner has decided to withdraw (delete) their idea;
  only administrators can permanently delete ideas
  */
  Withdrawn('D',"Withdrawn"); //Deleted
  
  private final char DatabaseID;
  private final String Display;
  private Statuses(char DatabaseID,String DisplayString){
   this.DatabaseID=DatabaseID;
   this.Display=DisplayString;
  }
  public char getDatabaseID(){
   return this.DatabaseID;
  }
  //This could be changed into a localization key
  public String getDisplayString(){
   return Display;
  }
  //This is defined here rather than in the dependant Converter, so that any additions to the enum are less likely to be missed here
  /**
  @throws IllegalArgumentException If the supplied character does not map to any enum value
  */
  static public Statuses FromDatabaseID(char DatabaseID){
   if(DatabaseID==Provisional.DatabaseID){return Provisional;}
   else if(DatabaseID==Approved.DatabaseID){return Approved;}
   else if(DatabaseID==Allocated.DatabaseID){return Allocated;}
   else if(DatabaseID==Rejected.DatabaseID){return Rejected;}
   else if(DatabaseID==Withdrawn.DatabaseID){return Withdrawn;}
   else{throw new IllegalArgumentException();}
  }
 }
}
