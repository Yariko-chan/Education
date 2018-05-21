package main.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Ganeeva Diana in Май, 2018
 * for DB
 */
public class FileUtils {

    private FileUtils() {
    }



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
