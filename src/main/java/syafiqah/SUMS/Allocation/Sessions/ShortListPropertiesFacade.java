/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syafiqah.SUMS.Allocation.Sessions;

import Sessions.AbstractFacade;
import syafiqah.SUMS.Allocation.Entities.ShortListProperties;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author ASUS
 */
@Stateless
public class ShortListPropertiesFacade extends AbstractFacade<ShortListProperties> {

    @PersistenceContext(unitName = "uk.ac.port.SUMS.PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ShortListPropertiesFacade() {
        super(ShortListProperties.class);
    }
    
    public ShortListProperties getShortlistProjectMax(){
        TypedQuery<ShortListProperties> Query = getEntityManager().createNamedQuery("ShortlistSize.project", ShortListProperties.class);
        return (ShortListProperties)Query.getSingleResult();
    }
    
     public ShortListProperties getShortlistSvMax(){
        TypedQuery<ShortListProperties> Query = getEntityManager().createNamedQuery("ShortlistSize.supervisor", ShortListProperties.class);
        return (ShortListProperties)Query.getSingleResult();
    }
    
}
