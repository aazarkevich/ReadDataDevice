package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.DAO.DataDAO;
import project.DAO.DeviceDAO;
import project.Model.DataEntity;
import project.Model.DevicesEntity;
import project.mercury.MercuryTCP;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReadData {
    @Autowired
    private DeviceDAO deviceDAO;
    @Autowired
    private DataDAO dataDAO;

    public List<DataEntity> readDataAll() {
        List<DevicesEntity> listDevice = deviceDAO.getDevices();
        List<DataEntity> rezult = new ArrayList<>();
        for (DevicesEntity device : listDevice) {
            MercuryTCP mercuryTCP = new MercuryTCP(device);
            System.out.println(mercuryTCP.toString());
            rezult.add(mercuryTCP.getValues());
//            break;
        }
        return rezult;
    }

    public void readDataAllSave() {
        dataDAO.saveAll(readDataAll());
    }
}
