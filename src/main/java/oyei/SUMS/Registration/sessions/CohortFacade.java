package oyei.SUMS.Registration.sessions;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import Entities.Cohort;
import Sessions.AbstractFacade;
import Sessions.AbstractFacade;

@Stateless
@Named("cohort")
public class CohortFacade extends AbstractFacade<Cohort> {

    @PersistenceContext(unitName = "uk.ac.port.SUMS.PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CohortFacade() {
        super(Cohort.class);
    }

}
