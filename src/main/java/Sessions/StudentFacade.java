package Sessions;

import Bus.BusinessExceptions;
import Entities.FinalProject;
import Entities.Staff;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import Entities.Student;
import Sessions.AbstractFacade;
import java.util.List;
import javax.persistence.TypedQuery;

@Stateless
@Named("student")
public class StudentFacade extends AbstractFacade<Student> {

    @PersistenceContext(unitName = "uk.ac.port.SUMS.PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

     public List<Student> getStudentID(){
        return em.createNamedQuery("Student.exmpleStudentID").getResultList();
    }
    
    public void updateStudent(Student s) throws BusinessExceptions{
        System.out.println("sfc : " + s.getShortLists().size());
        em.merge(s);
        System.out.println("yes");
    }
    
    public List<Student> getAllStudentByProject(long projectID){        
        TypedQuery<Student> Query = getEntityManager().createNamedQuery("Student.getByProject", Student.class);
        Query.setParameter("value1", projectID);
        System.out.println("Query : " + projectID);
        return Query.getResultList();
    }

    public StudentFacade() {
        super(Student.class);
    }

}
