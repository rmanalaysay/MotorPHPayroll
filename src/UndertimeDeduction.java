
// Undertime Deduction
public class UndertimeDeduction extends Deduction {
    private final int minutesUndertime;
    private static final double RATE_PER_MINUTE = 2.0;

    //Constructor initializes an UndertimeDeduction based on attendance data
    public UndertimeDeduction(Attendance attendance) {
        super(attendance.getEmployeeId(), "Undertime Deduction");
        this.minutesUndertime = attendance.getMinutesUndertime();
    }

    @Override
    //Calculates the total undertime deduction
    public double calculateDeduction() {
        return minutesUndertime * RATE_PER_MINUTE;
    }
}