package uk.ac.port.SUMS.ProjectIdeas.presentation;
import java.io.*;
import java.nio.charset.*;
import java.text.*;
import java.util.logging.*;
import javax.inject.*;
import javax.faces.context.*;
import oyei.SUMS.Registration.entities.*;
import Sessions.*;
import javax.ejb.*;
import oyei.SUMS.Registration.sessions.*;
import uk.ac.port.SUMS.ProjectIdeas.model.*;

/**
Base class for Controllers (JSF Backing Beans) that provides information
about the currently logged in user, as well as common convenience methods.
Current logged in user elements are a placeholder for testing;
edit/replace with solution used by Project Registration module.
@author Reciprocal
*/
abstract public class ControllerBase implements java.io.Serializable{
 @Inject
 private transient Logger Log;
 
 private void readObject(java.io.ObjectInputStream in)throws java.io.IOException,java.lang.ClassNotFoundException{
  in.defaultReadObject();
  this.Log=Logger.getLogger(this.getClass().getPackage().getName());
 }
 
 public boolean isUserAuthenticated(){
  
  //TODO replace with Project Registration solution/implementation
  return true;
  
 }
 
 public Person getCurrentUser(){
  
  //TODO replace with Project Registration solution/implementation
  
  /*
  Person Result=new Person();
  Result.setId((long)9);
  Result.setUsername("winryR");
  Result.setForername("Albert");
  Result.setSurname("Wesker");
  Result.setOrganisation(new Organisation());
  Result.getOrganisation().setId((long)0);
  Result.getOrganisation().setName("University of Portsmouth");
  Result.setPersonStatus(new PersonStatus());
  Result.getPersonStatus().setName("Staff");
  */
  /*
  //Result.setId((long)-2);
  //Result.setUsername("esmelir-A");
  Result.getPersonStatus().setName("Student");
  Result.setForername("Chris");
  Result.setSurname("Redfield");
  */
  //return Result;
  if(CurrentUser==null){
   CurrentUser=UserDAO.find((long)-9); //Administrator
   //CurrentUser=UserDAO.find((long)-8); //Coordinator
   //CurrentUser=UserDAO.find((long)-1); //Staff
   //CurrentUser=UserDAO.find((long)-77); //Student
  }
  return CurrentUser;
  
 }
 @EJB private PersonFacade UserDAO;
 static private Person CurrentUser=null;
 
 /**
 Convenience base class method for determining if the current request is a "Post-Back" request,
 a term also used in the ASP.NET framework.
 @return true if the current request is a "Post-Back", otherwise false
 @author Reciprocal
 */
 public boolean isPostBack(){
  try{
   return FacesContext.getCurrentInstance().isPostback();
  }catch(UnsupportedOperationException Error){
   return false;
  }
 }
 
 /**
 Convenience base class method for redirecting a request to a different page/resource.
 The JSF request cycle will be aborted after calling this method;
 calling code should not continue to construct a response view, as it will not be returned to the client.
 @param To Relative URL path component of the resource to redirect to, relative to the web application's root
 @author Reciprocal
 */
 protected void Redirect(String To){
  FacesContext RequestContext=FacesContext.getCurrentInstance();
  ExternalContext _RequestContext=RequestContext.getExternalContext();
  try{
   _RequestContext.redirect(_RequestContext.getRequestContextPath()+"/"+_RequestContext.encodeResourceURL(To));
  }catch(IOException Error){
   RequestContext.responseComplete();
   Log.log(Level.WARNING,MessageFormat.format("IO error redirecting to \"{0}\"",To),Error);
  }
 }
 
 /**
 Convenience base class method for setting the HTTP status code of the response, if applicable.
 This method only sets the response code;
 it does not direct to or display any error page.
 @param StatusCode The HTTP response status code to send in the response
 @author Reciprocal
 */
 protected void setHTTPStatusCode(int StatusCode){
  try{
   FacesContext.getCurrentInstance().getExternalContext().setResponseStatus(StatusCode);
  }catch(UnsupportedOperationException __){
  }
 }
 
 /**
 Enables support for UTF-8 query string parameter values.
 The Java Servlet specification still assumes ISO-8859-1 as the HTTP request encoding,
 rather than UTF-8 which is what is used today.
 Because of this, query string parameters with values outside the ASCII range
 are likely to be interpreted incorrectly,
 decoded as ISO-8859-1 rather than UTF-8.
 To correct this, this method can be called, supplying the query string parameter value.
 Note this assumes that the web application server container
 has not been configured to override HTTP request encoding
 to something other than ISO-8859-1.
 @param value A query string parameter value as retrieved from the Java Servlet API, or from an API that depends upon it
 @return The query string parameter value correctly decoded as UTF-8
 */
 protected String DecodeQueryStringValue(String value){
  return new String(value.getBytes(ISO88591),UTF8);
 }
 static private final Charset ISO88591=Charset.forName("ISO-8859-1");
 static private final Charset UTF8=Charset.forName("UTF-8");
}
