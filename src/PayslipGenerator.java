
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;

public class PayslipGenerator {
    private final Payroll payroll;

    public PayslipGenerator(Employee employee, List<Attendance> attendanceRecords) {
        this.payroll = new Payroll(employee, attendanceRecords);
    }

    public String generatePayslip(LocalDate startDate, LocalDate endDate) {
        return payroll.generatePayrollSummary(startDate, endDate);
    }

    public void savePayslipToFile(Employee employee, LocalDate startDate, LocalDate endDate, String payslipDetails) {
        try {
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
