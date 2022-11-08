
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class GameCanvas extends Canvas implements ActionListener {
    private boolean inGame = true;

    private final int DELAY = 17; // 60 FPS
    private Timer updateTimer;

    private World world;


    public GameCanvas() {
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(W_WIDTH, W_HEIGHT));
        addEventListeners();
        InputHandler.init(this);
        initGame();
    }

    private void addEventListeners() {
        // Fixed update
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                for (GameObject go : world.gameObjects) {
                    go.fixedUpdate();
                }
            }
        };
        updateTimer = new Timer(DELAY, taskPerformer);
        updateTimer.start();
    }

    private void initGame() {
    
        world = new World();
        inGame = true;
    
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }
    
    private void doDrawing(Graphics g) {
        if (inGame) {
            world.update();

            // Draw all game objects of the world
            for (GameObject go : world.gameObjects) {
                Image image = rotate(go.image, go.rotation);
                g.drawImage(image, (int) Math.round(go.pos_x), (int) Math.round(go.pos_y), this);
            }

            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
        }        
        repaint();
    }

    private void gameOver(Graphics g) {
        
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (W_WIDTH - metr.stringWidth(msg)) / 2, W_HEIGHT / 2);
    }

    

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public Image rotate(Image image, double angle) {
        int w = image.getWidth(null);
        int h = image.getHeight(null);
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bi.createGraphics();
        g2d.rotate(Math.toRadians(angle), w / 2, h / 2);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return bi;
    }
}
