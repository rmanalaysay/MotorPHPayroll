import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class LeaveRequest {
    private final Employee employee;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String leaveType;

    public LeaveRequest(Employee employee, String leaveType, LocalDate startDate, LocalDate endDate) {
        this.employee = employee;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

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
    
    public boolean isValid() {
        LocalDate today = LocalDate.now();
        return startDate.isAfter(today) && !endDate.isBefore(startDate);
    }

    public void approveLeave() {
        if ("Unpaid".equalsIgnoreCase(leaveType)) {
            int days = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
            new UnpaidLeaveDeduction(employee.getEmployeeId(), days);
        }
    }
}
