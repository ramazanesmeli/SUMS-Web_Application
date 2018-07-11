/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ramazan.SUMS.Monitoring.controller;

import ramazan.SUMS.Monitoring.entities.MilestoneFeedback;
import Bus.BusinessExceptions;
import ramazan.SUMS.Monitoring.entities.Milestone;
import ramazan.SUMS.Monitoring.bus.*;
import javax.inject.Named;
import Entities.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author a
 */
@Named(value = "projectMonitoringManagement")
@ViewScoped
public class ProjectMonitoringManagement implements Serializable {

    @EJB
    private ProjectMonitoringBussiness pb;

    private List<Staff> allstaf = new ArrayList<Staff>();
    private List<FinalProject> finalprojects = new ArrayList<FinalProject>();
    private List<MilestoneFeedback> feedbacks = new ArrayList<MilestoneFeedback>();
    private Milestone newmilestone = new Milestone();
    private List<Milestone> milestones = new ArrayList<Milestone>();
    private long selectedid;
    private Milestone selectedmilestone = new Milestone();
    private FinalProject selectedfinalproject = new FinalProject();
    private MilestoneFeedback feedback = new MilestoneFeedback();
    private Staff selectedstaff = new Staff();
    private Date currentDate;
    private boolean milestoneselected = false;
    private boolean projectselected = false;
    private boolean newmilestoneselected = false;

    public boolean isNewmilestoneselected() {
        return newmilestoneselected;
    }

    public void setNewmilestoneselected(boolean newmilestoneselected) {
        this.newmilestoneselected = newmilestoneselected;
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

    public Milestone getNewmilestone() {
        return newmilestone;
    }

    public void setNewmilestone(Milestone newmilestone) {
        this.newmilestone = newmilestone;
    }

    public Staff getSelectedstaff() {
        return selectedstaff;
    }

    public void setSelectedstaff(Staff selectedstaff) {
        this.selectedstaff = selectedstaff;
    }

    public MilestoneFeedback getFeedback() {
        return feedback;
    }

    public void setFeedback(MilestoneFeedback feedback) {
        this.feedback = feedback;
    }

    public FinalProject getSelectedfinalproject() {
        return selectedfinalproject;
    }

    public void setSelectedfinalproject(FinalProject selectedfinalproject) {
        this.selectedfinalproject = selectedfinalproject;
    }

    public boolean isMilestoneselected() {
        return milestoneselected;
    }

    public void setMilestoneselected(boolean milestoneselected) {
        this.milestoneselected = milestoneselected;
    }

    public List<Staff> getAllstaf() {
        return allstaf;
    }

    public void setAllstaf(List<Staff> allstaf) {
        this.allstaf = allstaf;
    }

    public List<FinalProject> getFinalprojects() {
        return finalprojects;
    }

    public void setFinalprojects(List<FinalProject> finalprojects) {
        this.finalprojects = finalprojects;
    }

    public Milestone getSelectedmilestone() {
        return selectedmilestone;
    }

    public void setSelectedmilestone(Milestone selectedmilestone) {
        this.selectedmilestone = selectedmilestone;
    }

    public List<MilestoneFeedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<MilestoneFeedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public List<Milestone> getMilestones() {
        return milestones;
    }

    public void setMilestones(List<Milestone> milestones) {
        this.milestones = milestones;
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
            getMilestones().clear();
            setMilestoneselected(false);
            setProjectselected(false);
            setNewmilestoneselected(false);
        } else {
            selectedStaff();
            getMilestones().clear();
            setMilestoneselected(false);
            setProjectselected(false);
            setNewmilestoneselected(false);
        }

    }

    public void selectedProject(FinalProject f) throws BusinessExceptions {
        setSelectedfinalproject(f);
        setMilestones(null);
        setMilestones(pb.getFinalProjectwithID(getSelectedfinalproject().getId()).getMilestones());
        setMilestoneselected(false);
        setProjectselected(true);
        setNewmilestoneselected(false);
    }

    public void selectMilestone(Milestone m) throws BusinessExceptions {

        //System.out.println(ap.getFinalProject().getMilestones().get(0).getFeedbacks().get(0).getCommend());
        setSelectedmilestone(pb.getMilestonewithId(m.getId()));
        setFeedbacks(getSelectedmilestone().getFeedbacks());
        if (getSelectedmilestone().getId() != null) {
            setMilestoneselected(true);
            setNewmilestoneselected(false);
        } else {
            setMilestoneselected(false);
            setNewmilestoneselected(false);
        }
    }

    public void getstafflist() throws BusinessExceptions {
        setAllstaf(pb.allStaf());
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

    public void newMilestoneselected() {
        //assign new value to localeCode
        setNewmilestoneselected(true);
        setMilestoneselected(false);

    }

    @PostConstruct
    public void init() {
        try {
            getstafflist();
        } catch (BusinessExceptions ex) {
            Logger.getLogger(ProjectMonitoringManagement.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void addMilestone() throws BusinessExceptions {
        getNewmilestone().setStatus("Not Submitted");
        pb.addMilestone(getNewmilestone(), getSelectedfinalproject());
        setMilestones(pb.getFinalProjectwithID(getSelectedfinalproject().getId()).getMilestones());
        clear();
        setMilestoneselected(false);
        setNewmilestoneselected(false);
    }

    public void updateMilestone() throws BusinessExceptions {

        if (getSelectedmilestone().getActiveDate().before(getSelectedmilestone().getDueDate())) {
            pb.updateMilestone(getSelectedmilestone(), getSelectedmilestone().getStatus(), getSelectedmilestone().getSubmittedDate());
            setMilestones(null);
            setMilestones(pb.getFinalProjectwithID(getSelectedfinalproject().getId()).getMilestones());
            setMilestoneselected(false);
            setNewmilestoneselected(false);
        } else {
            FacesContext.getCurrentInstance().addMessage("form4:activeDatem", new FacesMessage("Error: Check the dates;Milestone activated date must be before due date ! or due date must be after current date!"));

        }
    }

    public void addComment() throws BusinessExceptions {
        System.out.println(getSelectedmilestone().getId());
        if (getSelectedmilestone().getId() != null) {
            if (feedback.getCommentActivateDate().after(getSelectedmilestone().getActiveDate()) & feedback.getCommentActivateDate().after(getSelectedmilestone().getSubmittedDate())) {
                setMilestoneselected(false);
                setNewmilestoneselected(false);
                pb.insertFeedback(getFeedback(), getSelectedmilestone(), getSelectedstaff(), getCurrentDate());
                setFeedbacks(pb.getMilestonewithId(getSelectedmilestone().getId()).getFeedbacks());
            } else {
                FacesContext.getCurrentInstance().addMessage("form6:factivateddate", new FacesMessage("Error: Check the feedback activated date;feedback activated date must be equal  (or) after Milestone Submitted date and Milestone activated date !"));

            }
        } else {
            setMilestoneselected(false);
            setNewmilestoneselected(false);
        }
    }

    public void deleteMilestone(Milestone m) throws BusinessExceptions {
        System.out.println("Controller.ProjectMonitoringManagement.deleteMilestone()");
        pb.deleteMilestone(m, getSelectedfinalproject());
        setMilestones(pb.getFinalProjectwithID(getSelectedfinalproject().getId()).getMilestones());
        setMilestoneselected(false);
        setNewmilestoneselected(false);
    }

    public void DeleteComment(MilestoneFeedback f) throws BusinessExceptions {

        //System.out.println(ap.getFinalProject().getMilestones().get(0).getFeedbacks().get(0).getCommend());
        if (getSelectedmilestone().getId() != null) {
            setMilestoneselected(false);
            setNewmilestoneselected(false);
            pb.deleteFeedback(f, getSelectedmilestone());
            setFeedbacks(pb.getMilestonewithId(getSelectedmilestone().getId()).getFeedbacks());
        } else {
            setMilestoneselected(false);
            setNewmilestoneselected(false);
        }
    }

    public void dowloadfile() throws FileNotFoundException, IOException {
        File file = new File(System.getProperty("user.dir").replaceFirst("config", "cx").replaceAll("config", "").replaceFirst("cx", "config") + "generated\\jsp\\SUMS\\" + getSelectedmilestone().getFilePath());

        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpServletResponse response
                = (HttpServletResponse) facesContext.getExternalContext().getResponse();

        response.reset();
        response.setHeader("Content-Type", getSelectedmilestone().getFileContext());
        response.setHeader("Content-Disposition", "attachment;filename=" + getSelectedmilestone().getFilePath());

        try (OutputStream responseOutputStream = response.getOutputStream()) {
            InputStream fileInputStream = new FileInputStream(file);

            byte[] bytesBuffer = new byte[2048];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(bytesBuffer)) > 0) {
                responseOutputStream.write(bytesBuffer, 0, bytesRead);
            }

            responseOutputStream.flush();

            fileInputStream.close();
        }

        facesContext.responseComplete();
    }

    public void clear() {
        getNewmilestone().setActiveDate(null);
        getNewmilestone().setDueDate(null);
        getNewmilestone().setDescription("");
    }//end clear`

    public ProjectMonitoringManagement() {

    }

}
