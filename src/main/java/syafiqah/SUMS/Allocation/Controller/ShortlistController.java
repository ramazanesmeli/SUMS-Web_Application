/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syafiqah.SUMS.Allocation.Controller;

import Bus.BusinessExceptions;
import ramazan.SUMS.Monitoring.bus.*;
import syafiqah.SUMS.Allocation.Bus.ShortlistBusiness;
import Entities.FinalProject;
import syafiqah.SUMS.Allocation.Entities.ShortList;
import syafiqah.SUMS.Allocation.Entities.ShortListProperties;
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
import uk.ac.port.SUMS.ProjectIdeas.model.ProjectIdea;

/**
 *
 * @author ASUS
 */
@Named(value = "shortlistController")
@SessionScoped
public class ShortlistController implements Serializable {

    /**
     * Creates a new instance of ShortlistController
     */
    
    ShortList shortlist = new ShortList();
    ShortList forStdUpdate = new ShortList();
    List<ShortList> shortlist_list = new ArrayList<ShortList>();
    List<ShortList> std_shortlistProject = new ArrayList<ShortList>();
    List<ShortList> std_shortlistSv = new ArrayList<ShortList>();
    FinalProject finalProject = new FinalProject();
    List<FinalProject> projectList = new ArrayList<FinalProject>();    
    FinalProject currentProject = new FinalProject();
    ProjectIdea projectidea;
    List<ProjectIdea> projectideas = new ArrayList<ProjectIdea>();
    Student currentStudent = new Student();
    Student student = new Student();
    List<Student> students = new ArrayList<Student>();
    List<Staff> supervisor_list = new ArrayList<Staff>();
    Staff staff = new Staff();
    ShortListProperties svMax = new ShortListProperties();
    ShortListProperties projectMax = new ShortListProperties();
    List<FinalProject> notAllocatedProject = new ArrayList<FinalProject>();
    
    String searchKeywordForPI;
    int shortListSize;
    int shortListSvSize;
    int shortListCurrentSize;
    int shortListSvCurrentSize;
    
    Boolean insertShortlist = Boolean.FALSE;
    Boolean shortlistExist = Boolean.FALSE;
    Boolean shortlistMax = Boolean.FALSE;
    
    private Long selectedid;

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
    
    public Long getSelectedid() {
        return selectedid;
    }

    public void setSelectedid(Long selectedid) {
        this.selectedid = selectedid;
    }
    
    public ProjectIdea getProjectidea() {
        return projectidea;
    }

    public void setProjectidea(ProjectIdea projectidea) {
        this.projectidea = projectidea;
    }

    public List<ProjectIdea> getProjectideas() {
        return projectideas;
    }

    public void setProjectideas(List<ProjectIdea> projectideas) {
        this.projectideas = projectideas;
    }

    public ShortList getShortlist() {
        return shortlist;
    }

    public void setShortlist(ShortList shortlist) {
        this.shortlist = shortlist;
    }
    
    public List<ShortList> getShortlist_list() {
        return shortlist_list;
    }

    public void setShortlist_list(List<ShortList> shortlist_list) {
        this.shortlist_list = shortlist_list;
    }

    public List<ShortList> getStd_shortlistSv() {
        return std_shortlistSv;
    }

    public void setStd_shortlistSv(List<ShortList> std_shortlistSv) {
        this.std_shortlistSv = std_shortlistSv;
    }

    public FinalProject getFinalProject() {
        return finalProject;
    }

    public void setFinalProject(FinalProject finalProject) {
        this.finalProject = finalProject;
    }

    public List<FinalProject> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<FinalProject> projectList) {
        this.projectList = projectList;
    }

    public ShortlistBusiness getSb() {
        return sb;
    }

    public void setSb(ShortlistBusiness sb) {
        this.sb = sb;
    }

    public Student getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(Student currentStudent) {
        this.currentStudent = currentStudent;
    }   

    public FinalProject getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(FinalProject currentProject) {
        this.currentProject = currentProject;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ShortList getForStdUpdate() {
        return forStdUpdate;
    }

    public void setForStdUpdate(ShortList forStdUpdate) {
        this.forStdUpdate = forStdUpdate;
    }

    public List<ShortList> getStd_shortlistProject() {
        return std_shortlistProject;
    }

    public void setStd_shortlistProject(List<ShortList> std_shortlistProject) {
        this.std_shortlistProject = std_shortlistProject;
    }

    public List<Staff> getSupervisor_list() {
        return supervisor_list;
    }

    public void setSupervisor_list(List<Staff> supervisor_list) {
        this.supervisor_list = supervisor_list;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getSearchKeywordForPI() {
        return searchKeywordForPI;
    }

    public void setSearchKeywordForPI(String searchKeywordForPI) {
        this.searchKeywordForPI = searchKeywordForPI;
    }

    public ShortListProperties getSvMax() {
        return svMax;
    }

    public void setSvMax(ShortListProperties svMax) {
        this.svMax = svMax;
    }

    public ShortListProperties getProjectMax() {
        return projectMax;
    }

    public void setProjectMax(ShortListProperties projectMax) {
        this.projectMax = projectMax;
    }

    public int getShortListCurrentSize() {
        return shortListCurrentSize;
    }

    public void setShortListCurrentSize(int shortListCurrentSize) {
        this.shortListCurrentSize = shortListCurrentSize;
    }

    public int getShortListSvCurrentSize() {
        return shortListSvCurrentSize;
    }

    public void setShortListSvCurrentSize(int shortListSvCurrentSize) {
        this.shortListSvCurrentSize = shortListSvCurrentSize;
    }

    public List<FinalProject> getNotAllocatedProject() {
        return notAllocatedProject;
    }

    public void setNotAllocatedProject(List<FinalProject> notAllocatedProject) {
        this.notAllocatedProject = notAllocatedProject;
    }

    public Boolean getInsertShortlist() {
        return insertShortlist;
    }

    public void setInsertShortlist(Boolean insertShortlist) {
        this.insertShortlist = insertShortlist;
    }

    public Boolean getShortlistExist() {
        return shortlistExist;
    }

    public void setShortlistExist(Boolean shortlistExist) {
        this.shortlistExist = shortlistExist;
    }

    public Boolean getShortlistMax() {
        return shortlistMax;
    }

    public void setShortlistMax(Boolean shortlistMax) {
        this.shortlistMax = shortlistMax;
    }


    @EJB
    private ShortlistBusiness sb;
    
    public void getAllStudents(){
        System.err.println(sb.getAllStudents().size());
        setStudents(sb.getAllStudents());
    }
    
    public void getShortlistData() throws BusinessExceptions{
        setShortlist_list(sb.getShortlists());        
    }
    
    public void getShortListProjectMaxSize(){
        setProjectMax(sb.getShortlistProjectMax());
    }
    
    public void getShortListSvMaxSize(){
        setSvMax(sb.getShortlistSvMax());
    }  

    public int getShortListSize() {
        return shortListSize;
    }

    public void setShortListSize(int shortListSize) {
        this.shortListSize = shortListSize;
    }

    public int getShortListSvSize() {
        return shortListSvSize;
    }

    public void setShortListSvSize(int shortListSvSize) {
        this.shortListSvSize = shortListSvSize;
    }
     
    public void setCurrentStudentShortlist(){
        setStd_shortlistProject(sb.getRankedShortList(getCurrentStudent().getId()));
        System.out.println("here: " + getStd_shortlistProject().size());
    }
    
    public void setCurrentStudentSvShortlist(){
        setStd_shortlistSv(sb.getRankedShortListSv(getCurrentStudent().getId()));
        System.out.println("here: " + getStd_shortlistProject().size());
    }
    
    public void addToShortlist(FinalProject p) throws BusinessExceptions{      
        System.out.println("s controller");
        getShortListProjectMaxSize();
        int maxSize = getProjectMax().getShortlistMax();
        System.out.println("Size: "+maxSize );
        setShortListSize(sb.getProjectSize(getCurrentStudent()));
        System.out.println("Size ShortList: "+getShortListSize() );
        if(!sb.checkExistingShortList(p, getCurrentStudent())){
            if(getShortListSize() < maxSize){
                setCurrentProject(p);
                shortlist.setFinalProject(getCurrentProject());
                shortlist.setStudent(getCurrentStudent());
                shortlist.setRank(getStd_shortlistProject().size() + 1);
                shortlist.setIsProject(Boolean.TRUE);
                shortlist.setIsSupervisor(Boolean.FALSE);
                shortlist.setSupervisor(null);
                sb.addtoShortlist(shortlist,getCurrentStudent());                
                System.out.println("project added to shorlits");
                setShortListCurrentSize(getShortListSize() + 1);
                setInsertShortlist(Boolean.TRUE);
                setShortlistExist(Boolean.FALSE);
                setShortlistMax(Boolean.FALSE);
            }else{
                setInsertShortlist(Boolean.FALSE);
                setShortlistExist(Boolean.FALSE);
                setShortlistMax(Boolean.TRUE);
                System.out.println("You have reach the maximum size of your project shortlist");
            }        
        }else{
            setInsertShortlist(Boolean.FALSE);
            setShortlistExist(Boolean.TRUE);
            setShortlistMax(Boolean.FALSE);
            System.out.println("Project Already exist in the shortlist");
        }
        getStd_shortlistProject().clear();
        setCurrentStudentShortlist();
//        return "homePage.xhtml";
    }
    
    public String addSvToShortlist(Staff s) throws BusinessExceptions{
        getShortListSvMaxSize();
        int maxSize = getSvMax().getShortlistMax();
        System.out.println("Sizesv: "+maxSize );
        setShortListSvSize(sb.getSupervisorSize(getCurrentStudent()));
        if(!sb.checkExistingShortListSv(s, getCurrentStudent())){
            if(getShortListSvSize() < maxSize){
                setStaff(s);
                shortlist.setFinalProject(null);
                shortlist.setSupervisor(getStaff());
                shortlist.setStudent(getCurrentStudent());
                shortlist.setRank(getShortListSvSize() + 1);
                shortlist.setIsProject(Boolean.FALSE);
                shortlist.setIsSupervisor(Boolean.TRUE);
                sb.addtoShortlist(shortlist,getCurrentStudent());   
                setShortListSvCurrentSize(getShortListSvSize() + 1);
                System.out.println("sv added to shorlits");
                setInsertShortlist(Boolean.TRUE);
                setShortlistExist(Boolean.FALSE);
                setShortlistMax(Boolean.FALSE);
//                shortlistSv.setStudentID(getCurrentStudent().getId());
//                shortlistSv.setStaff(getStaff());
//                shortlistSv.setRank(getShortListSvSize() + 1);
//                sb.addtoShortlistSv(shortlistSv, getCurrentStudent());                
                            
            }else{
                setInsertShortlist(Boolean.FALSE);
                setShortlistExist(Boolean.FALSE);
                setShortlistMax(Boolean.TRUE);
                System.out.println("You have reach the maximum size of your supevisor shortlist");
            }
        }else{
            setInsertShortlist(Boolean.FALSE);
            setShortlistExist(Boolean.TRUE);
            setShortlistMax(Boolean.FALSE);
            System.out.println("Sv already Exist in shortlist");
        }
        getStd_shortlistSv().clear();
        setCurrentStudentSvShortlist();
        return "supervisorPage.xhtml";
    }
    
    public void getAllProjectIdeas() throws BusinessExceptions{        
        projectideas = sb.getAllProject();
        System.out.println("project: " + getProjectideas().size());
        for (int i = 0; i < getProjectideas().size(); i++) {
            FinalProject finalpr = new FinalProject();
            finalpr.setIdea(getProjectideas().get(i));
            sb.addprojectidea(finalpr);
            System.out.println("no : " + i);
        }
    }
    
    public void getAllSupervisor(){
        System.out.println("here");
        setSupervisor_list(sb.getAllSupervisorFromDB());
    }
    
    public String allocateProject(FinalProject f) throws BusinessExceptions{
        setCurrentProject(f);
        return "allocateProjectPage.xhmtl";
    }
         
    @PostConstruct
    public void onPageLoad(){
        getAllStudents();
        getAllSupervisor();
        getShortListProjectMaxSize();
        getShortListSvMaxSize();
        try {
            getAllProjectIdeas();
        } catch (BusinessExceptions ex) {
            Logger.getLogger(ShortlistController.class.getName()).log(Level.SEVERE, null, ex);
        }
        setProjectList(sb.getAllProjectApproved());
        System.out.println("set projet idea done" + sb.getAllProjectApproved().size());
        System.out.println("list final project : " + getProjectList().size());
    }
    
    @EJB
    ProjectMonitoringBussiness pm;
    
    public void changeStudent(ValueChangeEvent e) throws BusinessExceptions {        
        selectedid = (Long) e.getNewValue();
        setSelectedid(selectedid);
        setCurrentStudent(pm.getStudentswithId(getSelectedid()));
        setCurrentStudentShortlist();
        setCurrentStudentSvShortlist();
        setShortListSize(getStd_shortlistProject().size());
        setShortListSvSize(getStd_shortlistSv().size());
    }
    
    public void deleteFromShortlist(ShortList s) throws BusinessExceptions{    
        
        if(s.getIsProject()){
            sb.deleteShortList(s,getCurrentStudent(), getShortListCurrentSize());
            setCurrentStudentShortlist();
            setShortListCurrentSize(getShortListCurrentSize()- 1);
        }else{
            sb.deleteSv(s,getCurrentStudent(), getShortListSvCurrentSize());
            setCurrentStudentSvShortlist();
            setShortListSvCurrentSize(getShortListSvCurrentSize()- 1);
        } 
        System.out.println("deleted from shortlist");
    }
    
//    public void deleteFromShortlistSv(ShortList s) throws BusinessExceptions{                
//        sb.deleteSv(s,getCurrentStudent());
//        System.out.println("deleted from shortlist");
//        setCurrentStudentShortlist();
//        System.out.println("deleted from shortlist");
//    }
    
       
    public String getSearchProjectKeyword(){
        setProjectList(sb.getProjectIdeaSearch(getSearchKeywordForPI()));
        System.out.println("s controller search keyword for project");
        System.out.println("list final project : " + getProjectList().size());
        return "homePage.xhtml";
    }
    
    public String resetSearch() throws BusinessExceptions{
        setProjectList(sb.getAllProjectApproved());
        System.out.println("list final project : " + getProjectList().size());
        return "homePage.xhtml";
    }
    
    
    public void rankingShortlistUp(ShortList s, int value) throws BusinessExceptions{
        sb.voteUp(s, value, getCurrentStudent().getId()); 
        getStd_shortlistProject().clear();            
        setCurrentStudentShortlist();
        System.out.println("system ranking up");
    }
    
    public void rankingShortlistDown(ShortList s, int value) throws BusinessExceptions{
        sb.voteDown(s, value, getCurrentStudent().getId());
        getStd_shortlistProject().clear();       
        setCurrentStudentShortlist();
        System.out.println("system ranking down");
    }
    
    public void rankingSvShortlistUp(ShortList s, int value) throws BusinessExceptions{
        sb.voteUpSv(s, value, getCurrentStudent().getId());
        getStd_shortlistSv().clear();
        setCurrentStudentSvShortlist();
    }
    
    public void rankingSvShortlistDown(ShortList s, int value) throws BusinessExceptions{
        sb.voteDownSv(s, value, getCurrentStudent().getId());
        getStd_shortlistSv().clear();
        setCurrentStudentSvShortlist();
    }
    
    public ShortlistController() {
    }
    
}
