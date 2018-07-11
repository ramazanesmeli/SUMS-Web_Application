/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jinet.SUMS.Marking.Controller;

import syafiqah.SUMS.Allocation.Entities.AllocatedProject;
import jinet.SUMS.Marking.Entities.GradeUpdate;
import jinet.SUMS.Marking.Entities.Grade;
import jinet.SUMS.Marking.Bus.*;
import javax.inject.Named;
import uk.ac.port.SUMS.ProjectIdeas.model.*;
import Entities.*;
import Bus.*;

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

import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;

/**
 *
 * @author a
 */
@Named(value = "GradeUpdateManagement")
@ViewScoped
public class GradeUpdateManagement implements Serializable {

    private List<ProjectIdea> projectideas = new ArrayList<ProjectIdea>();
    private List<Student> students = new ArrayList<Student>();
    private Long selectedid;
    private Student student;
    private List<Grade> grades = new ArrayList<Grade>();
    private Grade grade;
    private List<GradeUpdate> updates = new ArrayList<GradeUpdate>();
    private HtmlDataTable datatableBooks;
    private Grade selectedgrade;
    private List<FinalProject> finalprojects = new ArrayList<FinalProject>();
    private AllocatedProject ap;
    private boolean gradeselected = false;
    private boolean studentselected = false;
    private boolean submitted = false;

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

    

    public boolean isGradeselected() {
        return gradeselected;
    }

    public void setGradeselected(boolean gradeselected) {
        this.gradeselected = gradeselected;
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

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public List<FinalProject> getFinalprojects() {
        return finalprojects;
    }

    public void setFinalprojects(List<FinalProject> finalprojects) {
        this.finalprojects = finalprojects;
    }

    public List<GradeUpdate> getUpdates() {
        return updates;
    }

    public void setUpdates(List<GradeUpdate> updates) {
        this.updates = updates;
    }

    public Grade getSelectedgrade() {
        return selectedgrade;
    }

    public void setSelectedgrade(Grade selectedgrade) {
        this.selectedgrade = selectedgrade;
    }

    public HtmlDataTable getDatatableBooks() {
        return datatableBooks;
    }

    public void setDatatableBooks(HtmlDataTable datatableBooks) {
        this.datatableBooks = datatableBooks;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
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
    ProjectMarkingBusiness pm;


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
            setGrades(null);
            setAp(null);
            setStudentselected(false);
        } else {
            setStudent(null);
            setGrades(null);
            setAp(null);
            setStudent(pm.getStudentswithId(getSelectedid()));
            if (getStudent().getFinalProject()==null){
               setGrade(null);
                
            } else 
                    {
                setGrades(getStudent().getFinalProject().getGrades());
               
            }
            setGradeselected(false);
            setSubmitted(false);
            setStudentselected(true);
            if (pm.getStudentAllocatedProject(getStudent()).size() > 0) {
                setAp(pm.getStudentAllocatedProject(getStudent()).get(0));
            }
        }
    }

    public void selectGrade(Grade m) {

        System.out.println(getCurrentDate().getTime());
        setSelectedgrade(m);
        setUpdates(getSelectedgrade().getUpdates());
        setGradeselected(true);
        setSubmitted(false);

    }

    

    
    @PostConstruct
    public void init() {

       
        selectedgrade = new Grade();
        try {
            getProjectIdea(); 
            setStudents(pm.getStudents());
        } catch (BusinessExceptions ex) {
            Logger.getLogger(GradeUpdateManagement.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public GradeUpdateManagement() {

    }
}
