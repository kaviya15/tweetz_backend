package com.myapp.repository;

import com.myapp.model.Follower;

import com.myapp.model.User;
import com.myapp.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

public class FollowRepository {

    public void FollowUser(Follower follower){
        String query = "INSERT INTO followersDetails (follower_id, followee_id) values (?,?) ";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, follower.get_follower_id());
            stmt.setInt(2, follower.get_followee_id());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public void unfollowUser(Follower follower) {
        String query = "DELETE FROM followersDetails WHERE follower_id = ? AND followee_id = ?";
        try (Connection connection =DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, follower.get_follower_id());
            stmt.setInt(2, follower.get_followee_id());
            stmt.executeUpdate();
            System.out.println("Successfully unfollowed the user.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // instead of returning the string return the follower users, with the id
    public List<User> getFollowing(int userId) {
        String query = "SELECT u.name,u.id FROM users u JOIN followersDetails f ON u.id = f.followee_id WHERE f.follower_id = ?" ;
        List<User> followedUsers = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                followedUsers.add( new User( rs.getInt("id"),   rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return followedUsers;
    }

    public List<User> getFollowers(int followeeId) {
        String query = "SELECT u.name,u.id FROM users u JOIN followersDetails f ON u.id = f.followee_id WHERE f.follower_id = ?" ;
        List<User> followedUsers = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, followeeId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                followedUsers.add( new User( rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return followedUsers;
    }





}
