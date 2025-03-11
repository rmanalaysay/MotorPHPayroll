import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

// AttendanceCSVReader Class
class AttendanceCSVReader {
    private static final String FILE_PATH = "src/CSVFiles/attendance.csv";
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy"); // FIXED

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
                        LocalDate.parse(values[3], DATE_FORMAT), // FIXED
                        values[4],
                        values[5]
                ));
            }
        } catch (IOException e) {
            System.err.println("Error reading attendance file: " + e.getMessage());
        }
        return records;
    }

    public static void updateAttendance(int employeeId, boolean isLogin) {
        List<Attendance> records = readAttendance();
        LocalDate today = LocalDate.now();
        String currentTime = LocalTime.now().format(TIME_FORMAT);
        boolean updated = false;

        for (Attendance record : records) {
            if (record.getEmployeeId() == employeeId && record.getDate().equals(today)) {
                records.remove(record);
                records.add(new Attendance(employeeId, today, 
                    isLogin ? currentTime : record.getLogin(),
                    isLogin ? record.getLogout() : currentTime
                ));
                updated = true;
                break;
            }
        }

        if (!updated) {
            records.add(new Attendance(employeeId, today, isLogin ? currentTime : "", isLogin ? "" : currentTime));
        }
        writeAttendance(records);
    }

    private static void writeAttendance(List<Attendance> records) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            bw.write("Employee ID,Last Name,First Name,Date,Login,Logout\n");
            for (Attendance record : records) {
                bw.write(record.getEmployeeId() + ",,," + record.getDate() + "," + record.getLogin() + "," + record.getLogout() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing attendance file: " + e.getMessage());
        }
    }
}
