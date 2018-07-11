package syafiqah.SUMS.Allocation.Sessions;

import Entities.Student;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import syafiqah.SUMS.Allocation.Entities.ShortList;
import Sessions.AbstractFacade;
import java.util.List;
import javax.persistence.TypedQuery;

@Stateless
@Named("shortList")
public class ShortListFacade extends AbstractFacade<ShortList> {

    @PersistenceContext(unitName = "uk.ac.port.SUMS.PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public ShortList getShortlistWithRank(int rank, Long stdID){
        TypedQuery<ShortList> Query = getEntityManager().createNamedQuery("ShortList.getRankedShortlist", ShortList.class);
        Query.setParameter("shortListRank", rank);
        Query.setParameter("stdID", stdID);
        return Query.getSingleResult();
    }
    
    public ShortList getShortlistSvWithRank(int rank, Long stdID){
        TypedQuery<ShortList> Query = getEntityManager().createNamedQuery("ShortList.getRankedShortlistSv", ShortList.class);
        Query.setParameter("shortListRank", rank);
        Query.setParameter("stdID", stdID);
        return Query.getSingleResult();
    }
    
    public List<ShortList> getShortListOrderedByRank(Long stdID){
        TypedQuery<ShortList> Query = getEntityManager().createNamedQuery("ShortList.sortByRank", ShortList.class);
        Query.setParameter("stdID", stdID);
        return Query.getResultList();
    }
    
    public List<ShortList> getShortListSvOrderedByRank(Long stdID){
        TypedQuery<ShortList> Query = getEntityManager().createNamedQuery("ShortList.sortSvByRank", ShortList.class);
        Query.setParameter("stdID", stdID);
        return Query.getResultList();
    }
    
    public boolean IsthereProject(Long fp, Long s){
        TypedQuery<ShortList> Query=getEntityManager().createNamedQuery("ShortlList.checkExistingProject", ShortList.class);
        Query.setParameter("fpID", fp);
        Query.setParameter("stdID", s);
        if (Query.getResultList().isEmpty())
        {
            return Boolean.FALSE;
        }
        else
            return Boolean.TRUE;
    }
    
    public boolean IsthereSv(Long sv, Long s){
        TypedQuery<ShortList> Query=getEntityManager().createNamedQuery("ShortList.checkExistingSv", ShortList.class);
        Query.setParameter("svID", sv);
        Query.setParameter("stdID", s);
        if (Query.getResultList().isEmpty())
        {
            return Boolean.FALSE;
        }
        else
            return Boolean.TRUE;
    }
    
    public List<ShortList> getShortListProjectSize(Long stdID){
        TypedQuery<ShortList> Query = getEntityManager().createNamedQuery("ShortList.getProjectSize", ShortList.class);
        Query.setParameter("stdID", stdID);
        return Query.getResultList();
    }
    
    public List<ShortList> getShortListSvSize(Long stdID){
        TypedQuery<ShortList> Query = getEntityManager().createNamedQuery("ShortList.getSupervisorSize", ShortList.class);
        Query.setParameter("stdID", stdID);
        return Query.getResultList();
    }
    
    public List<Student> getStudentByProject(Long fpID){
        TypedQuery<Student> Query = getEntityManager().createNamedQuery("Student.getByProjectID", Student.class);
        Query.setParameter("fpID", fpID);
        return Query.getResultList();
    }
    
    
    public ShortListFacade() {
        super(ShortList.class);
    }

}
