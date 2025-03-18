/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author rejoice
 */
class Payroll implements PayrollCalculator { 
    private final int employeeId;
    private final double basicSalary;
    private final CompensationDetails compensationDetails;
    private final double benefits;
    private final List<Overtime> overtime;
    private int minutesLate;
    private int minutesUndertime;
    private final int daysUnpaidLeave;
    private final GovernmentContributions contributions;
    private final List<Attendance> attendanceRecords; // Store attendance records

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    public Payroll(int employeeId, CompensationDetails compensationDetails, double benefits, 
               List<Attendance> attendanceRecords, GovernmentContributions contributions) {
        this.employeeId = employeeId;
        this.compensationDetails = compensationDetails;
        this.basicSalary = compensationDetails.getBasicSalary();
        this.benefits = benefits;
        this.overtime = new ArrayList<>();
        this.minutesLate = 0;
        this.minutesUndertime = 0;
        this.daysUnpaidLeave = 0;
        this.contributions = contributions;
        this.attendanceRecords = attendanceRecords; // Store attendanceRecords

        for (Attendance record : attendanceRecords) {
            this.minutesLate += record.getMinutesLate();
            this.minutesUndertime += record.getMinutesUndertime();

            if (record.getOvertimeHours() > 0) {
                overtime.add(new Overtime(employeeId, (int) record.getOvertimeHours()));
            }
        }
    }
    
    public double getTotalHoursWorked() {
        double totalHoursWorked = 0;

        for (Attendance record : attendanceRecords) { // No need to pass it as a parameter
            totalHoursWorked += record.getHoursWorked();
        }

        return totalHoursWorked;
    }

    public static double calculateHoursWorked(String login, String logout) {
        LocalTime loginTime = LocalTime.parse(login, TIME_FORMAT);
        LocalTime logoutTime = LocalTime.parse(logout, TIME_FORMAT);
        return Duration.between(loginTime, logoutTime).toMinutes() / 60.0;
    }

    @Override
    public double calculateEarnings(double totalHoursWorked, double hourlyRate) {
        // Regular earnings + Overtime pay
        return (totalHoursWorked * hourlyRate);
    }

    @Override
    public double calculateBenefits() {
        return benefits;
    }

    @Override
    public double calculateTotalDeduction() {
        double lateDeduction = new LateDeduction(employeeId, minutesLate).calculateDeduction();
        double undertimeDeduction = new UndertimeDeduction(employeeId, minutesUndertime).calculateDeduction();
        double unpaidLeaveDeduction = new UnpaidLeaveDeduction(employeeId, daysUnpaidLeave).calculateDeduction();
        double governmentContributions = contributions.calculateTotalGovernmentContributions(basicSalary);
        
        return lateDeduction + undertimeDeduction + unpaidLeaveDeduction + governmentContributions;
    }

    @Override
    public double calculateNetPay() {  
        double totalHoursWorked = getTotalHoursWorked(); // Get correct worked hours
        double hourlyRate = compensationDetails.getHourlyRate();
        double earnings = calculateEarnings(totalHoursWorked, hourlyRate);
        double grossPay = earnings + benefits; // Corrected formula
        double totalDeductions = calculateTotalDeduction();

        double netPay = grossPay - totalDeductions;
        return Math.round(netPay * 100.0) / 100.0; // Round to 2 decimal places
    }
}
