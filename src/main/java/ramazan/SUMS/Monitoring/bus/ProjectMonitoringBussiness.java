/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ramazan.SUMS.Monitoring.bus;

import ramazan.SUMS.Monitoring.session.MilestoneFeedbackFacade;
import ramazan.SUMS.Monitoring.session.MilestoneFacade;
import Bus.BusinessExceptions;
import syafiqah.SUMS.Allocation.Entities.AllocatedProject;
import Entities.FinalProject;
import ramazan.SUMS.Monitoring.entities.Milestone;
import Entities.Staff;
import ramazan.SUMS.Monitoring.entities.MilestoneFeedback;
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
public class ProjectMonitoringBussiness {

    @EJB
    private ProjectIdeaDAO psf;
    @EJB
    private StudentFacade sf;
    @PersistenceContext
    private EntityManager em;
    @EJB
    FinalProjectFacade pf;
    @EJB
    MilestoneFacade mf;
    @EJB
    StaffFacade staf;

    @EJB
    MilestoneFeedbackFacade feedf;

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

    public void updateMilestone(Milestone m, String status, Date currentDate) throws BusinessExceptions {
        m.setStatus(status);
        m.setSubmittedDate(currentDate);
        mf.edit(m);
        em.flush();

    }

    public List<Staff> allStaf() throws BusinessExceptions {

        return staf.findAll();
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
        if(staf.find(id)==null){
            throw new BusinessExceptions("staff could not be found");
        }
        
        return staf.find(id);
    }
  public List <Staff> findStaffWithProjectId(Long id)
  {
  return mf.findStaffwithProjectId(id);
  }
    public void insertFeedback(MilestoneFeedback feedback, Milestone m, Staff s, Date currentdate) throws BusinessExceptions{
        
        MilestoneFeedback f = new MilestoneFeedback();
        f.setComment(feedback.getComment());
        f.setCommentDate(currentdate);
        f.setCommentActivateDate(feedback.getCommentActivateDate());
        f.setStaff(s);
        m = mf.edit(m);
        feedf.create(f);
        f = feedf.find(f.getId());
        m.getFeedbacks().add(f);
        em.flush();
        System.out.println(m.getId());
        
        
        }

    public void deleteMilestone(Milestone m, FinalProject f) throws BusinessExceptions{
        m = mf.edit(m);
        f = pf.edit(f);
        f.getMilestones().remove(m);
        mf.remove(m);
        em.flush();

    }

    public Milestone getMilestonewithId(Long id) throws BusinessExceptions{
        if(mf.find(id)==null)
        {
       throw new BusinessExceptions("milestone could not be found");
        }
        return mf.find(id);
    }

    public void deleteFeedback(MilestoneFeedback f, Milestone m) throws BusinessExceptions {
        em.flush();
        m = mf.edit(m);
        f = feedf.edit(f);
        m.getFeedbacks().remove(f);
        feedf.remove(f);
        em.flush();
    }

    public void addMilestone(Milestone m, FinalProject f) throws BusinessExceptions {
        mf.create(m);
        f = pf.edit(f);
        f.getMilestones().add(m);

    }

    public FinalProject getFinalProjectwithID(long id) throws BusinessExceptions{
        return pf.find(id);
    }

    public void businessMethod() {
    }
}
