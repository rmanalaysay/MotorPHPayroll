/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author rejoice
 */

class Employee {
    private final int employeeId;
    private final String firstName;
    private final String lastName;
    private final String position;
    private final EmploymentStatus employmentStatus;
    private final CompensationDetails compensationDetails;
    private final GovernmentContributions governmentContributions;

    public Employee(int employeeId, String firstName, String lastName, String position,
                    EmploymentStatus employmentStatus, CompensationDetails compensationDetails, 
                    GovernmentContributions governmentContributions) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.employmentStatus = employmentStatus;
        this.compensationDetails = compensationDetails;
        this.governmentContributions = governmentContributions;
    }

    public int getEmployeeId() { return employeeId; }
    
    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getPosition() { return position; }

    public EmploymentStatus getEmploymentStatus() { return employmentStatus;}

    public CompensationDetails getCompensationDetails() { return compensationDetails; }

    public GovernmentContributions getGovernmentContributions() { return governmentContributions; }
}