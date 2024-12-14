package com.myapp.repository;

import com.myapp.model.User;
import com.myapp.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
public class UserRepository {
    public List<User> findAll(int userid) {

        System.out.println("find all call method invoked");
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE id NOT IN (SELECT followee_id FROM followersdetails WHERE follower_id = ?) and id != ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1,userid);
            stmt.setInt(2,userid);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return users;
    }


    private User getUserByName(String name){
        String query = "SELECT * from users where name = ?";
        User user = null;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1,name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                user = new User(rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return user;
    }
    public User save(User user) {

        User user1 = getUserByName(user.getName());

        if(user1 ==null)
        {
            String query = "INSERT INTO users (name) VALUES (?)";

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, user.getName());
                stmt.executeUpdate();
                user1 = getUserByName(user.getName());
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


        return user1;
    }










}

