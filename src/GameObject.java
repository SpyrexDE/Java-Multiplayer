import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class GameObject {
    public float pos_x;
    public float pos_y;
    public int width;
    public int height;
    public double rotation = 0d;
    public Image image;

    public GameObject(float pos_x, float pos_y, String image_path) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;

        ImageIcon ii = new ImageIcon(image_path);
        image = ii.getImage();

        this.width = image.getWidth(GameWindow.currentCanvas);
        this.height = image.getHeight(GameWindow.currentCanvas);
    }

    public void update() {}

    public void fixedUpdate() {}
}
