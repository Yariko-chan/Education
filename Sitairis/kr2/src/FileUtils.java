import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {



    /**
     * read data from file to String
     * @param filename - filename to read
     * @return
     * @throws FileNotFoundException if file doesnt exist
     * @throws IOException other file reading exceptions
     */
    public static String getFileData(String filename) throws FileNotFoundException, IOException {
        File src = new File(filename);
        if (!src.exists()) {
            throw new FileNotFoundException("File doesnt exist!");
        }
        StringBuilder builder = new StringBuilder();
        FileInputStream inputStream = null;
        BufferedInputStream in = null;
        try {
            inputStream = new FileInputStream(src);
            in = new BufferedInputStream(inputStream);
            int numByte = in.available();
            byte[] buf = new byte[numByte];
            while (in.read(buf) != -1) {
                // for each byte in buf
                for (byte b : buf) {
                    builder.append((char)b);
                }
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return builder.toString();
    }

    /**
     * Write data to file
     * @param filename - name of file to write, might be created if need
     * @param data - Data for writing to file
     * @throws IOException
     */
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
}
