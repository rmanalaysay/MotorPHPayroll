/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

/**
 *
 * @author rejoice
 */
// Attendance Class
class Attendance {
    private final int employeeId;
    private final LocalDate date;
    private final String login;
    private final String logout;

    public Attendance(int employeeId, LocalDate date, String login, String logout) {
        this.employeeId = employeeId;
        this.date = date;
        this.login = login;
        this.logout = logout;
    }

    public int getEmployeeId() { return employeeId; }
    public LocalDate getDate() { return date; }
    public String getLogin() { return login; }
    public String getLogout() { return logout; }
    
    public double getHoursWorked() {
        return Payroll.calculateHoursWorked(login, logout);
    }
}