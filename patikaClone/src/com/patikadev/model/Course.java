package com.patikadev.model;

import com.patikadev.helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Course {
    private int id;
    private int user_id;
    private int patika_id;
    private String name;
    private String language;
    private Patika patika;
    private User instructor;

    public Course(int id, int user_id, int patika_id, String name, String language) {
        this.id = id;
        this.user_id = user_id;
        this.patika_id = patika_id;
        this.name = name;
        this.language = language;
        this.patika = Patika.getFetch(patika_id);
        this.instructor = User.getFetch(user_id);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPatika_id() {
        return patika_id;
    }

    public void setPatika_id(int patika_id) {
        this.patika_id = patika_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Patika getPatika() {
        return patika;
    }

    public void setPatika(Patika patika) {
        this.patika = patika;
    }

    public User getInstructor() {
        return instructor;
    }

    public void setInstructor(User instructor) {
        this.instructor = instructor;
    }

    public static ArrayList<Course> getList() {
        ArrayList<Course> courses = new ArrayList<>();
        Course obj;
        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM courses ");
            while (rs.next()) {
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int patika_id = rs.getInt("patika_id");
                String name = rs.getString("name");
                String language = rs.getString("language");
                obj = new Course(id, user_id, patika_id, name, language);
                courses.add(obj);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return courses;
    }

    public static boolean add(int user_id, int patika_id, String name, String language) {
        String query = "INSERT INTO courses (name,user_id,patika_id,language) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, user_id);
            ps.setInt(3, patika_id);
            ps.setString(4, language);
            return ps.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static ArrayList<Course> getListByUserId(int userId) {
        ArrayList<Course> courses = new ArrayList<>();
        Course obj;
        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM courses WHERE user_id="+userId);
            while (rs.next()) {
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int patika_id = rs.getInt("patika_id");
                String name = rs.getString("name");
                String language = rs.getString("language");
                obj = new Course(id, user_id, patika_id, name, language);
                courses.add(obj);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return courses;
    }

    public static boolean delete(int id){
        String query="DELETE FROM courses WHERE id=?";
        try {
            PreparedStatement ps=DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1,id);
            return ps.executeUpdate() !=-1 ;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static boolean update(int id,String name,int user_id,int patika_id,String language){
        String query= "UPDATE courses SET name=?,user_id=?,patika_id=?,language=? WHERE id=?";
        try {
            PreparedStatement ps=DBConnector.getInstance().prepareStatement(query);
            ps.setString(1,name);
            ps.setInt(2,user_id);
            ps.setInt(3,patika_id);
            ps.setString(4,language);
            ps.setInt(5,id);
            return ps.executeUpdate()!=-1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }
}
