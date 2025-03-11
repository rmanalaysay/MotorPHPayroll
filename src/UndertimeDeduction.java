/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author rejoice
 */
// Undertime Deduction
class UndertimeDeduction extends Deduction {
    private int minutesUndertime;
    private static final double RATE_PER_MINUTE = 2.0; // Example rate

    public UndertimeDeduction(int employeeId, int minutesUndertime) {
        super(employeeId, "Undertime Deduction");
        this.minutesUndertime = Math.max(0, minutesUndertime);
    }

    @Override
    public double calculateDeduction() {
        return minutesUndertime * RATE_PER_MINUTE;
    }
}