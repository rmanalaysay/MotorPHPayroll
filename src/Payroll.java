/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
/**
 *
 * @author rejoice
 */
// Payroll class implementing PayrollCalculator
class Payroll {
    private final int payrollId;
    private final int employeeId;
    private final double basicSalary;
    private final double deductions;
    private final double benefits;
    private final List<Overtime> overtime;
    
    // Define TIME_FORMAT inside the class
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    public Payroll(int payrollId, int employeeId, double basicSalary, double deductions, double benefits, List<Overtime> overtime) {
        this.payrollId = payrollId;
        this.employeeId = employeeId;
        this.basicSalary = basicSalary;
        this.deductions = deductions;
        this.benefits = benefits;
        this.overtime = (overtime != null) ? Collections.unmodifiableList(overtime) : Collections.emptyList();
    }

    public double calculateNetPay() {
        return basicSalary + benefits - deductions + overtime.stream()
                .mapToDouble(o -> o.calculateOvertimePay(basicSalary / 160))
                .sum();
    }
    
    // Helper method for calculating hours worked
    public static double calculateHoursWorked(String login, String logout) {
        LocalTime loginTime = LocalTime.parse(login, TIME_FORMAT);
        LocalTime logoutTime = LocalTime.parse(logout, TIME_FORMAT);
        return Duration.between(loginTime, logoutTime).toMinutes() / 60.0;
    }
}
