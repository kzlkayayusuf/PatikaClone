import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //yüklü temaları göster
        for(UIManager.LookAndFeelInfo info:  UIManager.getInstalledLookAndFeels()){
            System.out.println(info.getName()+" => "+info.getClassName());
        }
    }
}