/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.*;
import java.util.*;
/**
 *
 * @author rejoice
 */
// EmployeeCSVReader class
class EmployeeCSVReader {
    private static final String EMPLOYEE_CSV_FILE = "src/CSVFiles/employees.csv";

    public static List<Employee> loadEmployees() {
        List<Employee> employees = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(EMPLOYEE_CSV_FILE))) {
            System.out.println("Loading employees from: " + new File(EMPLOYEE_CSV_FILE).getAbsolutePath());
            br.readLine(); // Skip header

            String line;
            while ((line = br.readLine()) != null) {
                // Use regex-based splitting to handle fields with commas inside quotes
                String[] data = parseCSVLine(line);
                
                if (data.length < 18) {  // Ensure valid number of columns
                    System.err.println("Skipping malformed row: " + line);
                    continue;
                }
                
                int employeeId = Integer.parseInt(data[0].trim());
                String lastName = data[1].trim();
                String firstName = data[2].trim();
                String position = data[11].trim();
                double basicSalary = parseDoubleOrDefault(data[13], 0.0);
                double riceSubsidy = parseDoubleOrDefault(data[14], 0.0);
                double phoneAllowance = parseDoubleOrDefault(data[15], 0.0);
                double clothingAllowance = parseDoubleOrDefault(data[16], 0.0);
                double hourlyRate = parseDoubleOrDefault(data[18], 0.0);
                
                CompensationDetails compensationDetails = new CompensationDetails(basicSalary, riceSubsidy, phoneAllowance, clothingAllowance, hourlyRate);
                GovernmentContributions governmentContributions = new GovernmentContributions(employeeId, compensationDetails);
                EmploymentStatus employmentStatus = EmploymentStatus.getEmploymentStatusByEmployeeId(employeeId);

                employees.add(new Employee(employeeId, firstName, lastName, position, employmentStatus, compensationDetails, governmentContributions));
            }
        } catch (IOException e) {
            System.err.println("Error reading employee file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing numeric value: " + e.getMessage());
        }
        return employees;
    }
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

    private static String[] parseCSVLine(String line) {
        List<String> values = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();

        for (char ch : line.toCharArray()) {
            if (ch == '"' && (sb.length() == 0 || sb.charAt(sb.length() - 1) != '\\')) {
                inQuotes = !inQuotes;
            } else if (ch == ',' && !inQuotes) {
                values.add(sb.toString().trim());
                sb.setLength(0);
            } else {
                sb.append(ch);
            }
        }
        values.add(sb.toString().trim());

        return values.toArray(new String[0]);
    }
}
