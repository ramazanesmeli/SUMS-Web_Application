package uk.ac.port.SUMS.ProjectIdeas.test;
import java.text.*;
import java.util.*;
import javax.ejb.*;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.*;
import javax.persistence.*;
import uk.ac.port.SUMS.ProjectIdeas.model.*;
import uk.ac.port.SUMS.ProjectIdeas.persistence.*;

/**
@author Reciprocal
*/
@RequestScoped @Named(value="Debug")
public class Debug{
 @PersistenceContext(unitName="uk.ac.port.SUMS.PU")
 private EntityManager em;
 @EJB
 private ProjectIdeaDAO DAO;
 public void Execute(){
  try{
   //String Result=MessageFormat.format("{0,time,full}",Calendar.getInstance().getTime());
   //boolean Result=em.createQuery("SELECT CASE when COUNT(PI.Title)>0 then true else false end from ProjectIdea PI where PI.Title='Sample'",Integer.class).getSingleResult()!=0;
   //String Result=FacesContext.class.getPackage().getImplementationVersion();
   //Collection<ProjectIdeaSummary> Result=DAO.ReadAllNonWithdrawn();
   Object Result=em.createQuery(
    // <//stackoverflow.com./questions/39729529/>
    "SELECT new java.lang.String(CONCAT('a','b')) from Person P where P.id=4"
   ).getSingleResult();
   return;
  }catch(Throwable Error){
   return;
  }
 }
}
