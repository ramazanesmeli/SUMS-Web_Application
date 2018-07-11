package Sessions;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import Entities.Staff;
import Sessions.AbstractFacade;
import java.util.List;
import javax.persistence.TypedQuery;

@Stateless
@Named("staff")
public class StaffFacade extends AbstractFacade<Staff> {

    @PersistenceContext(unitName = "uk.ac.port.SUMS.PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Staff> returnAllSupervisor(){    
        TypedQuery<Staff> Query=getEntityManager().createNamedQuery("FindAllSupervisor", Staff.class);
        return Query.getResultList();
    }
    
    public List<Staff> returnSelectedSv(long sID){
        TypedQuery<Staff> Query= getEntityManager().createNamedQuery("GetSelectedStaff", Staff.class);
        Query.setParameter("staffID", sID);
        return (List<Staff>) Query.getSingleResult();
        
    }
    public StaffFacade() {
        super(Staff.class);
    }

}
