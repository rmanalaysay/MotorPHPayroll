/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rejoice
 */
class GovernmentContributions implements GovernmentContributionsCalculator {
    private final int employeeId;
    private final CompensationDetails compensationDetails; // Store CompensationDetails

    public GovernmentContributions(int employeeId, CompensationDetails compensationDetails) {
        this.employeeId = employeeId;
        this.compensationDetails = compensationDetails;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    /**
     * Calculates the employee's SSS contribution.
     * - Minimum contribution: ₱135 (for salary ≤ ₱3,250)
     * - Maximum contribution: ₱1,125 (for salary ≥ ₱30,000)
     * - Formula: 4.5% of the basic salary
     */
    @Override
    public double calculateSSSContributions(double basicSalary) {
        if (basicSalary <= 3250) return 135; // Minimum SSS contribution
        return Math.min(basicSalary * 0.045, 1125); // 4.5% of salary, capped at ₱1,125
    }

    /**
     * Calculates the employee's PhilHealth contribution.
     * - Contribution rate: 5% of basic salary (split equally between employer & employee)
     * - Minimum contribution: ₱400
     * - Maximum contribution: ₱1,800
     */
    @Override
    public double calculatePhilHealthContributions(double basicSalary) {
        return Math.min(Math.max(basicSalary * 0.025, 400), 1800); // 2.5% of salary (since employee pays half)
    }

    /**
     * Calculates the employee's Pag-IBIG contribution.
     * - 1% of salary if ≤ ₱1,500
     * - 2% of salary if > ₱1,500
     * - Maximum contribution: ₱100
     */
    @Override
    public double calculatePagIbigContributions(double basicSalary) {
        return Math.min(100, basicSalary > 1500 ? basicSalary * 0.02 : basicSalary * 0.01);
    }

    /**
     * Calculates the monthly tax withheld based on the latest BIR tax table.
     * - Salary ≤ ₱20,833: No tax
     * - Salary ₱20,833 – ₱33,333: 20% of excess over ₱20,833
     * - Salary ₱33,333 – ₱66,667: ₱2,500 + 25% of excess over ₱33,333
     * - Salary ₱66,667 – ₱166,667: ₱10,833 + 30% of excess over ₱66,667
     * - Salary ₱166,667 – ₱666,667: ₱40,833 + 32% of excess over ₱166,667
     * - Salary > ₱666,667: ₱200,833 + 35% of excess over ₱666,667
     */
    @Override
    public double calculateTaxWithheld(double basicSalary) {
        if (basicSalary <= 20833) return 0; // No tax for salaries below ₱20,833
        if (basicSalary <= 33333) return (basicSalary - 20833) * 0.20;
        if (basicSalary <= 66667) return 2500 + (basicSalary - 33333) * 0.25;
        if (basicSalary <= 166667) return 10833 + (basicSalary - 66667) * 0.30;
        if (basicSalary <= 666667) return 40833 + (basicSalary - 166667) * 0.32;
        return 200833 + (basicSalary - 666667) * 0.35;
    }

    /**
     * Calculates the total government contributions deducted from the employee's salary.
     * - SSS + PhilHealth + Pag-IBIG + Tax Withheld
     */
    @Override
    public double calculateTotalGovernmentContributions(double basicSalary) {
        return calculateSSSContributions(basicSalary) +
               calculatePhilHealthContributions(basicSalary) +
               calculatePagIbigContributions(basicSalary) +
               calculateTaxWithheld(basicSalary);
    }
}
