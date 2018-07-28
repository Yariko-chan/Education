import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Используя варианты заданий, представленные ниже, разработать программу,
 * которая позволяла бы работать с тремя файлами. Исходная информация должна
 * хранить в файле file1.txt(программа ее считывает и осуществляет с ней
 * преобразования), затем преобразованная информация записывается в файл
 * file2.txt, а в файл file3.txt программа должна записать исходную и
 * преобразованную  информацию.
 *
 * 6.  Создать объект типа String и проинициализировать его текстовой строкой.
 * Определить количество гласных, пробелов и общее количество букв.
 *
 * Created by Ganeeva Diana
 */
public class StringAnalyser {

    /**
     * data structure for holding results of string analysis
     */
    private class AnalysisResult {
        private int vowelsCount;
        private int spacesCount;
        private int lettersCount;

        public AnalysisResult(int vowelsCount, int spacesCount, int lettersCount) {
            this.vowelsCount = vowelsCount;
            this.spacesCount = spacesCount;
            this.lettersCount = lettersCount;
        }

        public int getVowelsCount() {
            return vowelsCount;
        }

        public int getSpacesCount() {
            return spacesCount;
        }

        public int getLettersCount() {
            return lettersCount;
        }

        @Override
        public String toString() {
            return "Analysis result: " +
                    "vowelsCount = " + vowelsCount +
                    " , spacesCount= " + spacesCount +
                    " , lettersCount= " + lettersCount;
        }
    }

    /**
     * Analyzes string for count of vowels, spaces, letters
     * @param s string to analyze
     * @return AnalysisResult contains count of vowels, spaces, letters
     */
    public AnalysisResult analyze(String s) {
        int vowelsCount = 0;
        int spacesCount = 0;
        int lettersCount = 0;

        String englishVowels = "aeiou";
        String russianVowels = "аеёиоуыэюя";

        for (int i = 0; i < s.length(); i++) {
            Character ch = s.charAt(i);
            if (Character.isSpaceChar(ch)) {
                spacesCount++;
            } else if (Character.isLetter(ch)) {
                lettersCount++;
                if (englishVowels.indexOf(ch) >= 0 || russianVowels.indexOf(ch) >= 0) {
                    vowelsCount++;
                }
            }
        }

        return new AnalysisResult(vowelsCount, spacesCount, lettersCount);
    }

    /**
     * read data from file to String
     * @param filename - filename to read
     * @return
     * @throws FileNotFoundException if file doesnt exist
     * @throws IOException other file reading exceptions
     */
    public String getFileData(String filename) throws FileNotFoundException, IOException {
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
    public void writeToFile(String filename, String data) throws IOException {
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

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No arguments. Provide src filename!");
            return;
        }
        StringAnalyser analyser = new StringAnalyser();
        try {
            String src = analyser.getFileData(args[0]);
            AnalysisResult result = analyser.analyze(src);

            analyser.writeToFile("file2.txt", result.toString());
            analyser.writeToFile("file3.txt", src + "\n\n" + result.toString());

            System.out.println(src);
            System.out.println(result);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}
