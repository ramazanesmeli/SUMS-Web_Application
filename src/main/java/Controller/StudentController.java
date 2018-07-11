/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entities.Student;
import Sessions.StudentFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author ASUS
 */
@Named(value = "studentController")
@SessionScoped
public class StudentController implements Serializable {

    Student student = new Student();
    List<Student> student_list = new ArrayList<Student>();

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Student> getStudent_list() {
        return student_list;
    }

    public void setStudent_list(List<Student> student_list) {
        this.student_list = student_list;
    }
    
    @EJB
    private StudentFacade sf;
    
    @PostConstruct
    public void onPageLoad(){
        setStudent_list(sf.getStudentID());
    }
    
    /**
     * Creates a new instance of StudentController
     */
    public StudentController() {
    }
    
}
