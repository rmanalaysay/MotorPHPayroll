/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author rejoice
 */
// Overtime Class
class Overtime {
    private final int employeeId;
    private final int hoursWorked;
    private static final double OVERTIME_MULTIPLIER = 1.5; // 1.5x standard pay

    public Overtime(int employeeId, int hoursWorked) {
        this.employeeId = employeeId;
        this.hoursWorked = Math.max(0, hoursWorked);
    }

    public int getHoursWorked() { // Added getter method
        return hoursWorked;
    }

    public static double calculateOvertimePay(double totalHoursWorked, double hourlyRate) {
        double overtimeHours = Math.max(0, totalHoursWorked - 160); // Assuming 160 regular hours
        return overtimeHours * hourlyRate * OVERTIME_MULTIPLIER;
    }
}