import java.awt.EventQueue;
import javax.swing.JFrame;

public class GameWindow extends JFrame {

    public static Canvas currentCanvas;

    public GameWindow() {
        initUI();
    }
    
    private void initUI() {
        
        currentCanvas = new Canvas();

        add(currentCanvas);
               
        setResizable(false);
        pack();
        
        setTitle("Multiplayer Game");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
