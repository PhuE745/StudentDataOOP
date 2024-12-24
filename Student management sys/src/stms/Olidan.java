package stms;

import java.io.*;
import java.util.Scanner;

public class Olidan {
    private String csvFile;

    // Constructor to initialize the csvFile
    public Olidan(String csvFile) {
        this.csvFile = csvFile;
    }

    public void removeRecord(String nameToRemove) {
        File inputFile = new File(csvFile);
        File tempFile = new File("temp.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;
            boolean headerWritten = false;

            while ((currentLine = reader.readLine()) != null) {
                // Write the header to the temp file if it's the first line
                if (!headerWritten) {
                    writer.write(currentLine);
                    writer.newLine();
                    headerWritten = true;
                    continue;
                }

                // Split the line into fields
                String[] fields = currentLine.split(",");

                // Check if the name matches the one to remove
                if (!fields[0].equalsIgnoreCase(nameToRemove)) {
                    // If it doesn't match, write the line to the temp file
                    writer.write(currentLine);
                    writer.newLine();
                }
            }

            // Rename the temp file to the original file
            if (!inputFile.delete()) {
                System.out.println("Could not delete the original file");
                return;
            }
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Could not rename the temp file");
            }

            System.out.println("Record removed successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Instance method to remove a student by name
    public void remove(Scanner scanner) {
        System.out.println("Enter the name of the student to remove:");
        String nameToRemove = scanner.nextLine();
        removeRecord(nameToRemove);
    }
}