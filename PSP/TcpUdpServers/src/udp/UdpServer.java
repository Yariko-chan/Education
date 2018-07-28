package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Разработать приложение на основе UDP-соединения, позволяющее осуществлять
 * взаимодействие клиента и сервера по совместному решению задач обработки
 * информации. Приложение должно располагать возможностью передачи и
 * модифицирования получаемых (передаваемых) данных. Возможности клиента:
 * передать серверу исходные параметры (вводятся с клавиатуры) для расчета
 * значения функции, а также получить расчетное значение функции. Возможности
 * сервера: по полученным от клиента исходным параметрам рассчитать значение
 * функции, передать клиенту расчетное значение функции, а также сохранить
 * исходные параметры и значение функции в файл.
 *
 * Created by Ganeeva Diana in Май, 2018
 * for TcpUdpServers
 */

public class UdpServer {

    public void runServer() throws IOException {
        DatagramSocket socket = null;
        try {
            // initializations
            boolean isStop = false;
            socket = new DatagramSocket(Utils.DEFAULT_PORT);
            System.out.printf("UdpServer started on %s:%s \n", socket.getLocalAddress(), socket.getLocalPort());
            while (!isStop) {
                // initializations
                byte[] buffer = new byte[512];
                int length = 0;
                // receive command
                DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(receivedPacket);
                String cmd = new String(receivedPacket.getData()).trim();
                System.out.printf("Received: %s \n", cmd);
                if (cmd.equals(Utils.EXIT)) { // exit command
                    isStop = true;
                    continue;
                } else if (cmd.startsWith(Utils.SIGMA_COMMAND)) { // command to calculate sigma

                    // parse data
                    int dividerIndex = cmd.indexOf(",");
                    float x = Float.valueOf(cmd.substring(cmd.indexOf(" "), dividerIndex));
                    float y = Float.valueOf(cmd.substring(dividerIndex + 1, (dividerIndex = cmd.indexOf(",", dividerIndex + 1))));
                    float z = Float.valueOf(cmd.substring(dividerIndex + 2));
                    String result = String.valueOf(Utils.getSigma(x, y, z));
                    System.out.println("Result: " + result);

                    // data for answer
                    length = result.getBytes().length;
                    System.arraycopy(result.getBytes(), 0, buffer, 0, length);

                    // save to file
                    String data = new StringBuilder()
                            .append("Data: x = ").append(x)
                            .append(", y = "). append(y)
                            .append(", z = ").append(z).append("; ")
                            .append("Result = ").append(result)
                            .toString();
                    String filename = Utils.SIGMA_COMMAND + " " +
                            new SimpleDateFormat("dd.MM.yyyy_hh-mm-ss-SS").format(new Date());
                    Utils.writeToFile(filename, data);
                } else { // unknown command
                    length = Utils.UNKNOWN_CMD.length;
                    System.arraycopy(Utils.UNKNOWN_CMD, 0, buffer, 0, length);
                }

                // send answer
                DatagramPacket sendPacket = new DatagramPacket(buffer, 0, receivedPacket.getAddress(), receivedPacket.getPort());
                sendPacket.setData(buffer);
                sendPacket.setLength(length);
                socket.send(sendPacket);
            }
            System.out.println("UDPServer: Stopped");
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

    public static void main(String[] args) {
        try {
            UdpServer udpSvr = new UdpServer();
            udpSvr.runServer();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
