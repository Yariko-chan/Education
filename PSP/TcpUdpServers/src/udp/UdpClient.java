package udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Ganeeva Diana in Май, 2018
 * for TcpUdpServers
 */


public class UdpClient {

    public void runClient() throws IOException {
        DatagramSocket socket = null;
        try {
            // initializations
            byte[] buf = new byte[512]; 
            socket = new DatagramSocket();
            String answer = "";
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("UdpClient started");

            DatagramPacket sendPacket;
            do {
                // read data
                System.out.println("Calculating sigma");
                System.out.print("Enter x: ");
                float x = Float.valueOf(stdin.readLine());
                System.out.print("Enter y: ");
                float y = Float.valueOf(stdin.readLine());
                System.out.print("Enter z: ");
                float z = Float.valueOf(stdin.readLine());
                String data = new StringBuilder().append(Utils.SIGMA_COMMAND).append(" ")
                        .append(x).append(", ")
                        .append(y).append(", ")
                        .append(z).toString();
                System.out.printf("Source data %s\n", data);

                // send to server
                sendPacket = new DatagramPacket(data.getBytes(), data.getBytes().length,
                        InetAddress.getByName(Utils.IP_ADRESS),  Utils.DEFAULT_PORT);
                socket.send(sendPacket);

                // receive result
                DatagramPacket recvPacket = new DatagramPacket(buf, buf.length);
                socket.receive(recvPacket);
                System.out.println("Received result: " + new String(recvPacket.getData()).trim());

                // repeat or exit
                System.out.println("Calculate another one? (y/n)");
                answer = stdin.readLine();
            } while (answer.startsWith("y"));

            // exit
            byte[] quitCmd = {'Q', 'U', 'I', 'T'};
            sendPacket.setData(quitCmd);
            sendPacket.setLength(quitCmd.length);
            socket.send(sendPacket);
            System.out.println("UdpClient close");
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
    public static void main(String[] args) {
        try {
            UdpClient client = new UdpClient();
            client.runClient();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
