package com.myapp.repository;

import com.myapp.model.User;
import com.myapp.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
public class UserRepository {
    public List<User> findAll() {

        System.out.println("find all call method invoked");
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
             System.out.println(rs);
            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return users;
    }

    public void save(User user) {
        String query = "INSERT INTO users (id, name) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, user.getId());
            stmt.setString(2, user.getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }






    public void followUser(int follower_id , int followee_id){
        String query = "INSERT INTO followersDetails(follower_id ,followee_id) values (? ,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, follower_id);
            stmt.setInt(2, followee_id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void unfollowUser(int follower_id , int followee_id){
        String query = "DELETE FROM followersDetails(follower_id ,followee_id) values (? ,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, follower_id);
            stmt.setInt(2, followee_id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

