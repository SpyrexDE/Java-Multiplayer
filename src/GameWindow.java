import java.awt.EventQueue;
import javax.swing.JFrame;

public class GameWindow extends JFrame {

    public static Canvas currentCanvas;
    public static MenuCanvas menuCanvas;
    public static GameWindow gameWindow;

    public GameWindow() {
        initUI();
        gameWindow = this;
    }
    
    private void initUI() {

        menuCanvas = new MenuCanvas();

        add(menuCanvas);
               
        setResizable(false);
        pack();
        
        setTitle("Multiplayer Game");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void loadGame() {
        remove(menuCanvas);
        currentCanvas = new Canvas();
        add(currentCanvas);
    }
}
