/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author rejoice
 */
// Government Contributions
interface GovernmentContributionsCalculator {
    double calculateSSSContributions(double basicSalary);
    double calculatePhilHealthContributions(double basicSalary);
    double calculatePagIbigContributions(double basicSalary);
    double calculateTaxWithheld(double basicSalary);
    double calculateTotalGovernmentContributions(double basicSalary);
}