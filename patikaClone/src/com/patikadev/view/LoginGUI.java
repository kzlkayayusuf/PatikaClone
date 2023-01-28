package com.patikadev.view;

import com.patikadev.helper.Config;
import com.patikadev.helper.Helper;
import com.patikadev.model.Operator;
import com.patikadev.model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame{
    private JPanel wrapper;
    private JPanel wTop;
    private JPanel wBottom;
    private JTextField tfld_username;
    private JPasswordField passwordField;
    private JButton btn_login;

    public LoginGUI(){
        add(wrapper);
        setSize(420,420);
        setLocation(Helper.screenCenter("x",getSize()),Helper.screenCenter("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        btn_login.addActionListener(e -> {
            if (Helper.isFieldEmpty(tfld_username) || Helper.isFieldEmpty(passwordField)) {
                Helper.showMessage("fill");
            } else {
                User user=User.getFetch(tfld_username.getText(), String.valueOf(passwordField.getPassword()));
                if (user == null) {
                    Helper.showMessage("userNotExists");
                } else {
                    switch (user.getUserType()) {
                        case "OPERATOR" -> {
                            OperatorGUI operatorGUI=new OperatorGUI((Operator) user);
                        }
                        case "INSTRUCTOR" -> {
                            InstructorGUI instructorGUI=new InstructorGUI();
                        }
                        case "STUDENT"->{
                            StudentGUI studentGUI=new StudentGUI();
                        }
                    }
                    dispose();
                }
            }
        });
    }
}
