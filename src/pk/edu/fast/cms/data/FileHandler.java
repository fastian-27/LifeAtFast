package pk.edu.fast.cms.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for performing raw read/write operations on text files.
 */
public class FileHandler {

    /**
     * Reads all lines from a given text file.
     *
     * @param filePath The path to the file.
     * @return A list of strings, each representing a line in the file.
     * @throws RuntimeException If the file cannot be read.
     */
    public static List<String> readLines(String filePath) {
        List<String> lines = new ArrayList<>();
        File file = new File(filePath);

        // If the file doesn't exist yet, return an empty list instead of crashing
        if (!file.exists()) {
            return lines;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Critical I/O Error: Failed to read from " + filePath);
        }
        return lines;
    }

    /**
     * Appends a single line of string data to the specified file.
     *
     * @param filePath The path to the file.
     * @param data     The string to append.
     * @throws RuntimeException If the file cannot be written to.
     */
    public static void appendLine(String filePath, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Critical I/O Error: Failed to write to " + filePath);
        }
    }
}