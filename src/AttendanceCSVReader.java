import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

// AttendanceCSVReader Class
public class AttendanceCSVReader {
    private static final String FILE_PATH = "src/CSVFiles/attendance.csv";
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public static List<Attendance> readAttendance() {
        List<Attendance> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length < 6) continue;
                records.add(new Attendance(
                        Integer.parseInt(values[0]),
                        values[1], values[2],
                        LocalDate.parse(values[3], DATE_FORMAT),
                        values[4], values[5]
                ));
            }
        } catch (IOException e) {
            System.err.println("Error reading attendance file: " + e.getMessage());
        }
        return records;
    }

    public static boolean employeeExists(int employeeId) {
        return readAttendance().stream().anyMatch(a -> a.getEmployeeId() == employeeId);
    }
    

    // Updated to accept an Attendance object
    public static void updateAttendance(Attendance attendance) {
        List<String> lines = new ArrayList<>();
        boolean entryUpdated = false;
        File file = new File(FILE_PATH);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length >= 4 && values[0].equals(String.valueOf(attendance.getEmployeeId())) &&
                        values[3].equals(attendance.getDate().format(DATE_FORMAT))) {

                    line = attendance.formatForCSV(); //Use the new method
                    entryUpdated = true;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading attendance file: " + e.getMessage());
        }

        if (!entryUpdated) {
            lines.add(attendance.formatForCSV());
        }

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