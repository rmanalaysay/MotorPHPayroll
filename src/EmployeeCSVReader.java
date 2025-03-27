
import java.io.*;
import java.util.*;

class EmployeeCSVReader {
    private static final String EMPLOYEE_CSV_FILE = "src/CSVFiles/employees.csv"; // Path to the employee CSV file
    private static List<Employee> employees; // List to store employee records

    // Load employees only when needed
    private static void ensureEmployeesLoaded() {
        if (employees == null || employees.isEmpty()) {
            employees = loadEmployees();
        }
    }

    // A list of Employee objects populated from the CSV file
    public static List<Employee> loadEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(EMPLOYEE_CSV_FILE))) {
            System.out.println("Loading employees from: " + new File(EMPLOYEE_CSV_FILE).getAbsolutePath());
            br.readLine(); // Skip header

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = parseCSVLine(line); // Parse the line properly handling quoted values

                // Ensure the row has the expected number of fields before processing
                if (data.length < 18) { 
                    System.err.println("Skipping malformed row: " + line);
                    continue;
                }

                // Extract and parse employee data fields
                int employeeId = Integer.parseInt(data[0].trim());
                String lastName = data[1].trim();
                String firstName = data[2].trim();
                String position = data[11].trim();
                double basicSalary = parseDoubleOrDefault(data[13], 0.0);
                double riceSubsidy = parseDoubleOrDefault(data[14], 0.0);
                double phoneAllowance = parseDoubleOrDefault(data[15], 0.0);
                double clothingAllowance = parseDoubleOrDefault(data[16], 0.0);
                double hourlyRate = parseDoubleOrDefault(data[18], 0.0);

                // Create CompensationDetails and GovernmentContributions objects
                CompensationDetails compensationDetails = new CompensationDetails(basicSalary, riceSubsidy, phoneAllowance, clothingAllowance, hourlyRate);
                GovernmentContributions governmentContributions = new GovernmentContributions(employeeId, compensationDetails);
                
                // Retrieve employment status based on employee ID
                EmploymentStatus employmentStatus = EmploymentStatus.getEmploymentStatusByEmployeeId(employeeId);

                // Create and add the Employee object to the list
                employeeList.add(new Employee(employeeId, firstName, lastName, position, employmentStatus, compensationDetails, governmentContributions));
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading employee file: " + e.getMessage());
        }
        return employeeList;
    }

    // Ensure employees are loaded before searching
    public static Employee getEmployeeById(int employeeId) {
        ensureEmployeesLoaded();  // Load employees if not already loaded
        return employees.stream()
                .filter(emp -> emp.getEmployeeId() == employeeId)
                .findFirst()
                .orElse(null);
    }

    // Parses a string to a double, returning a default value if parsing fails
    private static double parseDoubleOrDefault(String value, double defaultValue) {
        if (value == null || value.trim().isEmpty() || value.equalsIgnoreCase("NA")) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format: " + value);
            return defaultValue;
        }
    }

    // Parses a CSV line, correctly handling values enclosed in double quotes
    private static String[] parseCSVLine(String line) {
        List<String> values = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();

        // Process each character in the line
        for (char ch : line.toCharArray()) {
            if (ch == '"' && (sb.length() == 0 || sb.charAt(sb.length() - 1) != '\\')) {
                inQuotes = !inQuotes;
            } else if (ch == ',' && !inQuotes) {
                values.add(sb.toString().trim()); // Add field to list
                sb.setLength(0); // Reset buffer for the next field
            } else {
                sb.append(ch); // Append character to current field
            }
        }
        values.add(sb.toString().trim()); // Add the last field

        return values.toArray(new String[0]);
    }
}
