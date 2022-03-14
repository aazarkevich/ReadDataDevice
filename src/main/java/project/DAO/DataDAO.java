package project.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import project.Model.DataEntity;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Locale;

@Component
public class DataDAO {
    private Session session;

    @PostConstruct
    public void openSession() {
        this.session = hibernateUtil.getOurSessionFactory().openSession();
    }

    @PreDestroy
    public void closeSession() {
        session.close();
    }

    public DataEntity read(Long id) {
        return (DataEntity) session.createQuery("from DataEntity where id = id").getSingleResult();
    }
    public List<DataEntity> readAll(){
        return session.createQuery("from DataEntity ").list();
    }

    public void save(DataEntity dataEntity) {
        session.beginTransaction();
        session.save(dataEntity);
        session.getTransaction().commit();
    }

    public void saveAll(List<DataEntity> listData) {
        for(DataEntity data : listData) {
            System.out.println(data);
            save(data);
        }
    }

    public void update(DataEntity dataEntity) {
        session.beginTransaction();
        session.update(dataEntity);
        session.getTransaction().commit();
    }

    public void delete(Long id) {
        session.beginTransaction();
        session.delete(read(id));
        session.getTransaction().commit();
    }
}
