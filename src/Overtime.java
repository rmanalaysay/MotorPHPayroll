
import java.util.List;

// Overtime Class
public class Overtime {
    private final List<Attendance> attendanceRecords;
    private static final double OVERTIME_MULTIPLIER = 1.25;

    //Constructs an Overtime object with a list of attendance records
    public Overtime(List<Attendance> attendanceRecords) {
        this.attendanceRecords = attendanceRecords;
    }

    //Calculates the total overtime pay for an employee
    public double calculateTotalOvertimePay(double hourlyRate) {
        double totalOvertimeHours = 0;

        // Sum up all overtime hours from the attendance records
        for (Attendance attendance : attendanceRecords) {
            totalOvertimeHours += attendance.getOvertimeHours();
        }

        // Compute total overtime pay
        return totalOvertimeHours * hourlyRate * OVERTIME_MULTIPLIER;
    }
}