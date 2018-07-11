package oyei.SUMS.Registration.sessions;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import oyei.SUMS.Registration.entities.Person;
import Sessions.AbstractFacade;
import Sessions.AbstractFacade;

@Stateless
@Named("person")
public class PersonFacade extends AbstractFacade<Person> {

    @PersistenceContext(unitName = "uk.ac.port.SUMS.PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonFacade() {
        super(Person.class);
    }

}
