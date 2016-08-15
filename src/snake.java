import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;

/**
 * Created by weixinwu on 2016-08-10.
 */
public class snake extends JFrame{


    public snake(){
        setBackground(Color.gray);
        setTitle("Snake game");
        setLocationRelativeTo(null);
        setSize(480,480);
        setResizable(false);
        add(new GameCanvas());



    }
    public static void main(String[] arg){



        snake s = new snake();
        s.setVisible(true);





    }
}
