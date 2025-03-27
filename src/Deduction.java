
abstract class Deduction {
    protected final int employeeId;
    protected final String type;

    //Constructor to initialize a deduction for a specific employee
    public Deduction(int employeeId, String type) {
        this.employeeId = employeeId;
        this.type = type;
    }
    
    //Abstract method to calculate the deduction amount
    public abstract double calculateDeduction();

    public int getEmployeeId() { return employeeId; }
    public String getType() { return type; }
}