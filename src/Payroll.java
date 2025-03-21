import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Payroll implements PayrollCalculator {
    private final Employee employee;
    private final List<Attendance> attendanceRecords;
    private final CompensationDetails compensation;
    private final GovernmentContributions contributions;

    private int minutesLate;
    private int minutesUndertime;
    private int daysUnpaidLeave;

    public Payroll(Employee employee, List<Attendance> attendanceRecords) {
        this.employee = employee;
        this.attendanceRecords = attendanceRecords;
        this.compensation = employee.getCompensationDetails();
        this.contributions = employee.getGovernmentContributions();
        this.minutesLate = 0;
        this.minutesUndertime = 0;
        this.daysUnpaidLeave = 0;

        for (Attendance record : attendanceRecords) {
            this.minutesLate += record.getMinutesLate();
            this.minutesUndertime += record.getMinutesUndertime();

            if (record.isUnpaidLeave()) {  
                this.daysUnpaidLeave++;
            }
        }
    }

    @Override
    public double calculateEarnings() {
        double totalHoursWorked = attendanceRecords.stream()
            .mapToDouble(Attendance::getHoursWorked)
            .sum();

        double regularPay = totalHoursWorked * compensation.getHourlyRate();
        double overtimePay = Overtime.calculateOvertimePay(totalHoursWorked, compensation.getHourlyRate());

        return regularPay + overtimePay;
    }

    @Override
    public double calculateBenefits() {
        return compensation.getTotalBenefits();
    }
    
    @Override
    public double calculateGrossPay() {
        return calculateEarnings() + compensation.getTotalBenefits();
    }

    @Override
    public double calculateTotalDeduction() {
        double lateDeduction = new LateDeduction(employee.getEmployeeId(), minutesLate).calculateDeduction();
        double undertimeDeduction = new UndertimeDeduction(employee.getEmployeeId(), minutesUndertime).calculateDeduction();
        double unpaidLeaveDeduction = new UnpaidLeaveDeduction(employee.getEmployeeId(), daysUnpaidLeave).calculateDeduction();
        double governmentDeductions = contributions.calculateTotalGovernmentContributions(compensation.getBasicSalary());

        return lateDeduction + undertimeDeduction + unpaidLeaveDeduction + governmentDeductions;
    }

    @Override
    public double calculateNetPay() {  
        return Math.round((calculateGrossPay() - calculateTotalDeduction()) * 100.0) / 100.0;
    }

    public String generatePayrollSummary(LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        double totalHoursWorked = attendanceRecords.stream().mapToDouble(Attendance::getHoursWorked).sum();
        double regularPay = totalHoursWorked * compensation.getHourlyRate();
        double overtimePay = Overtime.calculateOvertimePay(totalHoursWorked, compensation.getHourlyRate());

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
            compensation.getHourlyRate(),
            regularPay,
            overtimePay,
            calculateBenefits(),
            calculateGrossPay(),
            calculateTotalDeduction(),
            calculateNetPay()
        );
    }
}
