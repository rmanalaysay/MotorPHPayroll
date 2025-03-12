/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author rejoice
 */
// Compensation Details Class
class CompensationDetails {
    private final double basicSalary;
    private final double riceSubsidy;
    private final double phoneAllowance;
    private final double clothingAllowance;
    private final double hourlyRate;

    public CompensationDetails(double basicSalary, double riceSubsidy, double phoneAllowance, double clothingAllowance, double hourlyRate) {
        this.basicSalary = basicSalary;
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        this.hourlyRate = hourlyRate;
    }

    public double getBasicSalary() { return basicSalary; }
    public double getHourlyRate() { return hourlyRate; }
    public double getTotalBenefits() { return riceSubsidy + phoneAllowance + clothingAllowance; }
}