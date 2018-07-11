package Sessions;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import Entities.PersonStatus;
import Sessions.AbstractFacade;

@Stateless
@Named("personStatus")
public class PersonStatusFacade extends AbstractFacade<PersonStatus> {

    @PersistenceContext(unitName = "uk.ac.port.SUMS.PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonStatusFacade() {
        super(PersonStatus.class);
    }

}
