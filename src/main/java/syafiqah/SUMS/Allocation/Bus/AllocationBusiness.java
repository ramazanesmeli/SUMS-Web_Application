/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syafiqah.SUMS.Allocation.Bus;

import Bus.BusinessExceptions;
import syafiqah.SUMS.Allocation.Entities.AllocatedProject;
import Entities.FinalProject;
import Entities.Staff;
import Entities.Student;
import Sessions.FinalProjectFacade;
import syafiqah.SUMS.Allocation.Sessions.AllocatedProjectFacade;
import Sessions.StaffFacade;
import Sessions.StudentFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import syafiqah.SUMS.Allocation.Entities.ShortList;
import syafiqah.SUMS.Allocation.Entities.ShortListProperties;
import syafiqah.SUMS.Allocation.Sessions.ShortListFacade;
import syafiqah.SUMS.Allocation.Sessions.ShortListPropertiesFacade;
import uk.ac.port.SUMS.ProjectIdeas.model.ProjectIdea;

/**
 *
 * @author ASUS
 */
@Stateless
public class AllocationBusiness {

    @EJB
    private AllocatedProjectFacade apf;
    public void addAllocatedProject(AllocatedProject p){
        apf.create(p);
        changeProjectStatus(p.getFinalProject());
    }
    
    public List<AllocatedProject> getALlocatedProject(){
         return apf.findAll();
    }
    
    public Boolean checkExistence(FinalProject p){
        return apf.Isthere(p.getId());
    }
    
    public AllocatedProject getAllocatedProjectByID(FinalProject p){
        return apf.getAllocatedProjectID(p.getId());
    }
    
    
    public Boolean checkStaffProject(Staff s){
        return apf.checkSvProject(s.getId());
    }
    
    public void editProjectNewInfo(AllocatedProject p, Student s, Staff staff){
        p = apf.edit(p);
        p.setStudent(s);
        p.setStaffs(staff);
    }
    
    
   @EJB
    private StudentFacade sf;
    public List<Student> getAllStudentsByProject(long projectID){
        return sf.getAllStudentByProject(projectID);        
    }
    
    public List<Student> getAllStudent(){
        return sf.findAll();
    }
    
    public void addProjectToStudent(Student s, FinalProject p) throws BusinessExceptions{
        s = sf.edit(s);
        s.setFinalProject(p);
    }
    
    public List<Student> getAllStudentForAllocation(){
        return sf.findAll();
    }
    
    public void editProjectStudent(AllocatedProject p, Student beforeStd, Student afterStd) throws BusinessExceptions{
//        p = apf.edit(p);
        beforeStd = sf.edit(beforeStd);
        beforeStd.setFinalProject(null);
//        p.setStudent(afterStd);
        System.out.println("after student" + afterStd.getPerson().getFullName() + " now: " + p.getStudent().getPerson().getFullName());
        editNewStudent(p, afterStd);
    }
    
    public void editNewStudent(AllocatedProject p, Student afterStd){
//        p = apf.edit(p);
//        p.setStudent(afterStd);
        afterStd = sf.edit(afterStd);
        afterStd.setFinalProject(p.getFinalProject());  
    }
    
    @EJB
    private StaffFacade staffF;
    public Staff getSelectedStaff(long svID){
        return staffF.find(svID);
    }
    
    public List<Staff> getAllSupervisorFromDB(){
        return staffF.returnAllSupervisor();
    }
    
    public void addProjectToStaff(Staff s, FinalProject fp) throws BusinessExceptions{
        s = staffF.edit(s);
        s.getFinalProject().add(fp);
    }
    
    public void editProjectSupervisor(AllocatedProject p, Staff beforeStaff, Staff afterStaff){
//        p = apf.edit(p);
        beforeStaff = staffF.edit(beforeStaff);
        beforeStaff.getFinalProject().remove(p.getFinalProject());
//        p.setStaffs(afterStaff);
        if(afterStaff != null){
            editNewSupervisor(p, afterStaff);
        }else
            System.out.println("staff is: " + afterStaff);
        
        
    }
    
    public void editNewSupervisor(AllocatedProject p, Staff afterStaff){
        afterStaff = staffF.edit(afterStaff);
        afterStaff.getFinalProject().add(p.getFinalProject());
    }
    
    @EJB
    private ShortListFacade shortListf;
    public List<ShortList> getRankedShortList(Long stdID){
        return shortListf.getShortListOrderedByRank(stdID);
    }
    
    public List<ShortList> getRankedShortListSv(Long stdID){
        return shortListf.getShortListSvOrderedByRank(stdID);
    }
    
    public List<Student> getAllStudentsByProject(Long projectID){
        return shortListf.getStudentByProject(projectID);        
    }
    
    public ShortList getShortlistwithid(Long id) {
        return shortListf.find(id);
    }

    @EJB
    private ShortListPropertiesFacade spf;
    public List<ShortListProperties> getAllProperties(){
        return spf.findAll();
    }
    
    public void editProperties(ShortListProperties sp, int newValue){
        sp = spf.edit(sp);
        sp.setShortlistMax(newValue);
    }
    
    @EJB
    private FinalProjectFacade fpf;
    public List<FinalProject> getAllApprovedProject(){
        return fpf.approvedFinalProject();
    }
    
    public void changeProjectStatus(FinalProject p){ 
        //p.getIdea().getOwner();
        p = fpf.edit(p);
        p.getIdea().ChangeStatus(ProjectIdea.Statuses.Allocated, p.getIdea().getOwner());
        System.out.println("Project Status Changed : " + p.getIdea().getStatus());
    }
    
}
