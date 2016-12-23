import javax.swing.*;
import java.awt.*;

/**
 * Created by BodeNg on 2016/12/23.
 */
public class GraphicalInteraction extends JFrame{

    public GraphicalInteraction() {
        setTitle("在图形内拖拽，在任意地方滚轮以放缩");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(30, 30, 640, 640);
        getContentPane().add(new MyComponent(200, 100, 100, 100));
        setVisible(true);
//        pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GraphicalInteraction();
            }
        });
    }

    class MyCanvas extends JComponent {

        public void paint(Graphics g) {
            g.drawRect (10, 10, 200, 200);
        }
    }

}
