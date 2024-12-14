package com.myapp.repository;

import com.myapp.model.Feed;
import com.myapp.model.Follower;

import com.myapp.model.User;
import com.myapp.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

public class PostRepository {

    public static List<Feed> getPost(int userid){
        List<Feed>  feeds = new ArrayList<>();
        String query = """
            SELECT p.content as content, p.timestamp as timestamp, u.name as name
            FROM postsDetails p
            JOIN followersDetails f ON p.user_id = f.followee_id
            JOIN users u ON u.id = p.user_id
            WHERE f.follower_id = ?
            ORDER BY p.timestamp DESC
        """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             stmt.setInt(1,userid);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                feeds.add(new Feed((String) rs.getString("timestamp"), (String) rs.getString("content"), (String) rs.getString("name")));

            }
            System.out.println("successfully get req impl!! ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(feeds);
        return feeds;
    }
    public Feed addPost(Feed feed){
        System.out.println("Post message is being called repo");
        String query = "INSERT INTO postsDetails (user_id, content) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, feed.getUserId());
            stmt.setString(2, feed.getContent());
            stmt.executeUpdate();
            System.out.println("successfully post got created!! ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feed;
    }
}
