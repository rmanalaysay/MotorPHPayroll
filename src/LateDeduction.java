/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author rejoice
 */
// Late Deduction
class LateDeduction extends Deduction {
    private final int minutesLate;
    private static final double RATE_PER_MINUTE = 2.0; // Example rate

    public LateDeduction(int employeeId, int minutesLate) {
        super(employeeId, "Late Deduction");
        this.minutesLate = Math.max(0, minutesLate); // Prevent negative values
    }

    @Override
    public double calculateDeduction() {
        return minutesLate * RATE_PER_MINUTE;
    }
}
