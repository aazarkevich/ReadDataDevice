package project.mercury;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

public class Converted {

    public static ArrayList<String> getSliceValuesHex(int start, int end, byte[] buffer) {
        ArrayList<String> values = new ArrayList<String>();
        for (int i = start; i < end; i++) {
            String valuesHex = Integer.toHexString(buffer[i]);
            if (buffer[i] < 0) {
                valuesHex = valuesHex.substring(6, valuesHex.length());
            }
            if (buffer[i] > 0 && buffer[i] < 15) {
                valuesHex = '0' + valuesHex;
            }
            values.add(valuesHex);
        }
        return values;
    }

    public static byte[] decimalToHexNetwork(String networkAddress) {
        if (Integer.parseInt(networkAddress) <= 15) {
            networkAddress = "0" + networkAddress;
        }
        networkAddress = "0x" + Integer.toHexString(Integer.parseInt(networkAddress));
        int hexNetworkAddress = Integer.decode(networkAddress);
        return new byte[]{(byte) hexNetworkAddress};
    }
}
