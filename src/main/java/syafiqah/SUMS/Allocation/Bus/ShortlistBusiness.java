/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syafiqah.SUMS.Allocation.Bus;

import Bus.BusinessExceptions;
import Entities.FinalProject;
import syafiqah.SUMS.Allocation.Entities.ShortList;
import syafiqah.SUMS.Allocation.Entities.ShortListProperties;
import syafiqah.SUMS.Allocation.Entities.ShortListSv;
import Entities.Staff;
import Entities.Student;
import Sessions.FinalProjectFacade;
import syafiqah.SUMS.Allocation.Sessions.ShortListFacade;
import syafiqah.SUMS.Allocation.Sessions.ShortListPropertiesFacade;
import syafiqah.SUMS.Allocation.Sessions.ShortListSvFacade;
import Sessions.StaffFacade;
import Sessions.StudentFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import uk.ac.port.SUMS.ProjectIdeas.model.ProjectIdea;

/**
 *
 * @author ASUS
 */
@Stateless
public class ShortlistBusiness {

   @EJB
    private ShortListFacade sf;
    @PersistenceContext
    private EntityManager em;
    @EJB
    private FinalProjectFacade fpf;
    @EJB
    private StudentFacade stdf;
    @EJB
    private ShortListSvFacade svf;
    
    public Student getStudentswithId(Long id) throws BusinessExceptions {
        Student s = stdf.find(id);
        if (s == null) {
            throw new BusinessExceptions("Student does not exit");
        }
        return stdf.find(id);
    }
    
    public List<ShortList> getShortlists() throws BusinessExceptions{
        return sf.findAll();
    }
    
    public void addtoShortlist(ShortList s, Student i) throws BusinessExceptions{
        
        sf.create(s);
        i = stdf.edit(i);
        i.getShortLists().add(s);
    }     
        
    public void deleteShortList(ShortList s, Student i, int size) throws BusinessExceptions{
        editRanks(s.getRank(), size, i);
        s = sf.edit(s);
        i = stdf.edit(i);
        i.getShortLists().remove(s);                
        sf.remove(s);  
    }
    
    public void editRanks(int shortListRank, int size, Student std){
        System.out.println("size: " + size + " rank: " + shortListRank);
        for(int i=shortListRank;i<size;i++){
            updateNextShortList(i, std.getId());
            System.out.println("value of i: " + i);
        }
    }
    
    public void deleteSv(ShortList s, Student i, int size) throws BusinessExceptions{
        editSvRanks(s.getRank(), size, i);
        s = sf.edit(s);
        i = stdf.edit(i);
        i.getShortLists().remove(s);
        sf.remove(s);
    }
    
    public void editSvRanks(int shortListRank, int size, Student std){
        System.out.println("size: " + size + " rank: " + shortListRank);
        for(int i=shortListRank;i<size;i++){
            updateNextShortListSv(i, std.getId());
            System.out.println("value of i: " + i);
        }
    }
    
    public void voteUp(ShortList s, int value, Long stdID) throws BusinessExceptions{        
        int tempValue = value - 1;
        updateBeforeShortList(value, stdID);
        System.out.println("tempvalue :"  + tempValue);        
        s = sf.edit(s);
        s.setRank(tempValue);
        
    }
    
    public void updateBeforeShortList(int value, Long stdID){
        ShortList s2;
        int tempValue = value - 1;
        s2 = sf.getShortlistWithRank(tempValue, stdID);
        s2 = sf.edit(s2);
        s2.setRank(value);
        System.out.println("update tempvalue :"  + tempValue); 
    }
    
    public void voteUpSv(ShortList s, int value, Long stdID) throws BusinessExceptions{        
        int tempValue = value - 1;
        updateBeforeShortListSv(value, stdID);
        System.out.println("tempvalue :"  + tempValue);        
        s = sf.edit(s);
        s.setRank(tempValue);
        
    }
    
    public void updateBeforeShortListSv(int value, Long stdID){
        ShortList s2;
        int tempValue = value - 1;
        s2 = sf.getShortlistSvWithRank(tempValue, stdID);
        s2 = sf.edit(s2);
        s2.setRank(value);
        System.out.println("update tempvalue :"  + tempValue); 
    }
    
    public List<ShortList> getRankedShortList(Long stdID){
        return sf.getShortListOrderedByRank(stdID);
    }
    
    public List<ShortList> getRankedShortListSv(Long stdID){
        return sf.getShortListSvOrderedByRank(stdID);
    }
    
    public void voteDown(ShortList s, int value, Long stdID) throws BusinessExceptions{
        
        int tempValue = value + 1;
        updateNextShortList(value, stdID);
        System.out.println("check tempvalue: " + tempValue);
        s = sf.edit(s);
        s.setRank(tempValue);
    }
    
    public void updateNextShortList(int value, Long stdID){
        ShortList s2;
        int tempValue = value + 1;
        s2 = sf.getShortlistWithRank(tempValue, stdID);
        s2 = sf.edit(s2);
        s2.setRank(value);
        System.out.println("update tempvalue :"  + tempValue); 
    }
    
    public void voteDownSv(ShortList s, int value, Long stdID) throws BusinessExceptions{
        
        int tempValue = value + 1;
        updateNextShortListSv(value, stdID);
        System.out.println("check tempvalue: " + tempValue);
         s = sf.edit(s);
        s.setRank(tempValue);
    }
    
    public void updateNextShortListSv(int value, Long stdID){
        ShortList s2;
        int tempValue = value + 1;
        s2 = sf.getShortlistSvWithRank(tempValue, stdID);
        s2 = sf.edit(s2);
        s2.setRank(value);
        System.out.println("update tempvalue :"  + tempValue); 
    }
    
    public List<FinalProject> getProjectList() throws BusinessExceptions{
        return fpf.findAll();
    }
    
    public void addprojectidea(FinalProject fp) throws BusinessExceptions {
        try {
            if (fpf.Isthere(fp)) {
                System.out.println("Bus.ProjectMonitoringBussiness.addprojectidea()");
            } else {
                System.out.println("yok");
                em.persist(fp);
            }
        } catch (Exception e) {
        }
    }    
    
    public Boolean checkExistingShortList(FinalProject fp, Student s){
        return sf.IsthereProject(fp.getId(), s.getId());
    }
        
    public int getProjectSize(Student s){
        return sf.getShortListProjectSize(s.getId()).size();
    }
    
    public Boolean checkExistingShortListSv(Staff staff, Student s){
        return sf.IsthereSv(staff.getId(), s.getId());
    }
    
    public int getSupervisorSize(Student s){
        return sf.getShortListSvSize(s.getId()).size();
    }
    
    @EJB
    private ShortListPropertiesFacade spf;
    
    public ShortListProperties getShortlistProjectMax(){
        return spf.getShortlistProjectMax();
    }
    
    public ShortListProperties getShortlistSvMax(){
        return spf.getShortlistSvMax();
    }
    
    @EJB
    private StaffFacade staffFacade;
    
    public List<Staff> getAllSupervisorFromDB(){
        return staffFacade.returnAllSupervisor();
    }
        
    public List<ProjectIdea> getAllProject(){
        return fpf.projectsIdeas();
    }
    
    public List<FinalProject> getAllProjectApproved(){
        return fpf.findAll();
    }
    
    public List<Student> getAllStudents() {
        return stdf.getStudentID();
    }

    public Student getStudentswithid(Long id) {
        return stdf.find(id);
    }
    
    public List<FinalProject> getProjectIdeaSearch(String searchKeyword){
        return fpf.getProjectIdeaSearch(searchKeyword);
    }
}
