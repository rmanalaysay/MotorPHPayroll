
import java.util.List;

// Unpaid Leave Deduction
public class UnpaidLeaveDeduction extends Deduction {
    private final int daysUnpaidLeave;
    private static final double DAILY_RATE = 500.0; // Example daily rate

    // Constructor for planned leave (e.g., from LeaveRequest)
    public UnpaidLeaveDeduction(int employeeId, int daysUnpaidLeave) {
        super(employeeId, "Unpaid Leave Deduction");
        this.daysUnpaidLeave = Math.max(0, daysUnpaidLeave);
    }

    // Constructor for unplanned absences (e.g., from Attendance)
    public UnpaidLeaveDeduction(List<Attendance> attendanceRecords) {
        super(attendanceRecords.get(0).getEmployeeId(), "Unpaid Leave Deduction");
        this.daysUnpaidLeave = (int) attendanceRecords.stream()
                .filter(Attendance::isUnpaidLeave)
                .count();
    }

    @Override
    //Calculates the total unpaid leave deduction
    public double calculateDeduction() {
        return daysUnpaidLeave * DAILY_RATE;
    }
}
