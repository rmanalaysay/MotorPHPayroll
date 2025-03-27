
// Late Deduction
public class LateDeduction extends Deduction {
    private final int minutesLate;
    private static final double RATE_PER_MINUTE = 2.0;

    //Constructor initializes a LateDeduction based on attendance data
    public LateDeduction(Attendance attendance) {
        super(attendance.getEmployeeId(), "Late Deduction");
        this.minutesLate = attendance.getMinutesLate();
    }

    @Override
    //Calculates the total late deduction
    public double calculateDeduction() {
        return minutesLate * RATE_PER_MINUTE;
    }
}