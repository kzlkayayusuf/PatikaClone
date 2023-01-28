package com.patikadev.model;

import com.patikadev.helper.DBConnector;
import com.patikadev.helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User {
    private int id;
    private String lastName;
    private String firstName;
    private String userName;
    private String userPassword;
    private String userType;

    public User() {
    }

    public User(int id, String lastName, String firstName, String userName, String userPassword, String userType) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userType = userType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public static ArrayList<User> getList(){
        ArrayList<User> userList=new ArrayList<>();
        String query="SELECT * FROM users";
        User obj;
        try {
            Statement statement= DBConnector.getInstance().createStatement();
            ResultSet resultSet=statement.executeQuery(query);
            while (resultSet.next()){
                obj=new User();
                obj.setId(resultSet.getInt("id"));
                obj.setFirstName(resultSet.getString("firstName"));
                obj.setLastName(resultSet.getString("lastName"));
                obj.setUserName(resultSet.getString("userName"));
                obj.setUserType(resultSet.getString("userType"));
                obj.setUserPassword(resultSet.getString("userPassword"));
                userList.add(obj);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public static boolean addDb(String lastName,String firstName,String userName,String userPassword,String userType){
        String query="INSERT INTO users (lastName,firstName,userName,userPassword,userType) VALUES (?,?,?,?,?)";
        User findUser= User.getFetch(userName);
        if(findUser!=null){
            Helper.showMessage("duplicatedUserName");
            return false;
        }
        try {
            PreparedStatement ps= DBConnector.getInstance().prepareStatement(query);
            ps.setString(1,lastName);
            ps.setString(2,firstName);
            ps.setString(3,userName);
            ps.setString(4,userPassword);
            ps.setString(5,userType);
            int response=ps.executeUpdate();
            if(response ==-1){
                Helper.showMessage("error");
            }
            return response !=-1;

        } catch (SQLException e) {
            System.out.println( e.getMessage());
        }
        return true;
    }

    public static User getFetch(String userName){
        User user=null;
        String query="SELECT * FROM users WHERE userName LIKE ?";

        try {
            PreparedStatement ps= DBConnector.getInstance().prepareStatement(query);
            ps.setString(1,userName);
            ResultSet rs= ps.executeQuery();
            if (rs.next()){
                user=new User();
                user.setId(rs.getInt("id"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setUserName(rs.getString("userName"));
                user.setUserType(rs.getString("userType"));
                user.setUserPassword(rs.getString("userPassword"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    public static boolean delete(int id){
        String query="DELETE FROM users WHERE id=?";
        try {
            PreparedStatement ps=DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1,id);

            return ps.executeUpdate() !=-1 ;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static boolean update(int id,String lastName,String firstName,String userName,String userPassword,String userType){
        String query="UPDATE users SET lastName=?,firstName=?,userName=?,userPassword=?,userType=? WHERE id=?";
        User findUser= User.getFetch(userName);
        List<String> types= Arrays.asList("OPERATOR","INSTRUCTOR","STUDENT","İNSTRUCTOR");
        int result=0;
        for (String type:types) {
            if(type.equals(userType.toUpperCase())){
                userType= userType.equals("instructor") ? "INSTRUCTOR" : userType;
                result++;
            }
        }
        if(result==0){
            Helper.showMessage("wrongUserType");
            return false;
        }
        if(findUser!=null && findUser.getId() !=id){
            Helper.showMessage("duplicatedUserName");
            return false;
        }
        try {
            PreparedStatement ps=DBConnector.getInstance().prepareStatement(query);
            ps.setString(1,lastName);
            ps.setString(2,firstName);
            ps.setString(3,userName);
            ps.setString(4,userPassword);
            ps.setString(5,userType);
            ps.setInt(6,id);
            return ps.executeUpdate() !=-1 ;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static ArrayList<User> searchUserList(String query){
        ArrayList<User> userList=new ArrayList<>();
        User obj;
        try {
            Statement statement= DBConnector.getInstance().createStatement();
            ResultSet resultSet=statement.executeQuery(query);
            while (resultSet.next()){
                obj=new User();
                obj.setId(resultSet.getInt("id"));
                obj.setFirstName(resultSet.getString("firstName"));
                obj.setLastName(resultSet.getString("lastName"));
                obj.setUserName(resultSet.getString("userName"));
                obj.setUserType(resultSet.getString("userType"));
                obj.setUserPassword(resultSet.getString("userPassword"));
                userList.add(obj);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public static String searchQuery(String userName,String firstName,String lastName,String userType){
        String query="SELECT * FROM users WHERE userName LIKE '%{{userName}}%' AND firstName LIKE '%{{firstName}}%' AND lastName LIKE '%{{lastName}}%' AND userType LIKE '%{{userType}}%'";
        query=query.replace("{{userName}}",userName);
        query=query.replace("{{firstName}}",firstName);
        query=query.replace("{{lastName}}",lastName);
        userType= userType.equals("instructor") ? "ınstructor" : userType;
        query=query.replace("{{userType}}",userType);

        return query;
    }
}
