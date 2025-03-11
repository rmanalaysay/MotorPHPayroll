/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author rejoice
 */
abstract class Deduction {
    protected final int employeeId;
    protected final String type;

    public Deduction(int employeeId, String type) {
        this.employeeId = employeeId;
        this.type = type;
    }

    public abstract double calculateDeduction();

    public int getEmployeeId() { return employeeId; }
    public String getType() { return type; }
}