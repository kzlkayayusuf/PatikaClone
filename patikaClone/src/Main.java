import com.patikadev.helper.Helper;
import com.patikadev.model.Operator;
import com.patikadev.view.OperatorGUI;


public class Main {
    public static void main(String[] args) {
        //yüklü temaları göster
        /*
        for(UIManager.LookAndFeelInfo info:  UIManager.getInstalledLookAndFeels()){
            System.out.println(info.getName()+" => "+info.getClassName());
        }
         */

        Helper.setLayout();
        Operator operator=new Operator();
        operator.setId(1);
        operator.setFirstName("yusuf");
        operator.setLastName("kızılkaya");
        operator.setUserName("kzlkayay");
        operator.setUserPassword("123456");
        operator.setUserType("OPERATOR");
        OperatorGUI operatorGUI=new OperatorGUI(operator);
    }
}