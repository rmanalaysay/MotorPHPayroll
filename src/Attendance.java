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

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy"); // ✅ FIXED: Added missing DATE_FORMAT
    private static final LocalTime START_TIME = LocalTime.parse("09:00", TIME_FORMAT);
    private static final LocalTime END_TIME = LocalTime.parse("17:00", TIME_FORMAT);

    public Attendance(int employeeId, String lastName, String firstName, LocalDate date, String login, String logout) {
        this.employeeId = employeeId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.date = date;
        this.login = login;
        this.logout = logout;
    }
    
    // Log Time In
    public void logTimeIn() {
        this.login = LocalTime.now().format(TIME_FORMAT);
    }

    // Log Time Out
    public void logTimeOut() {
        this.logout = LocalTime.now().format(TIME_FORMAT);
    }

    public String formatForCSV() {
        return String.format("%d,%s,%s,%s,%s,%s",
                employeeId, lastName, firstName, date.format(DATE_FORMAT),  // ✅ FIXED: Now DATE_FORMAT exists
                (login != null ? login : ""), (logout != null ? logout : ""));
    }

    public int getEmployeeId() { return employeeId; }
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public LocalDate getDate() { return date; }
    public String getLogin() { return login; }
    public String getLogout() { return logout; }

    public double getHoursWorked() {
        LocalTime loginTime = (login == null || login.isEmpty()) ? START_TIME : LocalTime.parse(login, TIME_FORMAT);
        LocalTime logoutTime = (logout == null || logout.isEmpty()) ? END_TIME : LocalTime.parse(logout, TIME_FORMAT);
        return Duration.between(loginTime, logoutTime).toMinutes() / 60.0;
    }

    public int getMinutesLate() {
        if (login == null || login.isEmpty()) return 0;
        LocalTime loginTime = LocalTime.parse(login, TIME_FORMAT);
        return loginTime.isAfter(START_TIME) ? (int) Duration.between(START_TIME, loginTime).toMinutes() : 0;
    }

    public int getMinutesUndertime() {
        if (logout == null || logout.isEmpty()) return 0;
        LocalTime logoutTime = LocalTime.parse(logout, TIME_FORMAT);
        return logoutTime.isBefore(END_TIME) ? (int) Duration.between(logoutTime, END_TIME).toMinutes() : 0;
    }

    public double getOvertimeHours() {
        if (logout == null || logout.isEmpty()) return 0;
        LocalTime logoutTime = LocalTime.parse(logout, TIME_FORMAT);
        return logoutTime.isAfter(END_TIME) ? Duration.between(END_TIME, logoutTime).toMinutes() / 60.0 : 0;
    }

    public boolean isUnpaidLeave() {
        return (login == null || login.isEmpty()) && (logout == null || logout.isEmpty());
    }
}
