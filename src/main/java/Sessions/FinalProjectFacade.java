package Sessions;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import Entities.FinalProject;
import Sessions.AbstractFacade;
import java.util.List;
import javax.persistence.TypedQuery;
import uk.ac.port.SUMS.ProjectIdeas.model.ProjectIdea;

@Stateless
@Named("finalProject")
public class FinalProjectFacade extends AbstractFacade<FinalProject> {

    @PersistenceContext(unitName = "uk.ac.port.SUMS.PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

public boolean Isthere(FinalProject f){
List<FinalProject> results = em.createQuery("SELECT f FROM FinalProject f where f.idea.DatabaseID = :value1")
                        .setParameter("value1",f.getIdea().getDatabaseID() ).getResultList();
if (results.isEmpty())
{
return Boolean.FALSE;
}
else
    return Boolean.TRUE;
}
    public List<ProjectIdea> projectsIdeas() {

        TypedQuery<ProjectIdea> Query = getEntityManager().createNamedQuery("ProjectIdea.Approved", ProjectIdea.class);
        return Query.getResultList();
    }
   
   
    public List<FinalProject> getProjectIdeaSearch(String searchKeyword){
        String searchKeywordFormat = "%" + searchKeyword + "%";
        TypedQuery<FinalProject> Query = getEntityManager().createNamedQuery("FindProject.Search", FinalProject.class);
        Query.setParameter("searchKeyword",searchKeywordFormat);
        System.out.println("Query : " + searchKeywordFormat);
        return Query.getResultList();
    }
    
    public List<FinalProject> approvedFinalProject(){
        TypedQuery<FinalProject> Query = getEntityManager().createNamedQuery("FinalProject.Approved", FinalProject.class);
        return Query.getResultList();
    }
    

    public FinalProjectFacade() {
        super(FinalProject.class);
    }

}
