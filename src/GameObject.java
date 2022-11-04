import java.awt.Image;
import java.awt.event.*;

import javax.swing.ImageIcon;

public class GameObject {
    public double pos_x;
    public double pos_y;
    public int width;
    public int height;
    public double rotation = 0d;
    public Image image;
    public World world;

    public boolean is_puppet = false;

    public GameObject(World world, double pos_x, double pos_y, String image_path) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;

        ImageIcon ii = new ImageIcon(image_path);
        image = ii.getImage();

        this.width = image.getWidth(GameWindow.currentCanvas);
        this.height = image.getHeight(GameWindow.currentCanvas);

        this.world = world;
        world.queue_add(this);
    }

    public void update() {}

    public void fixedUpdate() {}
}
