// Interface for Payroll calculations
public interface PayrollCalculator {
    double calculateEarnings();
    double calculateBenefits();
    double calculateGrossPay();
    double calculateTotalDeduction();
    double calculateNetPay();
}