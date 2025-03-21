import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

class LeavesCSVReader {
    private static final String LEAVE_FILE = Paths.get("src/CSVFiles/leaverequest.csv").toAbsolutePath().toString();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Load leave requests from CSV
    public static List<LeaveRequest> loadLeaveRequests(Map<Integer, Employee> employees) {
        List<LeaveRequest> leaveRequests = new ArrayList<>();

        if (!Files.exists(Paths.get(LEAVE_FILE))) {
            System.out.println("Leave request file not found. Returning empty list.");
            return leaveRequests;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(LEAVE_FILE))) {
            String line;
            boolean isFirstLine = true; // To skip header if present

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Assume first line is a header and skip it
                    continue;
                }

                String[] data = line.split(",");
                if (data.length < 6) {
                    System.err.println("Invalid CSV format, skipping line: " + line);
                    continue;
                }

                try {
                    int employeeId = Integer.parseInt(data[0].trim());
                    String lastName = data[1].trim();
                    String firstName = data[2].trim();
                    String leaveType = data[3].trim();
                    LocalDate startDate = LocalDate.parse(data[4].trim(), FORMATTER);
                    LocalDate endDate = LocalDate.parse(data[5].trim(), FORMATTER);

                    Employee employee = employees.get(employeeId);
                    if (employee == null) {
                        System.err.println("Warning: Employee ID " + employeeId + " not found. Skipping entry.");
                        continue;
                    }

                    leaveRequests.add(new LeaveRequest(employee, leaveType, startDate, endDate));

                } catch (Exception e) {
                    System.err.println("Error parsing line, skipping: " + line);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading leave requests file: " + e.getMessage());
            e.printStackTrace();
        }

        return leaveRequests;
    }

    // Write leave request to CSV
    public static void writeLeaveToCSV(LeaveRequest leaveRequest) {
        File file = new File(LEAVE_FILE);
        boolean fileExists = file.exists();

        try {
            // Create file if it doesn't exist
            if (!fileExists) {
                file.getParentFile().mkdirs(); // Ensure directory exists
                file.createNewFile();
                System.out.println("Leave request file created: " + LEAVE_FILE);

                // Write header with a newline to avoid data appending issues
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write("EmployeeID,LastName,FirstName,LeaveType,StartDate,EndDate");
                    writer.newLine(); // Ensure a new line after the header
                    writer.flush();
                }
            }

            // Append the new leave request
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                // Move to a new line before writing new data
                writer.newLine();  
                
                String newEntry = leaveRequest.getEmployee().getEmployeeId() + "," +
                                  leaveRequest.getEmployee().getLastName() + "," +
                                  leaveRequest.getEmployee().getFirstName() + "," +
                                  leaveRequest.getLeaveType() + "," +
                                  leaveRequest.getStartDate().format(FORMATTER) + "," +
                                  leaveRequest.getEndDate().format(FORMATTER);

                writer.write(newEntry);
                writer.flush(); // Ensure data is written
                
                System.out.println("Leave request successfully written: " + newEntry);
            }

        } catch (IOException e) {
            System.err.println("Error writing leave request to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
