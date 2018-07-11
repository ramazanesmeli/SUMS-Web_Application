/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oyei.SUMS.Registration.Bus;

import oyei.SUMS.Registration.entities.Email;
import oyei.SUMS.Registration.entities.Person;
import oyei.SUMS.Registration.sessions.CohortFacade;
import oyei.SUMS.Registration.sessions.PersonFacade;
import Bus.BusinessExceptions;
import Entities.*;
import Sessions.*;
import java.util.Date;
import java.util.List;


import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Ifeoluwa
 */
@Stateless
public class PersonBusiness {
    @EJB
    private PersonFacade pf;
    public List <Person> personss () throws BusinessExceptions {
         return pf.findAll();
}
    @EJB
    private StudentFacade sf;
    @EJB
    private EmailFacade ef;
    @EJB
    private PersonStatusFacade rf;
    @EJB
    private OrganisationFacade of;
    @EJB
    private PhonenumberFacade hf;
    @EJB
    private CohortFacade cf;
   
    public List<Cohort> displaycohorts() throws BusinessExceptions {

        return cf.findAll();
    }

  
    public void add(Person p,Student s,List<Email> e, PersonStatus r,Organisation o,Phonenumber h, Date currentDate) throws BusinessExceptions
    { 
    
    p.setPersonStatus(null);
    p.setEmails(null);
    p.setCohort(null);
    p.setStudent(null);
    p.setPhonenumbers(null);
    p.setOrganisation(null);
    p.setDateCreation(currentDate);
    p.setDateConfirmation(currentDate);
    pf.create(p);
    // rf.create(r);// status already 'n the database or it will be added new okey we w'll we add new
    
    //ef.create(e);

    //sf.create(s);
    
   
    //of.create(o);
    //hf.create(h);
    
}

   
}