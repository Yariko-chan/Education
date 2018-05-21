package udp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Ganeeva Diana in Май, 2018
 * for TcpUdpServers
 */
public class Utils {
    public static final String IP_ADRESS = "127.0.0.1";
    public static final int DEFAULT_PORT = 8001;
    public static final String SIGMA_COMMAND = "sigma";
    public static final String EXIT = "QUIT";
    public static final byte[] UNKNOWN_CMD = {'U', 'n', 'k', 'n', 'o', 'w', 'n', ' ', 'c', 'o', 'm', 'm', 'a', 'n', 'd'};

    public static void writeToFile(String filename, String data) throws IOException {
        File src = new File(filename);
        src.createNewFile();
        FileOutputStream outputStream = null;
        BufferedOutputStream out = null;
        try {
            outputStream = new FileOutputStream(src);
            out = new BufferedOutputStream(outputStream);
            out.write(data.getBytes());
        } finally {
            if (out != null) {
                out.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    public static float getSigma(float x, float y, float z) {
        float result = 0;
        result += Math.pow(y, x);
        result += Math.sqrt(Math.abs(x) + Math.pow(Math.E, y));
        float z3 = (float) Math.pow(z, 3);
        float num = (float) (z3 * Math.pow(Math.sin(y), 2));
        float denom = y + z3 / (y - z3);
        result -= num / denom;
        return result;
    }
}
