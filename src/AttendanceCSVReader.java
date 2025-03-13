import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

// AttendanceCSVReader Class
class AttendanceCSVReader {
    private static final String FILE_PATH = "/Users/rejoice/NetBeansProjects/MotorPHPayroll_Test/src/CSVFiles/attendance.csv";
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public static List<Attendance> readAttendance() {
        List<Attendance> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            System.out.println("Loading attendance from: " + new File(FILE_PATH).getAbsolutePath());
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length < 6) continue;
                records.add(new Attendance(
                        Integer.parseInt(values[0]),
                        values[1],  // Last Name
                        values[2],  // First Name
                        LocalDate.parse(values[3], DATE_FORMAT),
                        values[4],  // Log In Time
                        values[5]   // Log Out Time
                ));
            }
        } catch (IOException e) {
            System.err.println("Error reading attendance file: " + e.getMessage());
        }
        return records;
    }

    public static void updateAttendance(int employeeId, String lastName, String firstName, boolean isLogin) {
        String filePath = "src/CSVFiles/attendance.csv"; // Relative file path
        LocalDate today = LocalDate.now();
        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        List<String> lines = new ArrayList<>();
        boolean entryUpdated = false;

        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("Error: Attendance file not found at " + file.getAbsolutePath());
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                // Check if the line belongs to the current employee and today's date
                if (values.length >= 4 && values[0].equals(String.valueOf(employeeId)) &&
                    values[3].equals(today.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")))) {

                    // Ensure the array has enough fields
                    while (values.length < 6) {
                        values = Arrays.copyOf(values, 6); // Expand array to ensure log-in and log-out fields exist
                    }

                    if (isLogin) {
                        values[4] = currentTime; // Log-in time
                    } else {
                        values[5] = currentTime; // Log-out time
                    }

                    entryUpdated = true;
                    line = String.join(",", values);
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading the attendance file: " + e.getMessage());
        }

        // If no existing log-in record was found, add a new log-in row
        if (!entryUpdated && isLogin) {
            lines.add(employeeId + "," + lastName + "," + firstName + "," + 
                      today.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + "," + currentTime + ",");
        }

        // Rewrite the file with updated attendance data
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false))) { // Overwrites the file
            for (String updatedLine : lines) {
                bw.write(updatedLine);
                bw.newLine();
            }
            System.out.println(isLogin ? "Log-in recorded successfully." : "Log-out recorded successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to attendance file: " + e.getMessage());
        }
    }

    private static void writeAttendance(List<Attendance> records) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, false))) { // Overwrite the file
            bw.write("Employee ID,Last Name,First Name,Date,Login,Logout\n"); // Header
            for (Attendance record : records) {
                bw.write(record.getEmployeeId() + "," + record.getLastName() + "," + record.getFirstName() + "," +
                         record.getDate().format(DATE_FORMAT) + "," + 
                         (record.getLogin().isEmpty() ? "" : record.getLogin()) + "," + 
                         (record.getLogout().isEmpty() ? "" : record.getLogout()) + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing attendance file: " + e.getMessage());
        }
    }
}