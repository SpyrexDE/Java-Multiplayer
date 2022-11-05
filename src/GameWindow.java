import java.awt.EventQueue;
import javax.swing.JFrame;

public class GameWindow extends JFrame {

    public static Canvas currentCanvas;
    public static GameWindow window;

    public GameWindow() {
        initUI();
        window = this;
    }
    
    private void initUI() {

        switchCanvas(new MenuCanvas());
               
        setResizable(false);
        setTitle("Multiplayer Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void switchCanvas(Canvas canvas) {
        if(currentCanvas != null)
            remove(currentCanvas);
        currentCanvas = canvas;
        add(currentCanvas);
        pack();
        currentCanvas.requestFocus();
        setLocationRelativeTo(null);
    }
}
