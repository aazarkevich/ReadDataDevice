package project.DAO;

import project.Model.DevicesEntity;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@Component
public class DeviceDAO {
    private Session session;

    @PostConstruct
    public void openSession() {
        this.session = hibernateUtil.getOurSessionFactory().openSession();
    }

    @PreDestroy
    public void closeSession() {
        session.close();
    }

    public List<DevicesEntity> getDevices() {
        return session.getSession().createQuery("from DevicesEntity ").list();
    }

}
