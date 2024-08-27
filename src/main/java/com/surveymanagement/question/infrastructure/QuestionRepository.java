package com.surveymanagement.question.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import com.surveymanagement.question.domain.entity.Question;
import com.surveymanagement.question.domain.service.QuestionService;

public class QuestionRepository implements QuestionService {
    private Connection connection;
        public QuestionRepository(){
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
        public void createQuestion(Question question) {
            try {
            String query = "INSERT INTO questions (chapter_id, question_number, response_type, comment_question, question_text) VALUES (?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, question.getChapter_id());
            ps.setString(2, question.getQuestion_number());
            ps.setString(3, question.getResponse_type());
            ps.setString(4, question.getComment_question());
            ps.setString(5, question.getQuestion_text());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }
        @Override
        public void updateQuestion(Question question) {
            try {
                String query = "UPDATE questions SET chapter_id = ?, question_number = ?, response_type = ?, comment_question = ?, question_text = ? WHERE id = ?";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, question.getChapter_id());
                ps.setString(2, question.getQuestion_number());
                ps.setString(3, question.getResponse_type());
                ps.setString(4, question.getComment_question());
                ps.setString(5, question.getQuestion_text());
                ps.setInt(6, question.getId());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }  
        }
        @Override
        public Question deleteQuestion(int id) {
            String query = "DELETE FROM questions WHERE id = ?";
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
        public Optional<Question> findQuestionByCode(int id) {
            try {
                String query = "SELECT id, chapter_id, created_at, updated_at, question_number, response_type, comment_question, question_text FROM questions WHERE (id = ?)";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Question question = new Question(
                                rs.getInt("id"),
                                rs.getInt("chapter_id"),
                                rs.getString("created_at"),
                                rs.getString("updated_at"),
                                rs.getString("question_number"),
                                rs.getString("response_type"),
                                rs.getString("comment_question"),
                                rs.getString("question_text"));
                        return Optional.of(question);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty(); 
        }
        @Override
        public List<Question> findAllQuestion() {
            List<Question> questions = new ArrayList<>();
            String query = "SELECT id, chapter_id, created_at, updated_at, question_number, response_type, comment_question, question_text FROM questions";
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Question question = new Question(
                            rs.getInt("id"),
                            rs.getInt("chapter_id"),
                            rs.getString("created_at"),
                            rs.getString("updated_at"),
                            rs.getString("question_number"),
                            rs.getString("response_type"),
                            rs.getString("comment_question"),
                            rs.getString("question_text"));
                            questions.add(question);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return questions;
        }
        @Override
        public List<Question> findQuestionByChapter(int chapter_id) {
            List<Question> questions = new ArrayList<>();
            String query = "SELECT id, chapter_id, created_at, updated_at, question_number, response_type, comment_question, question_text  FROM Questions where chapter_id = ?";
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, chapter_id);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Question question = new Question(
                            rs.getInt("id"),
                            rs.getInt("chapter_id"),
                            rs.getString("created_at"),
                            rs.getString("updated_at"),
                            rs.getString("question_number"),
                            rs.getString("response_type"),
                            rs.getString("comment_question"),
                            rs.getString("question_text"));
                            questions.add(question);
                    }
                        
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return questions; }
}
