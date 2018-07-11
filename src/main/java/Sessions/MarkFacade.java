package Sessions;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import Entities.Mark;
import Sessions.AbstractFacade;

@Stateless
@Named("mark")
public class MarkFacade extends AbstractFacade<Mark> {

    @PersistenceContext(unitName = "uk.ac.port.SUMS.PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MarkFacade() {
        super(Mark.class);
    }

}
