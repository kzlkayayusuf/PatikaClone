package com.patikadev.view;

import com.patikadev.helper.*;
import com.patikadev.model.Operator;
import com.patikadev.model.User;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class OperatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane tabbedPane_operator;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_exit;
    private JPanel pnl_user_list;
    private JScrollPane scrl_user_list;
    private JTable tbl_user_list;
    private JPanel pnl_user_form;
    private JTextField tfld_user_name;
    private JTextField tfld_user_surname;
    private JTextField tfld_username;
    private JPasswordField passwordField;
    private JComboBox cmb_user_type;
    private JButton btn_user_add;
    private JTextField tfld_user_id;
    private JButton btn_user_delete;
    private JTextField tfld_search_user_name;
    private JTextField tfld_search_username;
    private JComboBox cmb_search_user_type;
    private JButton btn_search_user;
    private DefaultTableModel mdl_user_list;
    private Object[] row_user_list;


    private final Operator operator;
    public OperatorGUI(Operator operator){
        this.operator=operator;

        add(wrapper);
        setSize(1000,550);
        setLocation(Helper.screenCenter("x",getSize()),Helper.screenCenter("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText("Welcome to app: "+operator.getFirstName().toUpperCase()+" "+operator.getLastName().toUpperCase());

        // UserList

        mdl_user_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_user_list = {
                "ID", "Surname", "Name", "Username", "Password", "Membership Type"
        };
        mdl_user_list.setColumnIdentifiers(col_user_list);
        row_user_list = new Object[col_user_list.length];

        loadUserModel();

        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false);

        tbl_user_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selectedId = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString();
                tfld_user_id.setText(selectedId);
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        });

        tbl_user_list.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int user_id = Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString());
                String lastName = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 1).toString();
                String firstName = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 2).toString();
                String userName = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 3).toString();
                String password = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 4).toString();
                String userType = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 5).toString();

                if (User.update(user_id, lastName, firstName, userName, password, userType)) {
                    Helper.showMessage("updated");
                }
                loadUserModel();
            }
        });

        // ## UserList

        btn_user_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(tfld_user_name) || Helper.isFieldEmpty(tfld_user_surname) || Helper.isFieldEmpty(tfld_username) || Helper.isValidPassword(passwordField)) {
                Helper.showMessage("fill");
            } else {

                String firstName = tfld_user_name.getText();
                String lastName = tfld_user_surname.getText();
                String userName = tfld_username.getText();
                String password = Arrays.toString(passwordField.getPassword());
                String userType = Objects.requireNonNull(cmb_user_type.getSelectedItem()).toString();
                if (User.addDb(lastName, firstName, userName, password, userType)) {
                    loadUserModel();
                    Helper.showMessage("success");
                    tfld_user_name.setText(null);
                    tfld_user_surname.setText(null);
                    tfld_username.setText(null);
                    passwordField.setText(null);
                }

                //System.out.println("Added to DB");
            }
        });

        btn_user_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(tfld_user_id)) {
                Helper.showMessage("fill");
            } else {
                if(Helper.confirm("sure")){
                    int userId = Integer.parseInt(tfld_user_id.getText());
                    if (User.delete(userId)) {
                        Helper.showMessage("deleted");
                        loadUserModel();
                        tfld_user_id.setText(null);
                    } else {
                        Helper.showMessage("error");
                    }
                }
            }
        });

        btn_search_user.addActionListener(e -> {
            String name = "", surname = "";
            if (!tfld_search_user_name.getText().isEmpty()) {
                name = tfld_search_user_name.getText().split(" ")[0];
                if (tfld_search_user_name.getText().split(" ").length > 1)
                    surname = tfld_search_user_name.getText().split(" ")[1];
            }

            String userName = tfld_search_username.getText();
            String type = Objects.requireNonNull(cmb_search_user_type.getSelectedItem()).toString();
            String query = User.searchQuery(userName, name, surname, type);

            loadUserModel(User.searchUserList(query));
        });

        btn_exit.addActionListener(e -> {
            dispose();
        });
    }

    private void loadUserModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);

        for (User user : User.getList()) {
            int i = 0;
            row_user_list[i++] = user.getId();
            row_user_list[i++] = user.getLastName();
            row_user_list[i++] = user.getFirstName();
            row_user_list[i++] = user.getUserName();
            row_user_list[i++] = user.getUserPassword();
            row_user_list[i] = user.getUserType();

            mdl_user_list.addRow(row_user_list);

        }
    }

    private void loadUserModel(ArrayList<User> users) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);

        for (User user : users) {
            int i = 0;
            row_user_list[i++] = user.getId();
            row_user_list[i++] = user.getLastName();
            row_user_list[i++] = user.getFirstName();
            row_user_list[i++] = user.getUserName();
            row_user_list[i++] = user.getUserPassword();
            row_user_list[i] = user.getUserType();

            mdl_user_list.addRow(row_user_list);

        }
    }
}
