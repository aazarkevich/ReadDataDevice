package project.mercury;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;
import project.mercury.modbus.CRC16;

import java.util.Arrays;
import java.util.HashMap;

public abstract class Mercury {
    private HashMap<String, byte[]> COMMAND_MERCURY = new HashMap<String, byte[]>() {{
        put("authorization", new byte[] {0x01,0x01,0x01,0x01,0x01,0x01,0x01,0x01});
        put("energyResetAndPowerSum", new byte[]{0x05,0x00,0x00});
        put("energyDayStart", new byte[]{0x05, 0x40, 0x00});

    }};

    private byte[] authorization;
    private byte[] energyResetAndPowerSum;
    private byte[] energyDayStart;
    private String serialNumber;
    private String networkAddress;
    private byte[] hexNetworkAddress;
    private Converted converted = new Converted();

    public byte[] getHexNetworkAddress() {
        return hexNetworkAddress;
    }

    public Mercury() {

    }

    public Mercury(String serialNumber, String networkAddress) {
        this.serialNumber = serialNumber;
        this.networkAddress = serialNumberToNetworkAddress(serialNumber);
        this.hexNetworkAddress = converted.decimalToHexNetwork(networkAddress);
        authorization = queryAuthorization();
        energyResetAndPowerSum = queryEnergyResetAndPowerSum();
        energyDayStart = queryEnergyDayStart();

    }

    public Mercury(String serialNumber) {
        this.serialNumber = serialNumber;
        this.networkAddress = serialNumberToNetworkAddress(serialNumber);
        this.hexNetworkAddress = converted.decimalToHexNetwork(this.networkAddress);
        authorization = queryAuthorization();
        energyResetAndPowerSum = queryEnergyResetAndPowerSum();
        energyDayStart = queryEnergyDayStart();
    }

    public byte[] getAuthorization() {
        return authorization;
    }

    public byte[] getEnergyResetAndPowerSum() {
        return energyResetAndPowerSum;
    }

    public byte[] getEnergyDayStart() {
        return energyDayStart;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getNetworkAddress() {
        return networkAddress;
    }


    private String serialNumberToNetworkAddress(String serialNumber) {
        String networkAddress;
        if (serialNumber.length() <= 3) {
            return serialNumber;
        }
        if (Integer.parseInt(serialNumber.substring(serialNumber.length() - 3)) >= 240) {
            networkAddress = serialNumber.substring(serialNumber.length() - 2);
        } else {
            networkAddress = serialNumber.substring(serialNumber.length() - 3);
        }
        return networkAddress;
    }

    private byte[] crc(byte[] bytes) {
        return CRC16.getCRC(bytes);
    }

    private byte[] queryAuthorization() {
        byte[] crc16 = crc(ArrayUtils.addAll(hexNetworkAddress,COMMAND_MERCURY.get("authorization")));
        byte[] query = ArrayUtils.addAll(ArrayUtils.addAll(hexNetworkAddress, COMMAND_MERCURY.get("authorization")), crc16);
        return query;
    }

    public byte[] queryEnergyResetAndPowerSum() {
        byte[] crc16 = crc(ArrayUtils.addAll(hexNetworkAddress,COMMAND_MERCURY.get("energyResetAndPowerSum")));
        byte[] query = ArrayUtils.addAll(ArrayUtils.addAll(hexNetworkAddress, COMMAND_MERCURY.get("energyResetAndPowerSum")), crc16);
        return query;
    }

    private byte[] queryEnergyDayStart() {
        byte[] crc16 = crc(ArrayUtils.addAll(hexNetworkAddress,COMMAND_MERCURY.get("energyDayStart")));
        byte[] query = ArrayUtils.addAll(ArrayUtils.addAll(hexNetworkAddress, COMMAND_MERCURY.get("energyDayStart")), crc16);
        return query;
    }

    @Override
    public String toString() {
        return "Mercury{" +
                "serialNumber='" + serialNumber + '\'' +
                ", networkAddress='" + networkAddress + '\'' +
                ", hexNetworkAddress=" + Arrays.toString(hexNetworkAddress) +
                '}';
    }
}
