package project.mercury.modbus;

public class CRC16 {

	private static String getCRC2(byte[] bytes) {

		int CRC = 0x0000ffff;
		int POLYNOMIAL = 0x0000a001;

		int i, j;
		for (i = 0; i < bytes.length; i++) {
			CRC ^= (int) bytes[i];
			for (j = 0; j < 8; j++) {
				if ((CRC & 0x00000001) == 1) {
					CRC >>= 1;
					CRC ^= POLYNOMIAL;
				} else {
					CRC >>= 1;
				}
			}
		}

		return Integer.toHexString(CRC);
	}

	private static byte[] stringToHex(String bytes) {
		String hex1 = "0x"+bytes.substring(0,2);
		String hex2 = "0x"+bytes.substring(2,4);
		int intHex1 = Integer.decode(hex1);
		int intHex2 = Integer.decode(hex2);
		byte[] rezultByte = new byte[]{(byte)intHex2, (byte)intHex1 };

		return rezultByte;
	}

	public static byte[] getCRC(byte[] bytes) {
		String crcString = getCRC2(bytes);
		if (crcString.length() < 4) {
			crcString = "0" + crcString;
		}
		byte[] crc = stringToHex(crcString);

		return crc;
	}

}
