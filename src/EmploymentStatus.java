
// EmploymentStatus class
public class EmploymentStatus {
    private int statusId;
    private String statusName;

    // Constructor to initialize an EmploymentStatus object
    public EmploymentStatus(int statusId, String statusName) {
        this.statusId = statusId;
        this.statusName = statusName;
    }

    public int getStatusId() { return statusId; }
    public String getStatusName() { return statusName; }
    
    public void setStatusId(int statusId) { this.statusId = statusId; }
    public void setStatusName(String statusName) { this.statusName = statusName; }
    
    // Retrieves the employment status of an employee based on their employee ID
    public static EmploymentStatus getEmploymentStatusByEmployeeId(int employeeId) {
        return switch (employeeId) {
            case 101 -> new EmploymentStatus(1, "Regular");
            case 102 -> new EmploymentStatus(2, "Probationary");
            default -> new EmploymentStatus(0, "Unknown");
        };
    }
}