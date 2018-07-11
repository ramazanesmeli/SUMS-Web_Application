package Sessions;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import Entities.Phonenumber;
import Sessions.AbstractFacade;

@Stateless
@Named("phonenumber")
public class PhonenumberFacade extends AbstractFacade<Phonenumber> {

    @PersistenceContext(unitName = "uk.ac.port.SUMS.PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PhonenumberFacade() {
        super(Phonenumber.class);
    }

}
