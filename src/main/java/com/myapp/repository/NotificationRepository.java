package com.myapp.repository;



import com.myapp.model.Notifications;
import com.myapp.util.DBConnection;
        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.util.ArrayList;
        import java.util.List;

public class NotificationRepository  {
    public static void addNotification(int userId, String message) {
        String query = "INSERT INTO notifications (user_id, message) VALUES (?, ?)";
        try (Connection connection =  DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, message);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<Notifications> getUnreadNotifications(int userId, String name) {
        System.out.println("notification" + name);
        String query = "SELECT message FROM notifications WHERE user_id = ? AND is_read = FALSE";
        List<Notifications> notifications = new ArrayList<>();
        try (Connection connection =  DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                notifications.add(new Notifications(1,name,rs.getString("message")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notifications;
    }

    public static void markNotificationsAsRead(int userId) {
        String query = "UPDATE notifications SET is_read = TRUE WHERE user_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
