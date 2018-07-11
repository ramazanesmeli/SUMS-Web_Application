package jinet.SUMS.Marking.Session;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jinet.SUMS.Marking.Entities.GradeUpdate;
import Sessions.AbstractFacade;
import Sessions.AbstractFacade;

@Stateless
@Named("update")
public class GradeUpdateFacade extends AbstractFacade<GradeUpdate> {

    @PersistenceContext(unitName = "uk.ac.port.SUMS.PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GradeUpdateFacade() {
        super(GradeUpdate.class);
    }

}
