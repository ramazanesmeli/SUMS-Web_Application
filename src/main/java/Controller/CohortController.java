/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Bus.BusinessExceptions;
import Entities.Cohort;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Ifeoluwa
 */
@Named(value = "cohortController")
@RequestScoped
public class CohortController {
private Cohort cohorts = new Cohort();


    public Cohort getCohorts() {
        return cohorts;
    }

    public void setCohorts(Cohort cohorts) {
        this.cohorts = cohorts;
    }
    /**
     * Creates a new instance of CohortController
     */
   
    @EJB
    private  Bus.CohortBusiness cs;
    public String doChangeAddress() {
        try {
            cs.add(cohorts);
        } catch (BusinessExceptions ex) {
            Logger.getLogger(CohortController.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("something went wrong"));
        }
        return "";
    }
}

