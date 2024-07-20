
import java.sql.*;
import java.util.Scanner;

public class App {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nMaintenance Management System");
            System.out.println("1. Manage Equipment");
            System.out.println("2. Manage Maintenance Schedules");
            System.out.println("3. Manage Maintenance Logs");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 ->
                    manageEquipment();
                case 2 ->
                    manageMaintenanceSchedules();
                case 3 ->
                    manageMaintenanceLogs();
                case 4 ->
                    System.exit(0);
                default ->
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void manageEquipment() {
        System.out.println("\nEquipment Management");
        System.out.println("1. Add New Equipment");
        System.out.println("2. View Equipment Details");
        System.out.println("3. Update Equipment Information");
        System.out.println("4. Delete Equipment");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1 ->
                addNewEquipment();
            case 2 ->
                viewEquipmentDetails();
            case 3 ->
                updateEquipmentInformation();
            case 4 ->
                deleteEquipment();
            default ->
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void manageMaintenanceSchedules() {
        System.out.println("\nMaintenance Schedule Management");
        System.out.println("1. Schedule Maintenance");
        System.out.println("2. View Maintenance Schedules");
        System.out.println("3. Update Maintenance Schedule");
        System.out.println("4. Cancel Maintenance Schedule");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1 ->
                scheduleMaintenance();
            case 2 ->
                viewMaintenanceSchedules();
            case 3 ->
                updateMaintenanceSchedule();
            case 4 ->
                cancelMaintenanceSchedule();
            default ->
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void manageMaintenanceLogs() {
        System.out.println("\nMaintenance Log Management");
        System.out.println("1. Log Maintenance Activity");
        System.out.println("2. View Maintenance Logs");
        System.out.println("3. Update Maintenance Log");
        System.out.println("4. Delete Maintenance Log");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1 ->
                logMaintenanceActivity();
            case 2 ->
                viewMaintenanceLogs();
            case 3 ->
                updateMaintenanceLog();
            case 4 ->
                deleteMaintenanceLog();
            default ->
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void addNewEquipment() {
        System.out.print("Enter equipment name: ");
        String name = scanner.nextLine();
        System.out.print("Enter equipment description: ");
        String description = scanner.nextLine();
        System.out.print("Enter purchase date (YYYY-MM-DD): ");
        String purchaseDate = scanner.nextLine();
        System.out.print("Enter equipment status (active/inactive): ");
        String status = scanner.nextLine();

        String sql = "INSERT INTO Equipment (name, description, purchase_date, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setDate(3, Date.valueOf(purchaseDate));
            pstmt.setString(4, status);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("New equipment added successfully.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewEquipmentDetails() {
        System.out.print("Enter equipment ID: ");
        int equipmentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String sql = "SELECT * FROM Equipment WHERE equipment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, equipmentId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Equipment ID: " + rs.getInt("equipment_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Description: " + rs.getString("description"));
                System.out.println("Purchase Date: " + rs.getDate("purchase_date"));
                System.out.println("Status: " + rs.getString("status"));
            } else {
                System.out.println("Equipment not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateEquipmentInformation() {
        System.out.print("Enter equipment ID to update: ");
        int equipmentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter new equipment name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new equipment description: ");
        String description = scanner.nextLine();
        System.out.print("Enter new purchase date (YYYY-MM-DD): ");
        String purchaseDate = scanner.nextLine();
        System.out.print("Enter new equipment status (active/inactive): ");
        String status = scanner.nextLine();

        String sql = "UPDATE Equipment SET name = ?, description = ?, purchase_date = ?, status = ? WHERE equipment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setDate(3, Date.valueOf(purchaseDate));
            pstmt.setString(4, status);
            pstmt.setInt(5, equipmentId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Equipment updated successfully.");
            } else {
                System.out.println("Equipment not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteEquipment() {
        System.out.print("Enter equipment ID to delete: ");
        int equipmentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String sql = "DELETE FROM Equipment WHERE equipment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, equipmentId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Equipment deleted successfully.");
            } else {
                System.out.println("Equipment not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void scheduleMaintenance() {
        System.out.print("Enter equipment ID: ");
        int equipmentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter scheduled date (YYYY-MM-DD): ");
        String scheduledDate = scanner.nextLine();
        System.out.print("Enter status (scheduled/completed/cancelled): ");
        String status = scanner.nextLine();

        String sql = "INSERT INTO MaintenanceSchedule (equipment_id, scheduled_date, status) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, equipmentId);
            pstmt.setDate(2, Date.valueOf(scheduledDate));
            pstmt.setString(3, status);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Maintenance scheduled successfully.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewMaintenanceSchedules() {
        System.out.print("Enter equipment ID: ");
        int equipmentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String sql = "SELECT * FROM MaintenanceSchedule WHERE equipment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, equipmentId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println("Schedule ID: " + rs.getInt("schedule_id"));
                System.out.println("Scheduled Date: " + rs.getDate("scheduled_date"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateMaintenanceSchedule() {
        System.out.print("Enter schedule ID to update: ");
        int scheduleId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter new equipment ID: ");
        int equipmentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new scheduled date (YYYY-MM-DD): ");
        String scheduledDate = scanner.nextLine();
        System.out.print("Enter new status (scheduled/completed/cancelled): ");
        String status = scanner.nextLine();

        String sql = "UPDATE MaintenanceSchedule SET equipment_id = ?, scheduled_date = ?, status = ? WHERE schedule_id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, equipmentId);
            pstmt.setDate(2, Date.valueOf(scheduledDate));
            pstmt.setString(3, status);
            pstmt.setInt(4, scheduleId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Maintenance schedule updated successfully.");
            } else {
                System.out.println("Schedule not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void cancelMaintenanceSchedule() {
        System.out.print("Enter schedule ID to cancel: ");
        int scheduleId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String sql = "UPDATE MaintenanceSchedule SET status = 'cancelled' WHERE schedule_id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, scheduleId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Maintenance schedule cancelled successfully.");
            } else {
                System.out.println("Schedule not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void logMaintenanceActivity() {
        System.out.print("Enter equipment ID: ");
        int equipmentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter maintenance date (YYYY-MM-DD): ");
        String maintenanceDate = scanner.nextLine();
        System.out.print("Enter maintenance description: ");
        String description = scanner.nextLine();
        System.out.print("Enter technician name: ");
        String technicianName = scanner.nextLine();

        String sql = "INSERT INTO MaintenanceLog (equipment_id, maintenance_date, description, technician_name) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, equipmentId);
            pstmt.setDate(2, Date.valueOf(maintenanceDate));
            pstmt.setString(3, description);
            pstmt.setString(4, technicianName);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Maintenance activity logged successfully.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewMaintenanceLogs() {
        System.out.print("Enter equipment ID: ");
        int equipmentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String sql = "SELECT * FROM MaintenanceLog WHERE equipment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, equipmentId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println("Log ID: " + rs.getInt("log_id"));
                System.out.println("Maintenance Date: " + rs.getDate("maintenance_date"));
                System.out.println("Description: " + rs.getString("description"));
                System.out.println("Technician Name: " + rs.getString("technician_name"));
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateMaintenanceLog() {
        System.out.print("Enter log ID to update: ");
        int logId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter new equipment ID: ");
        int equipmentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new maintenance date (YYYY-MM-DD): ");
        String maintenanceDate = scanner.nextLine();
        System.out.print("Enter new maintenance description: ");
        String description = scanner.nextLine();
        System.out.print("Enter new technician name: ");
        String technicianName = scanner.nextLine();

        String sql = "UPDATE MaintenanceLog SET equipment_id = ?, maintenance_date = ?, description = ?, technician_name = ? WHERE log_id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, equipmentId);
            pstmt.setDate(2, Date.valueOf(maintenanceDate));
            pstmt.setString(3, description);
            pstmt.setString(4, technicianName);
            pstmt.setInt(5, logId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Maintenance log updated successfully.");
            } else {
                System.out.println("Maintenance log not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteMaintenanceLog() {
        System.out.print("Enter log ID to delete: ");
        int logId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String sql = "DELETE FROM MaintenanceLog WHERE log_id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, logId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Maintenance log deleted successfully.");
            } else {
                System.out.println("Maintenance log not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
