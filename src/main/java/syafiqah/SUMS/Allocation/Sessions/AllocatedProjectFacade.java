package syafiqah.SUMS.Allocation.Sessions;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import syafiqah.SUMS.Allocation.Entities.AllocatedProject;
import Sessions.AbstractFacade;
import Sessions.AbstractFacade;
import javax.persistence.TypedQuery;

@Stateless
@Named("allocatedProject")
public class AllocatedProjectFacade extends AbstractFacade<AllocatedProject> {

    @PersistenceContext(unitName = "uk.ac.port.SUMS.PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

     public boolean Isthere(Long fp){
        TypedQuery<AllocatedProject> Query=getEntityManager().createNamedQuery("getAllocatedProjectByID", AllocatedProject.class);
        Query.setParameter("valueID", fp);
        if (Query.getResultList().isEmpty())
        {
            return Boolean.FALSE;
        }
        else
            return Boolean.TRUE;
    }
    
    public AllocatedProject getAllocatedProjectID(Long fp){
        TypedQuery<AllocatedProject> Query=getEntityManager().createNamedQuery("getAllocatedProjectByID", AllocatedProject.class);
        Query.setParameter("valueID", fp);
        
        return Query.getSingleResult();
    }
    
    public Boolean checkSvProject(Long s){
        TypedQuery<AllocatedProject> Query= getEntityManager().createNamedQuery("countSv", AllocatedProject.class);
        Query.setParameter("staffID", s);
        
        if(Query.getResultList().size() < 3){
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }
    public AllocatedProjectFacade() {
        super(AllocatedProject.class);
    }

}
