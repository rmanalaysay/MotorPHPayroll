/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rejoice
 */
// EmploymentStatus class
public class EmploymentStatus {
    private int statusId;
    private String statusName;

    public EmploymentStatus(int statusId, String statusName) {
        this.statusId = statusId;
        this.statusName = statusName;
    }

    public int getStatusId() { return statusId; }
    public String getStatusName() { return statusName; }
    
    public void setStatusId(int statusId) { this.statusId = statusId; }
    public void setStatusName(String statusName) { this.statusName = statusName; }
    
    public static EmploymentStatus getEmploymentStatusByEmployeeId(int employeeId) {
        return switch (employeeId) {
            case 101 -> new EmploymentStatus(1, "Regular");
            case 102 -> new EmploymentStatus(2, "Probationary");
            default -> new EmploymentStatus(0, "Unknown");
        };
    }
}