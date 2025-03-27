
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

public class Attendance {
    private int employeeId;
    private String lastName;
    private String firstName;
    private LocalDate date;
    private String login;
    private String logout;

    // Constants for time formatting and work hours
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy"); 
    private static final LocalTime START_TIME = LocalTime.parse("09:00", TIME_FORMAT);
    private static final LocalTime END_TIME = LocalTime.parse("17:00", TIME_FORMAT);

    // Constructor to initialize an attendance record
    public Attendance(int employeeId, String lastName, String firstName, LocalDate date, String login, String logout) {
        this.employeeId = employeeId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.date = date;
        this.login = login;
        this.logout = logout;
    }
    
    // Logs the current time as the employee's login time
    public void logTimeIn() {
        this.login = LocalTime.now().format(TIME_FORMAT);
    }

    // Logs the current time as the employee's logout time
    public void logTimeOut() {
        this.logout = LocalTime.now().format(TIME_FORMAT);
    }

    // Formats the attendance record as a CSV string
    public String formatForCSV() {
        return String.format("%d,%s,%s,%s,%s,%s",
                employeeId, lastName, firstName, date.format(DATE_FORMAT),  // 
                (login != null ? login : ""), (logout != null ? logout : ""));
    }

    // Getters for attendance attributes
    public int getEmployeeId() { return employeeId; }
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public LocalDate getDate() { return date; }
    public String getLogin() { return login; }
    public String getLogout() { return logout; }

    // Calculates total hours worked
    public double getHoursWorked() {
        LocalTime loginTime = (login == null || login.isEmpty()) ? START_TIME : LocalTime.parse(login, TIME_FORMAT);
        LocalTime logoutTime = (logout == null || logout.isEmpty()) ? END_TIME : LocalTime.parse(logout, TIME_FORMAT);
        return Duration.between(loginTime, logoutTime).toMinutes() / 60.0;
    }

    // Calculates the number of minutes late
    public int getMinutesLate() {
        if (login == null || login.isEmpty()) return 0;
        LocalTime loginTime = LocalTime.parse(login, TIME_FORMAT);
        return loginTime.isAfter(START_TIME) ? (int) Duration.between(START_TIME, loginTime).toMinutes() : 0;
    }

    // Calculates the number of minutes undertime
    public int getMinutesUndertime() {
        if (logout == null || logout.isEmpty()) return 0;
        LocalTime logoutTime = LocalTime.parse(logout, TIME_FORMAT);
        return logoutTime.isBefore(END_TIME) ? (int) Duration.between(logoutTime, END_TIME).toMinutes() : 0;
    }

    // Calculates overtime hours
    public double getOvertimeHours() {
        if (logout == null || logout.isEmpty()) return 0;
        LocalTime logoutTime = LocalTime.parse(logout, TIME_FORMAT);
        return logoutTime.isAfter(END_TIME) ? Duration.between(END_TIME, logoutTime).toMinutes() / 60.0 : 0;
    }

    // Determines if the employee is on unpaid leave
    public boolean isUnpaidLeave() {
        return (login == null || login.isEmpty()) && (logout == null || logout.isEmpty());
    }
}
