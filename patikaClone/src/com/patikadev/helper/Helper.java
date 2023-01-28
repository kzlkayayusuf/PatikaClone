package com.patikadev.helper;

import javax.swing.*;
import java.awt.*;

public class Helper {
    public static void setLayout() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                         UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
                //System.out.println("Nimbus teması bulundu!");
                break;
            }
        }
    }

    public static int screenCenter(String axis, Dimension size) {
        return switch (axis) {
            case "x" -> (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
            case "y" -> (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
            default -> 0;
        };
    }

    public static boolean isFieldEmpty(JTextField field){
        return field.getText().trim().isEmpty();
    }

    public static boolean isValidPassword(JPasswordField passwordField){
        return passwordField.getPassword().length<5;
    }

    public static void showMessage(String message){
        optionPage();
        String msg,title;

        switch (message) {
            case "fill" -> {
                msg = "Please fill all field";
                title = "ERROR!";
            }
            case "added" -> {
                msg = "Successfully added to database";
                title = "SUCCESS";
            }
            case "deleted" -> {
                msg = "Successfully deleted from database";
                title = "SUCCESS";
            }
            case "updated" -> {
                msg = "Successfully updated";
                title = "SUCCESS";
            }
            case "wrongUserType" -> {
                msg = "User Type Not Match 'OPERATOR,INSTRUCTOR or STUDENT'";
                title = "ERROR!";
            }
            case "error" -> {
                msg = "Not added to database";
                title = "ERROR!";
            }
            case "duplicatedUserName" ->{
                msg="That userName exists, Please re-enter";
                title="ERROR!";
            }
            default -> {
                msg = message;
                title = "MESSAGE";
            }
        }

        JOptionPane.showMessageDialog(null,msg,title,JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(String str) {
        optionPage();
        String msg;
        switch (str){
            case "sure" ->{
                msg="Are you sure?";
            }
            default -> msg=str;
        }

        return JOptionPane.showConfirmDialog(null,msg,"your final decision?",JOptionPane.YES_NO_OPTION) == 0;
    }

    public static void optionPage(){
        UIManager.put("OptionPane.okButtonText" , "Got It");
        UIManager.put("OptionPane.yesButtonText" , "Accept");
        UIManager.put("OptionPane.noButtonText" , "Deny");

    }
}
