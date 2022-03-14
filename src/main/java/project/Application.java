package project;

import project.DAO.DataDAO;
import project.DAO.DeviceDAO;
import project.Model.DevicesEntity;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import project.mercury.MercuryTCP;
import project.service.ReadData;

import java.util.List;


public class Application {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContext.xml"
        );
        ReadData readData = context.getBean(ReadData.class);
//        readData.readDataAll();
        readData.readDataAllSave();

    }
}
