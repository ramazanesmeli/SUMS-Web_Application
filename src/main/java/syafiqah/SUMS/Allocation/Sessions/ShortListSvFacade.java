/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syafiqah.SUMS.Allocation.Sessions;

import Sessions.AbstractFacade;
import syafiqah.SUMS.Allocation.Entities.ShortListSv;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ASUS
 */
@Stateless
public class ShortListSvFacade extends AbstractFacade<ShortListSv> {

    @PersistenceContext(unitName = "uk.ac.port.SUMS.PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ShortListSvFacade() {
        super(ShortListSv.class);
    }
    
    public void flushData(){
        em.flush();
    }
    
}
