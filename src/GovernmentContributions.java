/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rejoice
 */
// GovernmentContributions class implementing GovernmentContributionsCalculator
class GovernmentContributions implements GovernmentContributionsCalculator {
    private final int employeeId;
    private final CompensationDetails compensationDetails; // Store CompensationDetails

    public GovernmentContributions(int employeeId, CompensationDetails compensationDetails) {
        this.employeeId = employeeId;
        this.compensationDetails = compensationDetails;
    }

    public int getEmployeeId() { return employeeId; }

    @Override
    public double calculateSSSContributions(double basicSalary) {
        return basicSalary <= 3250 ? 135 : Math.min(basicSalary * 0.045, 1125);
    }

    @Override
    public double calculatePhilHealthContributions(double basicSalary) {
        return Math.min(Math.max(basicSalary * 0.035, 400), 1800);
    }

    @Override
    public double calculatePagIbigContributions(double basicSalary) {
        return Math.min(100, basicSalary * 0.02);
    }

    @Override
    public double calculateTaxWithheld(double basicSalary) {
        if (basicSalary <= 20833) return 0;
        if (basicSalary <= 33333) return (basicSalary - 20833) * 0.2;
        if (basicSalary <= 66667) return 2500 + (basicSalary - 33333) * 0.25;
        return 10833 + (basicSalary - 166667) * 0.3;
    }

    @Override
    public double calculateTotalGovernmentContributions(double basicSalary) {
        return calculateSSSContributions(basicSalary) +
               calculatePhilHealthContributions(basicSalary) +
               calculatePagIbigContributions(basicSalary) +
               calculateTaxWithheld(basicSalary);
    }
}