
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

// AttendanceCSVReader Class
public class AttendanceCSVReader {
    private static final String FILE_PATH = "src/CSVFiles/attendance.csv";
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    //Reads attendance records from the CSV file
    public static List<Attendance> readAttendance() {
        List<Attendance> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            br.readLine(); // Skip the header line
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                
                // Ensure that the record has the required number of fields
                if (values.length < 6) continue;
                
                // Parse and add attendance record to the list
                records.add(new Attendance(
                        Integer.parseInt(values[0]), // Employee ID
                        values[1],  // Employee Name
                        values[2],  // Position
                        LocalDate.parse(values[3], DATE_FORMAT), // Date
                        values[4],  // Login Time
                        values[5]   // Logout Time
                ));
            }
        } catch (IOException e) {
            System.err.println("Error reading attendance file: " + e.getMessage());
        }
        return records;
    }

    //Checks if an employee exists in the attendance records.
    public static boolean employeeExists(int employeeId) {
        return readAttendance().stream().anyMatch(a -> a.getEmployeeId() == employeeId);
    }
    

    // Updates or adds an attendance entry in the CSV file.
    public static void updateAttendance(Attendance attendance) {
        List<String> lines = new ArrayList<>();
        boolean entryUpdated = false;
        File file = new File(FILE_PATH);

        // Read the existing attendance file and update if a matching entry is found
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                // Check if the entry matches the employee ID and date
                if (values.length >= 4 && values[0].equals(String.valueOf(attendance.getEmployeeId())) &&
                        values[3].equals(attendance.getDate().format(DATE_FORMAT))) {

                    // Replace the existing entry with the updated data
                    line = attendance.formatForCSV(); //Use the new method
                    entryUpdated = true;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading attendance file: " + e.getMessage());
        }

        // If no matching entry was found, add the new attendance record
        if (!entryUpdated) {
            lines.add(attendance.formatForCSV());
        }

        // Write the updated data back to the CSV file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (String updatedLine : lines) {
                bw.write(updatedLine);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to attendance file: " + e.getMessage());
        }
    }
}