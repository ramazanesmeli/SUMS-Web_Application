/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jinet.SUMS.Marking.Bus;

import jinet.SUMS.Marking.Session.GradeUpdateFacade;
import jinet.SUMS.Marking.Session.GradeFacade;
import Bus.BusinessExceptions;
import syafiqah.SUMS.Allocation.Entities.AllocatedProject;
import Entities.FinalProject;
import jinet.SUMS.Marking.Entities.Grade;
import Entities.Staff;
import jinet.SUMS.Marking.Entities.GradeUpdate;
import Sessions.*;
import Entities.Student;
import java.util.Date;
import java.util.List;
import uk.ac.port.SUMS.ProjectIdeas.persistence.*;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import uk.ac.port.SUMS.ProjectIdeas.model.ProjectIdea;

/**
 *
 * @author a
 */
@Stateless
public class ProjectMarkingBusiness {

    @EJB
    private ProjectIdeaDAO psf;
    @EJB
    private StudentFacade sf;
    @PersistenceContext
    private EntityManager em;
    @EJB
    FinalProjectFacade pf;
    @EJB
    GradeFacade mf;
    @EJB
    StaffFacade staff;

    @EJB
    GradeUpdateFacade feedf;

    public List<Student> getStudents() throws BusinessExceptions {
        return sf.findAll();
    }

    public Student getStudentswithId(Long id) throws BusinessExceptions {
        Student s = sf.find(id);
        if (s == null) {
            throw new BusinessExceptions("Student does not exit");
        }
        return sf.find(id);
    }

    public List<ProjectIdea> getAllApprovedIdeas() throws BusinessExceptions {
        return pf.projectsIdeas();
    }

    public void addProjectIdea(ProjectIdea pi) throws BusinessExceptions {
        try {
            FinalProject fp = new FinalProject();
            fp.setIdea(pi);
            if (pf.Isthere(fp)) {

            } else {
                pf.create(fp);
            }
        } catch (Exception e) {
            throw new BusinessExceptions("an error occured while setting final project items", e);
        }
    }


    public List<Staff> allStaff() throws BusinessExceptions {

        return staff.findAll();
    }

    public List<FinalProject> getstaffprojects(long id,long cohortid) throws BusinessExceptions // 
    {
    
        return mf.findcoordinatorprojects(cohortid);

    }

    public List<AllocatedProject> getStudentAllocatedProject(Student s) // 
    {

        return mf.findStudentAllocatedProject(s.getId());
    }

    public Staff findStaffwithId(Long id) throws BusinessExceptions{
        if(staff.find(id)==null){
            throw new BusinessExceptions("staff could not be found");
        }
        
        return staff.find(id);
    }
  public List <Staff> findStaffWithProjectId(Long id)
  {
  return mf.findStaffwithProjectId(id);
  }
    public void insertUpdate(GradeUpdate update, Grade m, Staff s, Date currentdate) throws BusinessExceptions{
        
        GradeUpdate f = new GradeUpdate();
        f.setComment(update.getComment());
        f.setCommentDate(currentdate);
        f.setCommentActivateDate(update.getCommentActivateDate());
        f.setStaff(s);
        m = mf.edit(m);
        feedf.create(f);
        f = feedf.find(f.getId());
        m.getUpdates().add(f);
        em.flush();
        System.out.println(m.getId());
        
        
        }

    public void deleteGrade(Grade m, FinalProject f) throws BusinessExceptions{
        m = mf.edit(m);
        f = pf.edit(f);
        f.getGrades().remove(m);
        mf.remove(m);
        em.flush();

    }

    public Grade getGradewithId(Long id) throws BusinessExceptions{
        if(mf.find(id)==null)
        {
       throw new BusinessExceptions("grade could not be found");
        }
        return mf.find(id);
    }

    public void deleteUpdate(GradeUpdate f, Grade m) throws BusinessExceptions {
        em.flush();
        m = mf.edit(m);
        f = feedf.edit(f);
        m.getUpdates().remove(f);
        feedf.remove(f);
        em.flush();
    }

    public void addGrade(Grade m, FinalProject f) throws BusinessExceptions {
        mf.create(m);
        f = pf.edit(f);
        f.getGrades().add(m);

    }

    public FinalProject getFinalProjectwithID(long id) throws BusinessExceptions{
        return pf.find(id);
    }

    public void businessMethod() {
    }
}
