/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jinet.SUMS.Marking.Controller;

import jinet.SUMS.Marking.Entities.GradeUpdate;
import Bus.BusinessExceptions;
import jinet.SUMS.Marking.Entities.Grade;
import jinet.SUMS.Marking.Bus.*;
import javax.inject.Named;
import Entities.*;
import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;


/**
 *
 * @author a
 */
@Named(value = "projectMarkingManagement")
@ViewScoped
public class ProjectMarkingManagement implements Serializable {

    @EJB
    private ProjectMarkingBusiness pb;

    private List<Staff> allstaff = new ArrayList<Staff>();
    private List<FinalProject> finalprojects = new ArrayList<FinalProject>();
    private List<GradeUpdate> updates = new ArrayList<GradeUpdate>();
    private Grade newgrade = new Grade();
    private List<Grade> grades = new ArrayList<Grade>();
    private long selectedid;
    private Grade selectedgrade = new Grade();
    private FinalProject selectedfinalproject = new FinalProject();
    private GradeUpdate update = new GradeUpdate();
    private Staff selectedstaff = new Staff();
    private Date currentDate;
    private boolean gradeselected = false;
    private boolean projectselected = false;
    private boolean newgradeselected = false;

    public boolean isNewgradeselected() {
        return newgradeselected;
    }

    public void setNewgradeselected(boolean newgradeselected) {
        this.newgradeselected = newgradeselected;
    }

    public boolean isProjectselected() {
        return projectselected;
    }

    public void setProjectselected(boolean projectselected) {
        this.projectselected = projectselected;
    }

    public Date getCurrentDate() {
        Calendar currentDate2 = Calendar.getInstance();
        currentDate2.add(Calendar.HOUR_OF_DAY, 1);
        currentDate = currentDate2.getTime();
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Grade getNewgrade() {
        return newgrade;
    }

    public void setNewgrade(Grade newgrade) {
        this.newgrade = newgrade;
    }

    public Staff getSelectedstaff() {
        return selectedstaff;
    }

    public void setSelectedstaff(Staff selectedstaff) {
        this.selectedstaff = selectedstaff;
    }

    public GradeUpdate getUpdate() {
        return update;
    }

    public void setUpdate(GradeUpdate update) {
        this.update = update;
    }

    public FinalProject getSelectedfinalproject() {
        return selectedfinalproject;
    }

    public void setSelectedfinalproject(FinalProject selectedfinalproject) {
        this.selectedfinalproject = selectedfinalproject;
    }

    public boolean isGradeselected() {
        return gradeselected;
    }

    public void setGradeselected(boolean gradeselected) {
        this.gradeselected = gradeselected;
    }

    public List<Staff> getAllstaff() {
        return allstaff;
    }

    public void setAllstaff(List<Staff> allstaff) {
        this.allstaff = allstaff;
    }

    public List<FinalProject> getFinalprojects() {
        return finalprojects;
    }

    public void setFinalprojects(List<FinalProject> finalprojects) {
        this.finalprojects = finalprojects;
    }

    public Grade getSelectedgrade() {
        return selectedgrade;
    }

    public void setSelectedgrade(Grade selectedgrade) {
        this.selectedgrade = selectedgrade;
    }

    public List<GradeUpdate> getUpdates() {
        return updates;
    }

    public void setUpdates(List<GradeUpdate> updates) {
        this.updates = updates;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public long getSelectedid() {
        return selectedid;
    }

    public void setSelectedid(long selectedid) {
        this.selectedid = selectedid;
    }

    public void staffCodeChanged(ValueChangeEvent e) throws BusinessExceptions {
        selectedid = (Long) e.getNewValue();
        setSelectedid(selectedid);
        if ((getSelectedid())==-1) {
            getGrades().clear();
            setGradeselected(false);
            setProjectselected(false);
            setNewgradeselected(false);
        } else {
            selectedStaff();
            getGrades().clear();
            setGradeselected(false);
            setProjectselected(false);
            setNewgradeselected(false);
        }

    }

    public void selectedProject(FinalProject f) throws BusinessExceptions {
        setSelectedfinalproject(f);
        setGrades(null);
        setGrades(pb.getFinalProjectwithID(getSelectedfinalproject().getId()).getGrades());
        setGradeselected(false);
        setProjectselected(true);
        setNewgradeselected(false);
    }

    public void selectGrade(Grade m) throws BusinessExceptions {

        //System.out.println(ap.getFinalProject().getGrades().get(0).getUpdates().get(0).getCommend());
        setSelectedgrade(pb.getGradewithId(m.getId()));
        setUpdates(getSelectedgrade().getUpdates());
        if (getSelectedgrade().getId() != null) {
            setGradeselected(true);
            setNewgradeselected(false);
        } else {
            setGradeselected(false);
            setNewgradeselected(false);
        }
    }

    public void getstafflist() throws BusinessExceptions {
        setAllstaff(pb.allStaff());
    }

    public boolean findstaffbyprojectid() {
         
       // System.out.println(pb.findStaffWithProjectId(selectedfinalproject.getId()).contains((Staff) selectedstaff));
       boolean issupervisor=false;
       List<Staff> projectstaff=new ArrayList<>();
       projectstaff=pb.findStaffWithProjectId(selectedfinalproject.getId());
        for (Staff staff : projectstaff) {
             if (Objects.equals(selectedstaff.getId(), staff.getId()))
        {
            issupervisor= true;
            System.out.println("supervisor");
        }
             
        }
        return issupervisor;

    }

    public void selectedStaff() throws BusinessExceptions {
        setSelectedstaff(pb.findStaffwithId(selectedid));
        if (pb.findStaffwithId(selectedid).isIsCoordinator()) {
            setFinalprojects(pb.getstaffprojects(selectedstaff.getId(), selectedstaff.getPerson().getCohort().getId()));
            selectedstaff.setFinalProject(finalprojects);

        } else {
            setFinalprojects(null);
        }
    }

    public void newGradeselected() {
        //assign new value to localeCode
        setNewgradeselected(true);
        setGradeselected(false);

    }

    @PostConstruct
    public void init() {
        try {
            getstafflist();
        } catch (BusinessExceptions ex) {
            Logger.getLogger(ProjectMarkingManagement.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void addGrade() throws BusinessExceptions {
        getNewgrade().setStatus("Not Submitted");
        pb.addGrade(getNewgrade(), getSelectedfinalproject());
        setGrades(pb.getFinalProjectwithID(getSelectedfinalproject().getId()).getGrades());
        clear();
        setGradeselected(false);
        setNewgradeselected(false);
    }

  

    public void deleteGrade(Grade m) throws BusinessExceptions {
        System.out.println("Controller.ProjectMonitoringManagement.deleteGrade()");
        pb.deleteGrade(m, getSelectedfinalproject());
        setGrades(pb.getFinalProjectwithID(getSelectedfinalproject().getId()).getGrades());
        setGradeselected(false);
        setNewgradeselected(false);
    }

    public void DeleteComment(GradeUpdate f) throws BusinessExceptions {

        //System.out.println(ap.getFinalProject().getGrades().get(0).getUpdates().get(0).getCommend());
        if (getSelectedgrade().getId() != null) {
            setGradeselected(false);
            setNewgradeselected(false);
            pb.deleteUpdate(f, getSelectedgrade());
            setUpdates(pb.getGradewithId(getSelectedgrade().getId()).getUpdates());
        } else {
            setGradeselected(false);
            setNewgradeselected(false);
        }
    }

   

    public void clear() {
        getNewgrade().setActiveDate(null);      
        getNewgrade().setDescription("");
        getNewgrade().setGradedby("");
        getNewgrade().setOverallmarks("");
        getNewgrade().setProjectunit("");
        getNewgrade().setGeneralcomments("");
        getNewgrade().setProjectcontext("");
        getNewgrade().setLiterature("");
        getNewgrade().setMethodological("");
        getNewgrade().setSpecification("");
        getNewgrade().setAnalysis("");
        getNewgrade().setImplementation("");
        getNewgrade().setVerification("");
        getNewgrade().setEvaluation("");
        getNewgrade().setPlanning("");
        getNewgrade().setFinalartefact("");
        getNewgrade().setSummary("");
        getNewgrade().setStructure("");
        getNewgrade().setPrize("");
    }//end clear`

    public ProjectMarkingManagement() {

    }

}
