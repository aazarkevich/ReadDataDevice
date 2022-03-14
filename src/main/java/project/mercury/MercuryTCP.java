package project.mercury;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import project.DAO.DeviceDAO;
import project.Model.DataEntity;
import project.Model.DevicesEntity;

import javax.management.ConstructorParameters;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class MercuryTCP extends Mercury {

    private DevicesEntity device;
    private Socket socket = new Socket();
    private DataInputStream in;
    private DataOutputStream out;
    private String valuesEnergy;
    private String valuesPower;

    public MercuryTCP(DevicesEntity device) {
        super(Integer.toString(device.getSerialNumber()), Integer.toString(device.getNetworkAdress()));
        this.device = device;
    }

    private void connection() throws IOException {
        socket.connect(new InetSocketAddress(device.getIp(), device.getPort()));
        socket.setSoTimeout(20000);
        in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }

    private byte[] query(byte[] query) throws IOException {
        out.write(query);
        out.flush();
        byte[] buffer = new byte[25];
        in.read(buffer);
        return buffer;
    }

    public DataEntity getValues() {
        try {
            connection();
            query(super.getAuthorization());

            byte[] buffer = query(super.getEnergyResetAndPowerSum());
            Thread.sleep(500);

            ArrayList<String> valuesHexEnergy = Converted.getSliceValuesHex(1, 5, buffer);
            ArrayList<String> valuesHexPower = Converted.getSliceValuesHex(9, 13, buffer);

            double energy = Long.parseLong(valuesHexEnergy.get(1) + valuesHexEnergy.get(0) + valuesHexEnergy.get(3) + valuesHexEnergy.get(2), 16);
            energy = energy / 1000;
            this.valuesEnergy = Double.toString(energy);

            double power = Long.parseLong(valuesHexPower.get(1) + valuesHexPower.get(0) + valuesHexPower.get(3) + valuesHexPower.get(2), 16);
            power = power / 1000;
            this.valuesPower = Double.toString(power);
            socket.close();

            DataEntity data = new DataEntity();
            data.setEnergyResetSum(this.valuesEnergy);
            data.setPowerSum(this.valuesPower);
            data.setEnergyDayStart("0");
            data.setEnergyDay("0");
            data.setPowerDay("0");
            data.setError("-");
            data.setDate(new java.sql.Date(new java.util.Date().getTime()));
            data.setDevice_id(device);
            return data;

        } catch (IOException | InterruptedException e) {
            DataEntity data = new DataEntity();
            data.setEnergyResetSum("0");
            data.setPowerSum("0");
            data.setEnergyDayStart("0");
            data.setEnergyDay("0");
            data.setPowerDay("0");
            data.setError(e.getMessage());
            data.setDate(new java.sql.Date(new java.util.Date().getTime()));
            data.setDevice_id(device);
            return data;
        }
    }

    public String getValuesEnergy() {
        return valuesEnergy;
    }

    public String getValuesPower() {
        return valuesPower;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
