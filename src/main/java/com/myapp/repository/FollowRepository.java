package com.myapp.repository;

import com.myapp.model.Followers;

import com.myapp.model.User;
import com.myapp.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

public class FollowRepository {

    public Followers followUser(Followers follower){
        String query = "INSERT INTO followersDetails (follower_id, followee_id) values (?,?) ";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, follower.get_follower_id());
            stmt.setInt(2, follower.get_followee_id());
            stmt.executeUpdate();
            System.out.println("successfully created!! ");
            return follower;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }




    }
    public String  unfollowUser(Followers follower) {
        String query = "DELETE FROM followersDetails WHERE follower_id = ? AND followee_id = ?";
        try (Connection connection =DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, follower.get_follower_id());
            stmt.setInt(2, follower.get_followee_id());
            stmt.executeUpdate();
            System.out.println("Successfully unfollowed the user.");
            return "true";
        } catch (SQLException e) {
            e.printStackTrace();
            return "false";
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
        String query = "SELECT u.name,u.id FROM users u JOIN followersDetails f ON u.id = f.follower_id WHERE f.followee_id = ?" ;
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



    public String update(String message) {
        /* update to the database for each followers*/
        return null;
    }





}
