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

    public double calculateOvertimePay(double hourlyRate) {
        return hoursWorked * hourlyRate * OVERTIME_MULTIPLIER;
    }
}
