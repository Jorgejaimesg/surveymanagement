package com.surveymanagement.chapter.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import com.surveymanagement.chapter.domain.entity.Chapter;
import com.surveymanagement.chapter.domain.service.ChapterService;

public class ChapterRepository implements ChapterService{
    private Connection connection;
        public ChapterRepository(){
        try {
            Properties props = new Properties();
            props.load(getClass().getClassLoader().getResourceAsStream("configdb.properties"));
            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String password = props.getProperty("password");
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void createChapter(Chapter chapter) {
        try {
            String query = "INSERT INTO chapter (survey_id, chapter_number, chapter_title) VALUES (?,?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, chapter.getSurvey_id());
            ps.setString(2, chapter.getChapter_number());
            ps.setString(3, chapter.getChapter_title());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateChapter(Chapter chapter) {
        String query = "UPDATE chapter SET survey_id = ?, chapter_number = ?, chapter_title = ? WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, chapter.getSurvey_id());
            ps.setString(2, chapter.getChapter_number());
            ps.setString(3, chapter.getChapter_title());
            ps.setInt(4, chapter.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }    
    
    }

    @Override
    public Chapter deleteChapter(int id) {
        String query = "DELETE FROM chapter WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Chapter> findChapterByName(String name, int survey_id) {
        String query = "SELECT id, created_at, survey_id, updated_at, chapter_number, chapter_title FROM chapter WHERE (name = ? && survey_id = ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, survey_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Chapter chapter = new Chapter(
                            rs.getInt("id"),
                            rs.getString("created_at"),
                            rs.getInt("survey_id"),
                            rs.getString("updated_at"),
                            rs.getString("chapter_number"),
                            rs.getString("chapter_title"));
                    return Optional.of(chapter);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Chapter> findChapterByCode(int id) {
        String query = "SELECT id, created_at, survey_id, updated_at, chapter_number, chapter_title FROM chapter WHERE (id = ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Chapter chapter = new Chapter(
                            rs.getInt("id"),
                            rs.getString("created_at"),
                            rs.getInt("survey_id"),
                            rs.getString("updated_at"),
                            rs.getString("chapter_number"),
                            rs.getString("chapter_title"));
                    return Optional.of(chapter);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Chapter> findAllChapter() {
    List<Chapter> chapters = new ArrayList<>();
        String query = "SELECT id, created_at, survey_id, updated_at, chapter_number, chapter_title FROM chapter";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Chapter chapter = new Chapter(
                            rs.getInt("id"),
                            rs.getString("created_at"),
                            rs.getInt("survey_id"),
                            rs.getString("updated_at"),
                            rs.getString("chapter_number"),
                            rs.getString("chapter_title"));
                        chapters.add(chapter);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chapters;
    }

    @Override
    public List<Chapter> findChapterBySurvey(int survey_id) {
        List<Chapter> chapters = new ArrayList<>();
        String query = "SELECT id, created_at, survey_id, updated_at, chapter_number, chapter_title FROM Chapter where survey_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, survey_id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Chapter chapter = new Chapter(
                        rs.getInt("id"),
                            rs.getString("created_at"),
                            survey_id,
                            rs.getString("updated_at"),
                            rs.getString("chapter_number"),
                            rs.getString("chapter_title"));
                            chapters.add(chapter);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chapters;
    }

}
