package com.surveymanagement.responsequestion.infrastructure;

import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.surveymanagement.responsequestion.domain.entity.ResponseQuestion;
import com.surveymanagement.responsequestion.domain.service.ResponseQuestionService;

public class ResponseQuestionRepository implements ResponseQuestionService {
    private Connection connection;

    public ResponseQuestionRepository() {
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
    public void createResposeQuestion(ResponseQuestion responseQuestion) {
        try {

            if(responseQuestion.getResponseId()==0){
                
            
                String query = "INSERT INTO response_question (response_id,subresponses_id,responsetext) VALUES (NULL,?,?)";
                PreparedStatement ps = connection.prepareStatement(query);
                
                ps.setInt(1, responseQuestion.getSubresponseId());
                ps.setString(2, responseQuestion.getResponseText());
            

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("ResponseQuestion added successfully!");
                } else {
                    System.out.println("ResponseQuestion addition failed!");
                }

            } else if(responseQuestion.getSubresponseId()==0){
                String query = "INSERT INTO response_question (response_id,subresponses_id,responsetext) VALUES (?,NULL,?)";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, responseQuestion.getResponseId());
                ps.setString(2, responseQuestion.getResponseText());
            

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("ResponseQuestion added successfully!");
                } else {
                    System.out.println("ResponseQuestion addition failed!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResponseQuestion deleteResponseQuestion(int resposeQuestioId) {
        ResponseQuestion responseOption = null;
        String selectQuery = "SELECT * FROM response_question WHERE id = ?";
        String deleteQuery = "DELETE FROM response_question WHERE id = ?";

        try (PreparedStatement selectPs = connection.prepareStatement(selectQuery);
            PreparedStatement deletePs = connection.prepareStatement(deleteQuery)) {

            // First, fetch the role
            selectPs.setInt(1, resposeQuestioId);
            try (ResultSet rs = selectPs.executeQuery()) {
                if (rs.next()) {
                    responseOption = new ResponseQuestion(
                        rs.getInt("id"),
                        rs.getInt("response_id"),
                        rs.getInt("subresponses_id"),
                        rs.getString("responsetext")
                    );
                }
            }

            if (responseOption != null) {
                deletePs.setInt(1, resposeQuestioId);
                int rowsAffected = deletePs.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("ResponseQuestion deleted successfully!");
                    return responseOption;
                }
            }

            System.out.println("ResponseQuestion deletion failed. Role not found.");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ResponseQuestion> findAllResponseQuestion() {
        List<ResponseQuestion> responseQuestions = new ArrayList<>();
        String query = "SELECT * FROM response_question";
        
        try (PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ResponseQuestion responseQuestion = new ResponseQuestion(
                    rs.getInt("id"),
                    rs.getInt("response_id"),
                    rs.getInt("subresponses_id"),
                    rs.getString("responsetext")
                );
                responseQuestions.add(responseQuestion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return responseQuestions;
    }    
}