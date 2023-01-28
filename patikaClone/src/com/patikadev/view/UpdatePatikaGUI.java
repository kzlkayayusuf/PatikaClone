package com.patikadev.view;

import com.patikadev.helper.Config;
import com.patikadev.helper.Helper;
import com.patikadev.model.Patika;

import javax.swing.*;

public class UpdatePatikaGUI extends JFrame {
    private JPanel wrapper;
    private JTextField tfld_patika_name;
    private JButton btn_patika_update;
    private Patika patika;
    public UpdatePatikaGUI(Patika patika){
        add(wrapper);
        this.patika=patika;
        setSize(300,150);
        setLocation(Helper.screenCenter("x",getSize()),Helper.screenCenter("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        tfld_patika_name.setText(patika.getName());
        btn_patika_update.addActionListener(e -> {
            if(Helper.isFieldEmpty(tfld_patika_name)){
                Helper.showMessage("fill");
            }else{
                if (Patika.update(patika.getId(), tfld_patika_name.getText())) {
                    Helper.showMessage("updated");
                }
                dispose();
            }
        });
    }
}
