package ourbusinessproject;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class EnterpriseProjectService {

    @PersistenceContext
    private EntityManager entityManager;

    public Project saveProjectForEnterprise(Project project, Enterprise enterprise) {
        saveEnterprise(enterprise);    	
        
    	Project proj = new Project();
    	proj.setId(project.getId());
    	proj.setTitle(project.getTitle());
    	proj.setDescription(project.getDescription());    	
    	proj.setEnterprise(enterprise);
    	
    	enterprise.addProject(proj);
    	
    	entityManager.merge(proj);    	
        entityManager.flush();
        return proj;
    }

    public Enterprise saveEnterprise(Enterprise enterprise) {    	
    	Enterprise ent = new Enterprise();
    	ent.setId(enterprise.getId());
    	ent.setName(enterprise.getName());
    	ent.setDescription(enterprise.getDescription());
    	ent.setContactName(enterprise.getContactName());
    	ent.setContactEmail(enterprise.getContactEmail());
    	
        entityManager.merge(ent);
        entityManager.flush();
        return ent;
    }

    public Project findProjectById(Long id) {
        return entityManager.find(Project.class, id);
    }

    public Enterprise findEnterpriseById(Long id) {
        return entityManager.find(Enterprise.class, id);
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Project> findAllProjects() {
        TypedQuery<Project> query = entityManager.createQuery("select p from Project p join fetch p.enterprise order by p.title", Project.class);
        return query.getResultList();
    }
}
