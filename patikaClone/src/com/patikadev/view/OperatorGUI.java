package com.patikadev.view;

import com.patikadev.helper.*;
import com.patikadev.model.Course;
import com.patikadev.model.Operator;
import com.patikadev.model.Patika;
import com.patikadev.model.User;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
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
    private JPanel pnl_patika_list;
    private JScrollPane scrl_patika_list;
    private JTable tbl_patika_list;
    private JPanel pnl_patika_add;
    private JTextField tfld_patika_name;
    private JButton btn_patika_add;
    private JPanel pnl_course_list;
    private JPanel pnl_user_top;
    private JScrollPane scrl_course_list;
    private JTable tbl_course_list;
    private JPanel pnl_course_add;
    private JTextField tfld_course_name;
    private JTextField tfld_course_language;
    private JComboBox cmb_course_patikas;
    private JComboBox cmb_course_instructor;
    private JButton btn_course_add;
    private JButton btn_course_update;
    private JTextField tfld_course_id;
    private JButton btn_course_delete;
    private DefaultTableModel mdl_user_list;
    private Object[] row_user_list;
    private DefaultTableModel mdl_patika_list;
    private Object[] row_patika_list;
    private JPopupMenu patikaMenu;
    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;


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
                loadCourseModel();
                loadInstructorCmb();
            }
        });

        // ## UserList

        // patikas
        patikaMenu=new JPopupMenu();
        JMenuItem updateMenu=new JMenuItem("Update");
        JMenuItem deleteMenu=new JMenuItem("Delete");
        patikaMenu.add(updateMenu);
        patikaMenu.add(deleteMenu);

        updateMenu.addActionListener(e -> {
            int selectId=Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(),0).toString());
            UpdatePatikaGUI patikaGUI= new UpdatePatikaGUI(Patika.getFetch(selectId));
            patikaGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadPatikaModel();
                    loadPatikaCmb();
                    loadCourseModel();
                    super.windowClosed(e);
                }
            });
        });

        deleteMenu.addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectId=Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(),0).toString());
                if (Patika.delete(selectId)) {
                    Helper.showMessage("deleted");
                    loadPatikaModel();
                    loadPatikaCmb();
                    loadCourseModel();
                }else{
                    Helper.showMessage("error");
                }
            }
        });


        mdl_patika_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Object[] col_lesson_list = {"ID", "Patika Name"};
        mdl_patika_list.setColumnIdentifiers(col_lesson_list);
        row_patika_list = new Object[col_lesson_list.length];
        loadPatikaModel();

        tbl_patika_list.setModel(mdl_patika_list);
        tbl_patika_list.setComponentPopupMenu(patikaMenu);
        tbl_patika_list.getTableHeader().setReorderingAllowed(false);
        tbl_patika_list.getColumnModel().getColumn(0).setMaxWidth(100);

        tbl_patika_list.addMouseListener(new MouseAdapter() {
                                             @Override
                                             public void mousePressed(MouseEvent e) {
                                                 Point point=e.getPoint();
                                                 int selectedRow= tbl_patika_list.rowAtPoint(point);
                                                 tbl_patika_list.setRowSelectionInterval(selectedRow,selectedRow);
                                                 //System.out.println(e.getPoint().toString());
                                                 super.mousePressed(e);
                                             }
                                         }
        );

        // ## patikas

        // course list
        mdl_course_list=new DefaultTableModel();
        Object[] col_course_list={"ID","Course Name","Programming Language","Patika","Instructor"};
        mdl_course_list.setColumnIdentifiers(col_course_list);
        row_course_list=new Object[col_course_list.length];
        loadCourseModel();
        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(80);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);

        tbl_course_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                tfld_course_id.setText(tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(), 0).toString());
                tfld_course_name.setText(tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(), 1).toString());
                tfld_course_language.setText(tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(), 2).toString());
                cmb_course_patikas.setSelectedItem(tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(), 3));
                cmb_course_instructor.setSelectedItem(tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(), 4));

            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        });


        loadPatikaCmb();
        loadInstructorCmb();
        // ## course list

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
                    loadInstructorCmb();
                    Helper.showMessage("added");
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
                        loadInstructorCmb();
                        loadCourseModel();
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
            LoginGUI loginGUI=new LoginGUI();
        });

        btn_patika_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(tfld_patika_name)) {
                Helper.showMessage("fill");
            } else {
                if (Patika.add(tfld_patika_name.getText())) {
                    Helper.showMessage("added");
                    loadPatikaModel();
                    loadPatikaCmb();
                    tfld_patika_name.setText(null);
                } else {
                    Helper.showMessage("error");
                }
            }
        });

        btn_course_add.addActionListener(e -> {
            Item patikaItem= (Item) cmb_course_patikas.getSelectedItem();
            Item userItem= (Item) cmb_course_instructor.getSelectedItem();
            if (Helper.isFieldEmpty(tfld_course_name) || Helper.isFieldEmpty(tfld_course_language)) {
                Helper.showMessage("fill");
            }else{
                if (Course.add(userItem.getKey(), patikaItem.getKey(), tfld_course_name.getText(), tfld_course_language.getText())) {
                    Helper.showMessage("added");
                    loadCourseModel();
                    tfld_course_language.setText(null);
                    tfld_course_name.setText(null);
                } else {
                    Helper.showMessage("error");
                }
            }
        });

        btn_course_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(tfld_course_id)) {
                Helper.showMessage("fill");
            } else {
                if(Helper.confirm("sure")){
                    int courseId = Integer.parseInt(tfld_course_id.getText());
                    if (Course.delete(courseId)) {
                        Helper.showMessage("deleted");
                        loadCourseModel();
                        tfld_course_id.setText(null);
                        tfld_course_language.setText(null);
                        tfld_course_name.setText(null);
                    } else {
                        Helper.showMessage("error");
                    }
                }
            }
        });

        btn_course_update.addActionListener(e -> {
            Item patikaItem= (Item) cmb_course_patikas.getSelectedItem();
            Item userItem= (Item) cmb_course_instructor.getSelectedItem();
            if(Helper.isFieldEmpty(tfld_course_id) && Helper.isFieldEmpty(tfld_course_name) && Helper.isFieldEmpty(tfld_course_language)){
                Helper.showMessage("fill");
            }else{
                if (Course.update(Integer.parseInt(tfld_course_id.getText()), tfld_course_name.getText(),userItem.getKey(),patikaItem.getKey(),tfld_course_language.getText())) {
                    Helper.showMessage("updated");
                    loadCourseModel();
                    tfld_course_language.setText(null);
                    tfld_course_name.setText(null);
                    tfld_course_id.setText(null);
                }else {
                    Helper.showMessage("error");
                }
            }
        });
    }

    private void loadUserModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);

        int i;
        for (User user : User.getList()) {
            i = 0;
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

        int i;
        for (User user : users) {
             i = 0;
            row_user_list[i++] = user.getId();
            row_user_list[i++] = user.getLastName();
            row_user_list[i++] = user.getFirstName();
            row_user_list[i++] = user.getUserName();
            row_user_list[i++] = user.getUserPassword();
            row_user_list[i] = user.getUserType();

            mdl_user_list.addRow(row_user_list);

        }
    }

    private void loadCourseModel() {
        DefaultTableModel clearModel=(DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0);

        int i;
        for (Course obj : Course.getList()) {
            i=0;
            row_course_list[i++]= obj.getId();
            row_course_list[i++]= obj.getName();
            row_course_list[i++]= obj.getLanguage();
            row_course_list[i++]= obj.getPatika().getName();
            row_course_list[i]= obj.getInstructor().getUserName();
            mdl_course_list.addRow(row_course_list);
        }
    }

    private void loadPatikaModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_patika_list.getModel();
        clearModel.setRowCount(0);

        int i;
        for (Patika obj : Patika.getList()
        ) {
            i=0;
            row_patika_list[i++] = obj.getId();
            row_patika_list[i++] = obj.getName();
            mdl_patika_list.addRow(row_patika_list);
        }
    }

    public void loadPatikaCmb() {
        cmb_course_patikas.removeAllItems();
        for (Patika patika : Patika.getList()) {
            cmb_course_patikas.addItem(new Item(patika.getId(),patika.getName()));
        }
    }

    public void loadInstructorCmb() {
        cmb_course_instructor.removeAllItems();
        for (User user : User.getListOnlyInstructors()) {
            cmb_course_instructor.addItem(new Item(user.getId(),user.getUserName()));
        }
    }
}
