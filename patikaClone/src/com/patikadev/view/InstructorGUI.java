package com.patikadev.view;

import com.patikadev.helper.Config;
import com.patikadev.helper.Helper;

import javax.swing.*;

public class InstructorGUI extends JFrame {
    private JPanel wrapper;

    public InstructorGUI(){
        add(wrapper);
        setSize(420,420);
        setLocation(Helper.screenCenter("x",getSize()),Helper.screenCenter("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
    }
}
