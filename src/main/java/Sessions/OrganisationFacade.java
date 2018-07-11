package Sessions;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import Entities.Organisation;
import Sessions.AbstractFacade;

@Stateless
@Named("organisation")
public class OrganisationFacade extends AbstractFacade<Organisation> {

    @PersistenceContext(unitName = "uk.ac.port.SUMS.PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrganisationFacade() {
        super(Organisation.class);
    }

}
