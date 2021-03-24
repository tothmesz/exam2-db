package com.codecool.database;

import java.sql.*;

public class RadioCharts {
    String url;
    String user;
    String password;

    public RadioCharts(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public String getMostPlayedSong() {
        String query = "SELECT song FROM music_broadcast GROUP BY song ORDER BY SUM(times_aired) DESC LIMIT 2";
        return getResult(query, "song");
    }

    public String getMostActiveArtist() {
        String query = "SELECT COUNT(DISTINCT song) AS number_of_songs, artist FROM music_broadcast GROUP BY artist ORDER BY number_of_songs DESC LIMIT 1";
        return getResult(query, "artist");
    }

    private String getResult(String query, String column) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                return (rs.getString(column));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}
