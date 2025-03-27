
import java.util.List;

public class Payroll implements PayrollCalculator {
    private final Employee employee;
    private final List<Attendance> attendanceRecords;
    private final CompensationDetails compensation;
    private final GovernmentContributions contributions;
    private int minutesLate;
    private int minutesUndertime;
    private int daysUnpaidLeave;
    
    //Constructs a Payroll object for an employee using their attendance records
    public Payroll(Employee employee, List<Attendance> attendanceRecords) {
        this.employee = employee;
        this.attendanceRecords = attendanceRecords;
        this.compensation = employee.getCompensationDetails();
        this.contributions = employee.getGovernmentContributions();
        this.minutesLate = 0;
        this.minutesUndertime = 0;
        this.daysUnpaidLeave = 0;

        // Loop through attendance records to calculate total late minutes, undertime, and unpaid leave days
        for (Attendance record : attendanceRecords) {
            this.minutesLate += record.getMinutesLate();
            this.minutesUndertime += record.getMinutesUndertime();

            if (record.isUnpaidLeave()) {  
                this.daysUnpaidLeave++;
            }
        }
    }
    
    //Gets the attendance records of the employee
    public List<Attendance> getAttendanceRecords() {
        return attendanceRecords;
    }

    // Gets the employee associated with this payroll
    public Employee getEmployee() {
        return employee;
    }

    // Calculates the total hours worked based on attendance records
    public double getTotalHoursWorked() {
        return attendanceRecords.stream()
            .mapToDouble(Attendance::getHoursWorked)
            .sum();
    }

    //Calculates the regular pay (excluding overtime)
    public double calculateRegularPay() {
        return getTotalHoursWorked() * compensation.getHourlyRate();
    }

    //Calculates the total overtime pay
    public double calculateTotalOvertimePay() {
        Overtime overtime = new Overtime(attendanceRecords);
        return overtime.calculateTotalOvertimePay(compensation.getHourlyRate());
    }

    @Override
    //Calculates the total earnings (regular pay + overtime pay)
    public double calculateEarnings() {
        return calculateRegularPay() + calculateTotalOvertimePay();
    }

    @Override
    //Calculates the total benefits
    public double calculateBenefits() {
        return compensation.getTotalBenefits();
    }
    
    @Override
    //Calculates the gross pay (earnings + benefits)
    public double calculateGrossPay() {
        return calculateEarnings() + compensation.getTotalBenefits();
    }

    @Override
    //Calculates the total deductions including late, undertime, unpaid leave, and government deductions
    public double calculateTotalDeduction() {
        // Deduction calculations based on the first attendance record
        double lateDeduction = new LateDeduction(attendanceRecords.get(0)).calculateDeduction(); 
        double undertimeDeduction = new UndertimeDeduction(attendanceRecords.get(0)).calculateDeduction();
        double unpaidLeaveDeduction = new UnpaidLeaveDeduction(attendanceRecords).calculateDeduction();
        double governmentDeductions = contributions.calculateTotalGovernmentContributions(compensation.getBasicSalary());

        return lateDeduction + undertimeDeduction + unpaidLeaveDeduction + governmentDeductions;
    }

    @Override
    //Calculates the net pay after all deductions
    public double calculateNetPay() {  
        return Math.round((calculateGrossPay() - calculateTotalDeduction()) * 100.0) / 100.0;
    }
}
