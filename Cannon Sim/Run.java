import Animate.Application;
import java.awt.EventQueue;

public class Run {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Application myApp = new Application("Title");
            myApp.setVisible(true);
        });

    }
}