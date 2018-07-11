package ramazan.SUMS.Monitoring.session;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ramazan.SUMS.Monitoring.entities.MilestoneFeedback;
import Sessions.AbstractFacade;
import Sessions.AbstractFacade;

@Stateless
@Named("feedback")
public class MilestoneFeedbackFacade extends AbstractFacade<MilestoneFeedback> {

    @PersistenceContext(unitName = "uk.ac.port.SUMS.PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MilestoneFeedbackFacade() {
        super(MilestoneFeedback.class);
    }

}
