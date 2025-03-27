
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class LeaveRequest {
    private final Employee employee;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String leaveType;

    //Constructs a LeaveRequest object
    public LeaveRequest(Employee employee, String leaveType, LocalDate startDate, LocalDate endDate) {
        this.employee = employee;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters for class properties
    public Employee getEmployee() {
        return employee;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getLeaveType() {
        return leaveType;
    }
    
    /**
     * Validates the leave request by ensuring:
     * - The start date is in the future.
     * - The end date is not before the start date.
     */
    public boolean isValid() {
        LocalDate today = LocalDate.now();
        return startDate.isAfter(today) && !endDate.isBefore(startDate);
    }

    /**
     * Approves the leave request. If the leave type is "Unpaid",
     * an UnpaidLeaveDeduction is created based on the number of days requested.
     */
    public void approveLeave() {
        if ("Unpaid".equalsIgnoreCase(leaveType)) {
            int days = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
            new UnpaidLeaveDeduction(employee.getEmployeeId(), days);
        }
    }
}
