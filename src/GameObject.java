import java.awt.Image;
import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import utils.Vector2D;

public class GameObject {
    public int id;

    public Vector2D position = new Vector2D(0, 0);
    public Vector2D velocity = new Vector2D(0, 0);
    public int width;
    public int height;
    public double rotation = 0d;
    public Image image;
    public World world;

    public boolean is_puppet = false;
    public ArrayList<Field> syncedFields = new ArrayList<Field>() {{
        try {
            add(GameObject.class.getField("position"));
            add(GameObject.class.getField("velocity"));
            add(GameObject.class.getField("rotation"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }};

    public GameObject(World world, Vector2D position, String image_path) {
        this.position = position;

        ImageIcon ii = new ImageIcon(image_path);
        image = ii.getImage();

        this.width = image.getWidth(GameWindow.currentCanvas);
        this.height = image.getHeight(GameWindow.currentCanvas);

        this.world = world;
        world.queue_add(this);
    }

    public void update() {}

    public void fixedUpdate() {}

    public String packSyncData() {
        String data = "";
        for (Field field : syncedFields) {
            try {
                data += field.get(this) + ";";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public void applySyncData(String data) {
        String[] fields = data.split(";");
        for (int i = 0; i < fields.length; i++) {
            try {
                syncedFields.get(i).set(this, fields[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
