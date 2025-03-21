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

    public double getRiceSubsidy() {
        return riceSubsidy;
    }

    public double getPhoneAllowance() {
        return phoneAllowance;
    }

    public double getClothingAllowance() {
        return clothingAllowance;
    }
    
    public double getBasicSalary() { 
        return basicSalary; }
    
    public double getHourlyRate() { 
        return hourlyRate; }
    
    public double getTotalBenefits() { 
        return riceSubsidy + phoneAllowance + clothingAllowance; }
}