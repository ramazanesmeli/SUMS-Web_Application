/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bus;

import Entities.Cohort;
import oyei.SUMS.Registration.sessions.CohortFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Ifeoluwa
 */
@Stateless
public class CohortBusiness {
    @EJB
    private CohortFacade cf;
    
    public Cohort add ( Cohort c) throws BusinessExceptions
    {
        cf.create(c);
        return c;
    }
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
