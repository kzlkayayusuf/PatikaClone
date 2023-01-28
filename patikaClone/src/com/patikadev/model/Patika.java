package com.patikadev.model;

import com.patikadev.helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Patika {
    private int id;
    private String name;

    public Patika(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ArrayList<Patika> getList(){
        ArrayList<Patika> patikas=new ArrayList<>();
        Patika obj;

        try {
            Statement statement= DBConnector.getInstance().createStatement();
            ResultSet resultSet=statement.executeQuery("SELECT * FROM lessons");
            while (resultSet.next()){
                obj=new Patika(resultSet.getInt("id"),resultSet.getString("name"));
                patikas.add(obj);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return patikas;
    }

    public static boolean add(String name){
        String query="INSERT INTO lessons (name) VALUES (?)";
        try {
            PreparedStatement ps= DBConnector.getInstance().prepareStatement(query);
            ps.setString(1,name);
            return ps.executeUpdate() !=-1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static boolean update(int id,String name){
        String query= "UPDATE lessons SET name=? WHERE id=?";
        try {
            PreparedStatement ps=DBConnector.getInstance().prepareStatement(query);
            ps.setString(1,name);
            ps.setInt(2,id);
            return ps.executeUpdate()!=-1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static Patika getFetch(int id){
        Patika obj=null;
        String query="SELECT * FROM lessons WHERE id = ?";

        try {
            PreparedStatement ps=DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1,id);
            ResultSet resultSet=ps.executeQuery();
            if (resultSet.next()){
                obj=new Patika(resultSet.getInt("id"),resultSet.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }


    public static boolean delete(int id){
        String query="DELETE FROM lessons WHERE id=?";
        ArrayList<Course> courses=Course.getList();
        for (Course c : courses) {
            if (c.getPatika().getId() == id) {
                Course.delete(c.getId());
            }
        }
        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1,id);
            return ps.executeUpdate()!=-1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

}
