/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author rejoice
 */
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

    public void approveLeave() {
        if ("Unpaid".equalsIgnoreCase(leaveType)) {
            int days = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
            new UnpaidLeaveDeduction(employee.getEmployeeId(), days);
        }
    }

    public boolean overlapsWith(LeaveRequest other) {
        return !startDate.isAfter(other.endDate) && !endDate.isBefore(other.startDate);
    }
}
