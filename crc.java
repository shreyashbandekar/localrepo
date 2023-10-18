mport java.util.Scanner;

public class crc {
    private static final int polynomial = 0x1021; // CRC-CCITT polynomial

    public static String CRCCCITT(String data) {
        int crc = 0xFFFF; // Initial CRC value
        for (char c : data.toCharArray()) {
            int ascii = (int) c;
            crc ^= (ascii << 8) & 0xFFFF;
            for (int i = 0; i < 8; i++) {
                if ((crc & 0x8000) != 0)
                    crc = (crc << 1) ^ polynomial;
                else
                    crc <<= 1;
                crc &= 0xFFFF; // Ensure it's a 16-bit value
            }
        }
        return Integer.toString(crc);
    }

    public static void main(String[] args) {
        Scanner sc = new java.util.Scanner(System.in);

        // Sender side
        System.out.print("Enter the data for CRC calculation: ");
        String inputData = sc.nextLine().trim();
        String crc = CRCCCITT(inputData);
        String dataWithCRC = inputData + crc;
        System.out.println("Transmitting data with CRC: " + dataWithCRC);

        // Receiver side
        System.out.print("Enter the received data (message + CRC): ");
        String receivedData = sc.nextLine().trim();
        String receivedMessage = receivedData.substring(0, receivedData.length() - 5);
        String receivedCRC = receivedData.substring(receivedData.length() - 5);
        String resCRC = CRCCCITT(receivedMessage);

        if (resCRC.equals(receivedCRC))
            System.out.println("CRC Check: Data is intact. Received message: " + receivedMessage);
        else
            System.out.println("CRC Check: Data is corrupted. Discarding the message.");
        sc.close();
    }
}
