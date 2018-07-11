package Sessions;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import oyei.SUMS.Registration.entities.Email;
import Sessions.AbstractFacade;

@Stateless
@Named("email")
public class EmailFacade extends AbstractFacade<Email> {

    @PersistenceContext(unitName = "uk.ac.port.SUMS.PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmailFacade() {
        super(Email.class);
    }

}
