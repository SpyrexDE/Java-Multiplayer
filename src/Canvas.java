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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Canvas extends JPanel implements ActionListener {

    public final int W_WIDTH = 1000;
    public final int W_HEIGHT = 1000;

    private boolean inGame = true;

    private final int DELAY = 17; // 60 FPS
    private Timer updateTimer;

    public ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
    private ArrayList<Integer> pressedKeys = new ArrayList<Integer>();

    private Player p;

    public Canvas() {
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(W_WIDTH, W_HEIGHT));
        addEventListeners();
        initGame();        
    }

    private void addEventListeners() {
        // Mouse down listener
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                // spawn projectile with velocity from player to mouse:
                float vel_x = evt.getX() - p.pos_x;
                float vel_y = evt.getY() - p.pos_y;
                float mag = (float) Math.sqrt(vel_x * vel_x + vel_y * vel_y);
                vel_x /= mag;
                vel_y /= mag;
                Projectile pr = new Projectile(p.pos_x, p.pos_y, "src/resources/projectile.png",  vel_x * 10,  vel_y * 10);
                gameObjects.add(pr);
            }
        });

        // Fixed update
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                for (GameObject go : gameObjects) {
                    go.fixedUpdate();
                }
            }
        };
        new Timer(17, taskPerformer).start();
    }

    private void initGame() {
    
        p = new Player(100, 100, "src/resources/player.png");
        gameObjects.add(p);

        inGame = true;
    
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }
    
    private void doDrawing(Graphics g) {
        if (inGame) {
            for (GameObject go : gameObjects) {
                go.update();
                Image image = rotate(go.image, go.rotation);
                g.drawImage(image, Math.round(go.pos_x), Math.round(go.pos_y), this);
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

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            
            if (!pressedKeys.contains(key))
                pressedKeys.add(key);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            
            if (pressedKeys.contains(key))
                pressedKeys.remove(pressedKeys.indexOf(key));
        }
    }

    public boolean isKeyPressed(int key) {
        return pressedKeys.contains(key);
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
