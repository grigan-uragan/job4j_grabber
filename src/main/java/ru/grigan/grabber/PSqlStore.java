package ru.grigan.grabber;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public class PSqlStore implements Store {
    private Connection connection;

    public PSqlStore(Properties properties) {
        try {
            Class.forName(properties.getProperty("driver-class-name"));
            connection = DriverManager.getConnection(
                    properties.getProperty("url"),
                    properties.getProperty("user"),
                    properties.getProperty("password")
            );
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // this constructor for rollBack connection only
    public PSqlStore(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement statement = connection.prepareStatement(
                "insert into posts (post_name, post_text, post_link, post_date)"
                        + " values ((?),(?),(?),(?)) on conflict (post_link) do nothing"
        )) {
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getDescription());
            statement.setString(3, post.getUrl());
            statement.setString(4, post.getDateCreation().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> results = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from posts order by post_id", Statement.RETURN_GENERATED_KEYS
        )) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                results.add(new Post(
                        rs.getInt("post_id"),
                        rs.getString("post_link"),
                        rs.getString("post_name"),
                        rs.getString("post_text"),
                        new SimpleDateFormat(
                                "EEE MMM dd HH:mm:ss zzz yyyy",
                                Locale.US).parse(rs.getString("post_date")))
                );
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public Post findById(int id) {
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from posts where post_id = (?)", Statement.RETURN_GENERATED_KEYS
        )) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Post(
                        resultSet.getInt("post_id"),
                        resultSet.getString("post_link"),
                        resultSet.getString("post_name"),
                        resultSet.getString("post_text"),
                        new SimpleDateFormat(
                                "EEE MMM dd HH:mm:ss zzz yyyy",
                                Locale.US).parse(resultSet.getString("post_date"))
                );
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}
