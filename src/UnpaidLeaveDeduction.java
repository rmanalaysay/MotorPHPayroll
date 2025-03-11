/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author rejoice
 */
// Unpaid Leave Deduction
class UnpaidLeaveDeduction extends Deduction {
    private int daysUnpaidLeave;
    private static final double DAILY_RATE = 500.0; // Example daily rate

    public UnpaidLeaveDeduction(int employeeId, int daysUnpaidLeave) {
        super(employeeId, "Unpaid Leave Deduction");
        this.daysUnpaidLeave = Math.max(0, daysUnpaidLeave);
    }

    @Override
    public double calculateDeduction() {
        return daysUnpaidLeave * DAILY_RATE;
    }
}
