/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syafiqah.SUMS.Allocation.Controller;

import syafiqah.SUMS.Allocation.Bus.AllocationBusiness;
import Bus.BusinessExceptions;
import syafiqah.SUMS.Allocation.Entities.AllocatedProject;
import Entities.FinalProject;
import syafiqah.SUMS.Allocation.Entities.ShortList;
import Entities.Staff;
import Entities.Student;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import syafiqah.SUMS.Allocation.Bus.ShortlistBusiness;
import syafiqah.SUMS.Allocation.Entities.ShortListProperties;
import uk.ac.port.SUMS.ProjectIdeas.model.ProjectIdea;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 *
 * @author ASUS
 */
@Named(value = "allocationController")
@SessionScoped
public class AllocationController implements Serializable {

    FinalProject finalProject = new FinalProject();
    List<FinalProject> finalPorjectList = new ArrayList<FinalProject>();
    List<Student> students = new ArrayList<Student>();
    List<Staff> supervisors =  new ArrayList<Staff>();
    Student selectedStudent = new Student();
    Long selectedID;
    Staff selectedSv = new Staff();
    Long svID;
    List<ShortList> sv_list = new ArrayList<ShortList>();
    ShortList selectedSupervisor = new ShortList();
    Long staffID;
    AllocatedProject allocatedProject = new AllocatedProject();
    Boolean checkStudent;
    Boolean checkSv;
    Staff addStaff = new Staff();    
    List<ShortList> stdProject = new ArrayList<ShortList>();
    List<ShortList> stdSv = new ArrayList<ShortList>();
    List<Staff> addSupervisor = new ArrayList<Staff>();
    List<Student> allStudents = new ArrayList<Student>();
    List<ShortListProperties> properties = new ArrayList<ShortListProperties>();
    ShortListProperties sp = new ShortListProperties();
    List<AllocatedProject> allocatedProjectList = new ArrayList<AllocatedProject>();
    List<ProjectIdea> projectIdeas = new ArrayList<ProjectIdea>();
    Boolean projectEdit = Boolean.FALSE;
   
    Student currentStudent = new Student();
    Staff currentStaff = new Staff();
    

    public FinalProject getFinalProject() {
        return finalProject;
    }

    public void setFinalProject(FinalProject finalProject) {
        this.finalProject = finalProject;
    }

    public List<FinalProject> getFinalPorjectList() {
        return finalPorjectList;
    }

    public void setFinalPorjectList(List<FinalProject> finalPorjectList) {
        this.finalPorjectList = finalPorjectList;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Staff> getSupervisors() {
        return supervisors;
    }

    public void setSupervisors(List<Staff> supervisors) {
        this.supervisors = supervisors;
    }

    public Student getSelectedStudent() {
        return selectedStudent;
    }

    public void setSelectedStudent(Student selectedStudent) {
        this.selectedStudent = selectedStudent;
    }

    public Staff getSelectedSv() {
        return selectedSv;
    }

    public void setSelectedSv(Staff selectedSv) {
        this.selectedSv = selectedSv;
    }

    public ShortList getSelectedSupervisor() {
        return selectedSupervisor;
    }

    public void setSelectedSupervisor(ShortList selectedSupervisor) {
        this.selectedSupervisor = selectedSupervisor;
    }

    public Long getSelectedID() {
        return selectedID;
    }

    public void setSelectedID(Long selectedID) {
        this.selectedID = selectedID;
    }

    public Long getSvID() {
        return svID;
    }

    public void setSvID(Long svID) {
        this.svID = svID;
    }

    public Long getStaffID() {
        return staffID;
    }

    public void setStaffID(Long staffID) {
        this.staffID = staffID;
    }

    public Boolean getCheckStudent() {
        return checkStudent;
    }

    public void setCheckStudent(Boolean checkStudent) {
        this.checkStudent = checkStudent;
    }

    public Boolean getCheckSv() {
        return checkSv;
    }

    public void setCheckSv(Boolean checkSv) {
        this.checkSv = checkSv;
    }

    public Staff getAddStaff() {
        return addStaff;
    }

    public void setAddStaff(Staff addStaff) {
        this.addStaff = addStaff;
    }

    public List<ShortList> getStdProject() {
        return stdProject;
    }

    public void setStdProject(List<ShortList> stdProject) {
        this.stdProject = stdProject;
    }

    public AllocatedProject getAllocatedProject() {
        return allocatedProject;
    }

    public void setAllocatedProject(AllocatedProject allocatedProject) {
        this.allocatedProject = allocatedProject;
    }

    public List<Staff> getAddSupervisor() {
        return addSupervisor;
    }

    public void setAddSupervisor(List<Staff> addSupervisor) {
        this.addSupervisor = addSupervisor;
    }

    public List<Student> getAllStudents() {
        return allStudents;
    }

    public void setAllStudents(List<Student> allStudents) {
        this.allStudents = allStudents;
    }

    public List<ShortListProperties> getProperties() {
        return properties;
    }

    public void setProperties(List<ShortListProperties> properties) {
        this.properties = properties;
    }

    public ShortListProperties getSp() {
        return sp;
    }

    public void setSp(ShortListProperties sp) {
        this.sp = sp;
    }

    public List<ShortList> getSv_list() {
        return sv_list;
    }

    public void setSv_list(List<ShortList> sv_list) {
        this.sv_list = sv_list;
    }

    public List<ShortList> getStdSv() {
        return stdSv;
    }

    public void setStdSv(List<ShortList> stdSv) {
        this.stdSv = stdSv;
    }

    public List<AllocatedProject> getAllocatedProjectList() {
        return allocatedProjectList;
    }

    public void setAllocatedProjectList(List<AllocatedProject> allocatedProjectList) {
        this.allocatedProjectList = allocatedProjectList;
    }

    public List<ProjectIdea> getProjectIdeas() {
        return projectIdeas;
    }

    public void setProjectIdeas(List<ProjectIdea> projectIdeas) {
        this.projectIdeas = projectIdeas;
    }

    public Boolean getProjectEdit() {
        return projectEdit;
    }

    public void setProjectEdit(Boolean projectEdit) {
        this.projectEdit = projectEdit;
    }

    public Student getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(Student currentStudent) {
        this.currentStudent = currentStudent;
    }

    public Staff getCurrentStaff() {
        return currentStaff;
    }

    public void setCurrentStaff(Staff currentStaff) {
        this.currentStaff = currentStaff;
    }
  

    @PostConstruct
    public void onPageLoad(){
        try {
            getAllProjectIdeas();
        } catch (BusinessExceptions ex) {
            Logger.getLogger(AllocationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @EJB
    private AllocationBusiness ab;
    
    public void getAllApprovedProject(){
        setFinalPorjectList(ab.getAllApprovedProject());
    }
    
    public void getAllStudentByProject(FinalProject f){
        getStudents().clear();
        setStudents(ab.getAllStudentsByProject(f.getId()));        
        System.out.println("test from allocation : " + getStudents().size());
    }
    
    public void getAllStudentsFromDB(){
        setAllStudents(ab.getAllStudent());
        System.out.println("test from allocation2 : " + getStudents().size());
    }
    
    public void getAllStaff(Student s){
        setStudentSvShortList(getSelectedStudent());
        //setSv_list(ab.getRankedShortListSv(s.getId()));
        System.out.println("from project Allocation staff size: " + getSupervisors().size());
    }
    
    public void getAllocatedProjectFromDB(){
        setAllocatedProjectList(ab.getALlocatedProject());
    }
    
    public String allocateProject(FinalProject fp){
        setFinalProject(fp);
        getAllStudentByProject(getFinalProject());
        getAllStudentsFromDB();
        getAllSupervisor();
        setCheckStudent(false);
        setCheckSv(false);
        return "projectAllocation.xhtml";
    }  
        
    public void getAllSupervisor(){
        setSupervisors(ab.getAllSupervisorFromDB());
        System.out.println("sv : " + getSupervisors().size());
    }
    
    public void changeSv(ValueChangeEvent e) {        
        if(e.getNewValue() != null){
            svID = (Long) e.getNewValue();
            setSvID(svID);
            setSelectedSupervisor(ab.getShortlistwithid(getSvID()));
            System.out.println("from project Allocation staff size: " + e.getNewValue());
            setCheckSv(true);
        }else{
            setSelectedSupervisor(null);
            setCheckSv(false);
        }
    }
    
    public void changeSvID(ValueChangeEvent e){        
        if(e.getNewValue() != null){
            staffID = (Long) e.getNewValue();
            setStaffID(staffID);
            setSelectedSv(ab.getSelectedStaff(staffID));
            System.out.println("hello satff: " + getSelectedSv().getPerson().getFullName());
            setCheckSv(true);
        }else{
            setCheckSv(false);
            System.out.println("Here staff null");
//            setSelectedSv(null);
//            setStaffID(null);
        }
    }
    
    public String projectAllocation() throws BusinessExceptions{
        System.out.println("projectAllocation: " + getProjectEdit().toString());
        Boolean checkFp = ab.checkExistence(getFinalProject());
        System.out.println("final project id: " + getFinalProject().getId());
        if(!getProjectEdit()){
            if(!checkFp){
                if(getCheckStudent() == false && getCheckSv() == false){
                System.out.println("no student nor staff selected" + getSelectedID() + getStaffID());
                }else if(getCheckStudent() == true && getCheckSv() == false){             

                    allocatedProject.setFinalProject(getFinalProject());
                    allocatedProject.setStudent(getSelectedStudent());
                    ab.addAllocatedProject(allocatedProject);           
                    ab.addProjectToStudent(getSelectedStudent(), getFinalProject());
                    ab.changeProjectStatus(getFinalProject());
                    System.out.println("allocatedproject added" + getSelectedStudent().getStudentNumber());
                    sendEmail(getSelectedStudent().getPerson().getFullName());

                }else if(getCheckSv() == true && getCheckStudent() == false){
                    System.out.println("need to choose student");
                }else{
                    allocatedProject.setFinalProject(getFinalProject());
                    allocatedProject.setStaffs(getSelectedSv());
                    allocatedProject.setStudent(getSelectedStudent());
                    ab.addAllocatedProject(allocatedProject);            
                    ab.addProjectToStudent(getSelectedStudent(), getFinalProject());
                    ab.addProjectToStaff(getSelectedSv(), getFinalProject());
                    System.out.println("both added");
                    sendEmail(getSelectedStudent().getPerson().getFullName());
                    sendEmail(getSelectedSv().getPerson().getFullName());
                }
            }else{
                System.out.println("Project already been allocated");
            }
                setProjectEdit(Boolean.FALSE);
        }else{
            if(getCheckStudent() == false && getCheckSv() == false){
                System.out.println("no student nor staff selected" + getSelectedID() + getStaffID());
            }else if(getCheckStudent() == true && getCheckSv() == false){
                if(!getCurrentStudent().getId().equals(getSelectedStudent().getId())){
                    ab.editProjectStudent(getAllocatedProject(), getCurrentStudent(), getSelectedStudent());
                    ab.editProjectNewInfo(getAllocatedProject(), getSelectedStudent(), getCurrentStaff());
                }
                System.out.println("here student");
            }else if(getCheckSv() == true && getCheckStudent() == false){
                System.out.println("here staff");
                if(getCurrentStaff() ==  null && getSelectedSv() != null){                    
                    ab.editNewSupervisor(getAllocatedProject(), getSelectedSv());
                }else if(getCurrentStaff() != null){
                    ab.editProjectSupervisor(getAllocatedProject(), getCurrentStaff(), getSelectedSv());
                }
                ab.editProjectNewInfo(getAllocatedProject(), getCurrentStudent(), getSelectedSv());    
                
            }else{
                ab.editProjectStudent(getAllocatedProject(), getCurrentStudent(), getSelectedStudent());
                System.out.println("selected studet: " + getSelectedStudent().getPerson().getFullName());
                if(getCurrentStaff() ==  null && getSelectedSv() != null){                    
                    ab.editNewSupervisor(getAllocatedProject(), getSelectedSv());
                }else if(getCurrentStaff() != null){
                    ab.editProjectSupervisor(getAllocatedProject(), getCurrentStaff(), getSelectedSv());
                }
                //ab.editProjectSupervisor(getAllocatedProject(), getCurrentStaff(), getSelectedSv());
                System.out.println("selected staff: " + getSelectedSv().getPerson().getFullName());
                ab.editProjectNewInfo(getAllocatedProject(), getSelectedStudent(), getSelectedSv());
                System.out.println("here both");
                sendEmail(getSelectedStudent().getPerson().getFullName());
                sendEmail(getSelectedSv().getPerson().getFullName());
            }
//            sendEmail(getCurrentStudent().getPerson().getFullName(), "syafiqahazhar27@gmail.com", getAllocatedProject());
        }
        getAllocatedProjectFromDB();
        setProjectEdit(Boolean.FALSE);
//        setCurrentStudent(null);
//        setCurrentStaff(null);
//        setSelectedID(null);
//        setSelectedStudent(null);
//        setSvID(null);
//        setSelectedSv(null);
        return "allocatedProject.xhtml";
    }
    
    public void studentShortlist(Student s){
        setSelectedStudent(s);
        setStudentPorjectShortList(getSelectedStudent());
        setStudentSvShortList(getSelectedStudent());
        System.out.println(getStdProject().size());
    }
    
    public String getStudentWithShortList(){
        setStudents(ab.getAllStudentForAllocation());
        System.out.println("StudentSize: " + getStudents().size());
        return "allocateProjetcPage.xhtml";
    }
    
    public String allocateByStudentPreference(FinalProject fp){
        setSelectedID(getSelectedStudent().getId());
        setFinalProject(fp);
        getStudents().clear();
        getAllStudents().clear();
        students.add(getSelectedStudent());
        setCheckStudent(true);
        setStudentSvShortList(getSelectedStudent());
        getAllSupervisor();
        System.out.println("count staff: " + getSv_list().size());
        for(int i = 0; i < getSv_list().size(); i++){
            addSupervisor.add(getSv_list().get(i).getSupervisor());
            System.out.println("count staff added: " + i);
        }
        setProjectEdit(Boolean.FALSE);
        return "projectAllocation.xhtml";
    }
    
    public void getAllProperties(){
        setProperties(ab.getAllProperties());
    }
    
    public String goToSystemSetting(){
        getAllProperties();
        return "systemSetting.xhtml";
    }
    
    public String goToAllocationPage(){
        getAllStudentsFromDB();
        return "allocateProjectPage.xhtml";
    }
    
    public String goToAllocateProjectPage() throws BusinessExceptions{
        getAllocatedProjectFromDB();
        return "allocatedProject.xhtml";
    }
    
    public String editProperties(ShortListProperties sp){
        setSp(sp);
        return "systemSetting.xhtml";
    }
    
    public String saveProperties(){
        ab.editProperties(getSp(), getSp().getShortlistMax());
        getAllProperties();
        System.out.println("refresh");
        return "systemSetting.xhtml";
    }
    
    public void setStudentPorjectShortList(Student s){
        setStdProject(ab.getRankedShortList(s.getId()));
    }
    
    public void setStudentSvShortList(Student s){
         setSv_list(ab.getRankedShortListSv(s.getId()));
    }
    
    public String editAllocatedProject(AllocatedProject p){
        
        setAllocatedProject(p);
        setFinalProject(p.getFinalProject());
        System.out.println("final project " + getFinalProject().getId());
        setSelectedID(p.getStudent().getId());
        setSelectedStudent(p.getStudent());
        setCurrentStudent(p.getStudent());
        if(p.getStaffs()!= null){
            setCurrentStaff(p.getStaffs());
            setSelectedSv(p.getStaffs());
            setSvID(p.getStaffs().getId());
        }else{
            setSelectedSv(null);
            setSvID(null);
            setCurrentStaff(null);
        }        
        
        setProjectEdit(Boolean.TRUE);
        setCheckStudent(Boolean.TRUE);
        setCheckSv(Boolean.FALSE);
        return "projectAllocation.xhtml";
        
    }
    
    @EJB
    private ShortlistBusiness sb;
    public void changeStudentID(ValueChangeEvent e) throws BusinessExceptions {
        System.out.println("hey");
        if(e.getNewValue() != null){
            selectedID = (Long) e.getNewValue();
            setSelectedID(selectedID);
            setSelectedStudent(sb.getStudentswithId(getSelectedID()));
            setStudentSvShortList(getSelectedStudent());
            System.out.println("hello: " + getSelectedStudent().getStudentNumber());
            setCheckStudent(true);
        }else{
            setSelectedID(null);
            setSelectedStudent(null);
            setCheckStudent(false);            
        }
    }
    
    public void getAllProjectIdeas() throws BusinessExceptions{        
        projectIdeas = sb.getAllProject();
        System.out.println("project: " + getProjectIdeas().size());
         for (int i = 0; i < getProjectIdeas().size(); i++) {
            FinalProject finalpr = new FinalProject();
            finalpr.setIdea(getProjectIdeas().get(i));
            sb.addprojectidea(finalpr);
            System.out.println("no : " + i);
        }
    }
    
    
    
    public AllocationController() {
    }
    
    /** All of these code for email function are retrieved from mkyong.com    **/
    public void sendEmail(String name){
        System.out.println("here done");
        final String username = "nursyafiqahbintiazhar@gmail.com";
        final String password = "haru05Feb";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props, new GMailAuthenticator(username, password));
        try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("nursyafiqahazharbintiazhar@gmail.com"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse("syafiqahAzhar27@gmail.com"));
                message.setSubject("Project Allocation");
                message.setText("Dear " + name + ", "
                        + "\n\n This email is to infrom you that you have been allocated to a project. Please contact your cohort coordinator for more info.");

                Transport.send(message);

                System.out.println("Done");

        } catch (MessagingException e) {
                throw new RuntimeException(e);
        }
    }
    
    class GMailAuthenticator extends Authenticator {
     String user;
     String pw;
    public GMailAuthenticator (String username, String password)
     {
        super();
        this.user = username;
        this.pw = password;
     }
     
    @Override
    public PasswordAuthentication getPasswordAuthentication()
    {
       return new PasswordAuthentication(user, pw);
    }
}
    
}