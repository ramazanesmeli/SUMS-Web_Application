package jinet.SUMS.Marking.Session;

import syafiqah.SUMS.Allocation.Entities.AllocatedProject;
import Entities.FinalProject;
import Entities.Staff;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jinet.SUMS.Marking.Entities.Grade;
import Sessions.AbstractFacade;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.TypedQuery;

@Stateless
@Named("marks")
public class GradeFacade extends AbstractFacade<Grade> {

    @PersistenceContext(unitName = "uk.ac.port.SUMS.PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GradeFacade() {
        super(Grade.class);
    }

    public List<FinalProject> findcoordinatorprojects(long cohortid) {
        TypedQuery<Staff> Query = getEntityManager().createNamedQuery("Project.cohort", Staff.class)
                .setParameter("cohortid",cohortid );
        List <FinalProject> fprojectlist=new ArrayList<>();
        Query.getResultList().stream().forEach((resultList) -> {
            resultList.getFinalProject().stream().forEach((resultList2) -> {
              if(!fprojectlist.contains(resultList2))
                fprojectlist.add(resultList2);
            });
        });
    return fprojectlist;
    }
     public List<AllocatedProject> findStudentAllocatedProject(long id) {
        TypedQuery<AllocatedProject> Query = getEntityManager().createNamedQuery("Project.findByStudentId", AllocatedProject.class)
                .setParameter("studentid",id );
      return Query.getResultList();
    }
     public List<Staff> findStaffwithProjectId(long id) {
        TypedQuery<Staff> Query = getEntityManager().createNamedQuery("Staff.findStaffwithProjectId", Staff.class)
                .setParameter("projectid",id );
      return Query.getResultList();
    }

}
