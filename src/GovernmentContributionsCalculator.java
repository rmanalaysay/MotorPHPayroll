
// Government Contributions
interface GovernmentContributionsCalculator {
    double calculateSSSContributions(double basicSalary);
    double calculatePhilHealthContributions(double basicSalary);
    double calculatePagIbigContributions(double basicSalary);
    double calculateTaxWithheld(double basicSalary);
    double calculateTotalGovernmentContributions(double basicSalary);
}