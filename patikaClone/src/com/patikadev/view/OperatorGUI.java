package com.patikadev.view;

import com.patikadev.helper.*;
import com.patikadev.model.Operator;

import javax.swing.*;

public class OperatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane tabbedPane_operator;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_exit;
    private JPanel pnl_user_list;

    private Operator operator;
    public OperatorGUI(Operator operator){
        this.operator=operator;

        add(wrapper);
        setSize(1000,500);
        setLocation(Helper.screenCenter("x",getSize()),Helper.screenCenter("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText("Welcome to app: "+operator.getFirstName().toUpperCase()+" "+operator.getLastName().toUpperCase());

    }
}
