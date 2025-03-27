
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;

public class PayslipGenerator {
    private final Payroll payroll;
    private final Employee employee;

    //Constructor to initialize the PayslipGenerator
    public PayslipGenerator(Employee employee, List<Attendance> attendanceRecords) {
        this.payroll = new Payroll(employee, attendanceRecords);
        this.employee = employee;
    }

    //Generates a formatted payslip as a string
    public String generatePayslip(LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        // Fetch precomputed values from Payroll
        double totalHoursWorked = payroll.getTotalHoursWorked();
        double regularPay = payroll.calculateRegularPay();
        double totalOvertimePay = payroll.calculateTotalOvertimePay();
        double hourlyRate = employee.getCompensationDetails().getHourlyRate();

        return String.format(
            "=== Payroll Computation ===\n" +
            "Employee ID: %d\n" +
            "Full Name: %s %s\n" +
            "Position: %s\n" +
            "Start Period: %s\n" +
            "End Period: %s\n" +
            "-----------------------------------\n" +
            "Total Hours Worked: %.2f\n" +
            "Hourly Rate: %.2f\n" +
            "Regular Pay: %.2f\n" +     
            "Overtime Pay: %.2f\n" +
            "Allowances: %.2f\n" +   
            "Gross Pay: %.2f\n" +
            "Total Deductions: %.2f\n" +
            "Net Pay: %.2f\n",
            employee.getEmployeeId(),
            employee.getFirstName(), employee.getLastName(),
            employee.getPosition(),
            startDate.format(formatter), endDate.format(formatter),
            totalHoursWorked,
            hourlyRate,
            regularPay,
            totalOvertimePay,
            payroll.calculateBenefits(),
            payroll.calculateGrossPay(),
            payroll.calculateTotalDeduction(),
            payroll.calculateNetPay()
        );
    }

    //Saves the generated payslip to a text file inside the 'src' directory
    public void savePayslipToFile(Employee employee, LocalDate startDate, LocalDate endDate, String payslipDetails) {
        try {
            //// Format filename properly to avoid issues
            String filename = "Payslip_" + employee.getEmployeeId() + "_" + startDate + "_to_" + endDate + ".txt";
            File file = new File("src/" + filename);
            FileWriter writer = new FileWriter(file);
            writer.write(payslipDetails);
            writer.close();
            JOptionPane.showMessageDialog(null, "Payslip saved successfully!", "Download Complete", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
