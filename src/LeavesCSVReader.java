/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rejoice
 */
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

// LeavesCSVReader Class
class LeavesCSVReader {
    private static final String LEAVE_FILE = "leaverequest.csv";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static List<LeaveRequest> loadLeaveRequests(Map<Integer, Employee> employees) {
        List<LeaveRequest> leaveRequests = new ArrayList<>();

        if (!Files.exists(Paths.get(LEAVE_FILE))) {
            return leaveRequests; // Return empty list if file doesn't exist
        }

        try (BufferedReader br = new BufferedReader(new FileReader(LEAVE_FILE))) {
            String line;
            boolean isFirstLine = true; // To skip the header

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip the first line (header row)
                    continue;
                }

                String[] data = line.split(",");
                if (data.length < 6) continue; // Ensure we have all required fields

                try {
                    int employeeId = Integer.parseInt(data[0].trim()); // Employee ID
                    String lastName = data[1].trim();
                    String firstName = data[2].trim();
                    String leaveType = data[3].trim();
                    LocalDate startDate = LocalDate.parse(data[4].trim(), FORMATTER);
                    LocalDate endDate = LocalDate.parse(data[5].trim(), FORMATTER);

                    // Retrieve employee from the map
                    Employee employee = employees.get(employeeId);
                    if (employee == null) {
                        System.err.println("Warning: Employee ID " + employeeId + " not found. Skipping.");
                        continue;
                    }

                    // Add the leave request
                    leaveRequests.add(new LeaveRequest(employee, leaveType, startDate, endDate));

                } catch (Exception e) {
                    System.err.println("Skipping invalid entry: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading leave requests: " + e.getMessage());
        }

        return leaveRequests;
    }
}
