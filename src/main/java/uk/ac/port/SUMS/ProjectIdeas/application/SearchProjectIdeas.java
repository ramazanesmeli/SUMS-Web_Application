package uk.ac.port.SUMS.ProjectIdeas.application;
import java.util.*;
import java.util.regex.*;
import javax.ejb.*;
import uk.ac.port.SUMS.ProjectIdeas.model.*;
import oyei.SUMS.Registration.entities.*;
import uk.ac.port.SUMS.ProjectIdeas.persistence.*;
import uk.ac.port.SUMS.infrastructure.*;

/**
@author Reciprocal
*/
@Stateless
public class SearchProjectIdeas{
 @EJB
 private ProjectIdeaDAO DAO;
 protected final StringSanitizer StringSanitizationService=new StringSanitizer();
 public SearchProjectIdeas(){}
 
 public List<ProjectIdeaSummary> Execute(String SearchString){
  SearchString=NormalizeSearchString(SearchString);
  return DAO.SearchApproved(SearchString);
 }
 
 public List<ProjectIdeaSummary> Execute(String SearchString,Person Searcher){
  SearchString=NormalizeSearchString(SearchString);
  if(Searcher.isStaff()){
   return DAO.Search(SearchString);
  }else{
   return DAO.SearchApprovedAndOwned(SearchString,Searcher);
  }
 }
 
 public String NormalizeSearchString(String value){
  value=StringSanitizationService.ProcessLine(value);
  value=BadLeadingTrailingSearchCharacters.matcher(value).replaceAll("");
  //Would ideally normalize string into a compatibility form rather than just a canonical one, but implementing this at the database may harm performance
  return value;
 }
 
 static protected final Pattern BadLeadingTrailingSearchCharacters=Pattern.compile("\\A[\\p{Z}]+|[\\p{Z}]+\\z");
}