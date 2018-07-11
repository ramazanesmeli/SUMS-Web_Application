/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ramazan.SUMS.Monitoring.controller;

import syafiqah.SUMS.Allocation.Entities.AllocatedProject;
import ramazan.SUMS.Monitoring.entities.MilestoneFeedback;
import ramazan.SUMS.Monitoring.entities.Milestone;
import ramazan.SUMS.Monitoring.bus.*;
import javax.inject.Named;
import uk.ac.port.SUMS.ProjectIdeas.model.*;
import Entities.*;
import Bus.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author a
 */
@Named(value = "listMilestoneManagement")
@ViewScoped
public class ListMilestoneManagement implements Serializable {

    private List<ProjectIdea> projectideas = new ArrayList<ProjectIdea>();
    private List<Student> students = new ArrayList<Student>();
    private Long selectedid;
    private Student student;
    private List<Milestone> milestones = new ArrayList<Milestone>();
    private Milestone milestone;
    private List<MilestoneFeedback> feedbacks = new ArrayList<MilestoneFeedback>();
    private HtmlDataTable datatableBooks;
    private Milestone selectedmilestone;
    private List<FinalProject> finalprojects = new ArrayList<FinalProject>();
    private AllocatedProject ap;
    private boolean milestoneselected = false;
    private boolean studentselected = false;
    private boolean submitted = false;
    private Part file1;
    private Date currentDate;

    public Date getCurrentDate() {
        Calendar currentDate2 = Calendar.getInstance();
        currentDate2.add(Calendar.MINUTE, 60);
        currentDate = currentDate2.getTime();
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Part getFile1() {
        return file1;
    }

    public void setFile1(Part file1) {
        this.file1 = file1;
    }

    public boolean isMilestoneselected() {
        return milestoneselected;
    }

    public void setMilestoneselected(boolean milestoneselected) {
        this.milestoneselected = milestoneselected;
    }

    public boolean isStudentselected() {
        return studentselected;
    }

    public void setStudentselected(boolean studentselected) {
        this.studentselected = studentselected;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    public AllocatedProject getAp() {
        return ap;
    }

    public void setAp(AllocatedProject ap) {
        this.ap = ap;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public List<FinalProject> getFinalprojects() {
        return finalprojects;
    }

    public void setFinalprojects(List<FinalProject> finalprojects) {
        this.finalprojects = finalprojects;
    }

    public List<MilestoneFeedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<MilestoneFeedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public Milestone getSelectedmilestone() {
        return selectedmilestone;
    }

    public void setSelectedmilestone(Milestone selectedmilestone) {
        this.selectedmilestone = selectedmilestone;
    }

    public HtmlDataTable getDatatableBooks() {
        return datatableBooks;
    }

    public void setDatatableBooks(HtmlDataTable datatableBooks) {
        this.datatableBooks = datatableBooks;
    }

    public List<Milestone> getMilestones() {
        return milestones;
    }

    public void setMilestones(List<Milestone> milestones) {
        this.milestones = milestones;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Long getSelectedid() {
        return selectedid;
    }

    public void setSelectedid(Long selectedid) {
        this.selectedid = selectedid;
    }

    public List<ProjectIdea> getProjectideas() {
        return projectideas;
    }

    public void setProjectideas(List<ProjectIdea> projectideas) {
        this.projectideas = projectideas;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
    @EJB
    ProjectMonitoringBussiness pm;


    public void getProjectIdea() throws BusinessExceptions {

        projectideas = pm.getAllApprovedIdeas();

        for (int i = 0; i < getProjectideas().size(); i++) {
          
            pm.addProjectIdea(getProjectideas().get(i));
            System.out.println(getProjectideas().size());
            System.out.println(getProjectideas().get(i).getTitle());
        }

    }

    public void studentCodeChanged(ValueChangeEvent e) throws BusinessExceptions {
        //assign new value to localeCode
        selectedid = (Long) e.getNewValue();
        setSelectedid(selectedid);
        if (Objects.isNull(getSelectedid())) {
            setStudent(null);
            setMilestones(null);
            setAp(null);
            setStudentselected(false);
        } else {
            setStudent(null);
            setMilestones(null);
            setAp(null);
            setStudent(pm.getStudentswithId(getSelectedid()));
            if (getStudent().getFinalProject()==null){
               setMilestone(null);
                
            } else 
                    {
                setMilestones(getStudent().getFinalProject().getMilestones());
               
            }
            setMilestoneselected(false);
            setSubmitted(false);
            setStudentselected(true);
            if (pm.getStudentAllocatedProject(getStudent()).size() > 0) {
                setAp(pm.getStudentAllocatedProject(getStudent()).get(0));
            }
        }
    }

    public void selectMilestone(Milestone m) {

        System.out.println(getCurrentDate().getTime());
        setSelectedmilestone(m);
        setFeedbacks(getSelectedmilestone().getFeedbacks());
        setMilestoneselected(true);
        setSubmitted(false);

    }

    public String upload() throws IOException {
        if (getFile1() == null) {

        } else {
            file1.write(getFileName(file1));
            getSelectedmilestone().setFilePath(getFileName(file1));
            getSelectedmilestone().setFileContext(getFile1().getContentType());
        }
        return "success";
    }

    public void dowloadFile() throws FileNotFoundException, IOException {
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

    private static String getFileName(Part part) {

        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

    public void submitMilestone() throws BusinessExceptions {
        try {
            upload();
            pm.updateMilestone(getSelectedmilestone(),"Submitted",currentDate);
            setSubmitted(true);
            setMilestoneselected(false);

        } catch (IOException ex) {
            Logger.getLogger(ListMilestoneManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @PostConstruct
    public void init() {

       
        selectedmilestone = new Milestone();
        try {
            getProjectIdea(); 
            setStudents(pm.getStudents());
        } catch (BusinessExceptions ex) {
            Logger.getLogger(ListMilestoneManagement.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ListMilestoneManagement() {

    }
}
