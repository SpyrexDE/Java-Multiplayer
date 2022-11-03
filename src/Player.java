import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Player extends GameObject {
    public float vel_x;
    public float vel_y;

    public float speed = 1.0f;

    public Player(float pos_x, float pos_y, String image_path) {
        super(pos_x, pos_y, image_path);
    }

    public void handleInput() {
        if (GameWindow.currentCanvas.isKeyPressed(KeyEvent.VK_LEFT)) {
            vel_x += -speed;
        }

        if (GameWindow.currentCanvas.isKeyPressed(KeyEvent.VK_RIGHT)) {
            vel_x += speed;
        }

        if (GameWindow.currentCanvas.isKeyPressed(KeyEvent.VK_UP)) {
            vel_y += -speed;
        }

        if (GameWindow.currentCanvas.isKeyPressed(KeyEvent.VK_DOWN)) {
            vel_y += speed;
        }
    }

    @Override
    public void fixedUpdate() {
        handleInput();

        // Update position
        pos_x += vel_x;
        pos_y += vel_y;

        // Friction
        vel_x *= 0.9f;
        vel_y *= 0.9f;

        // Stop slowing down when velocity is too low
        if (vel_x < 0.01f && vel_x > -0.01f) {
            vel_x = 0;
        }
        if (vel_y < 0.01f && vel_y > -0.01f) {
            vel_y = 0;
        }

        // Make the player slide along the walls:
        if (pos_x < 0) {
            pos_x = 0;
            vel_x = 0;
        }
        if (pos_x > GameWindow.currentCanvas.W_WIDTH - image.getWidth(null)) {
            pos_x = GameWindow.currentCanvas.W_WIDTH - image.getWidth(null);
            vel_x = 0;
        }
        if (pos_y < 0) {
            pos_y = 0;
            vel_y = 0;
        }
        if (pos_y > GameWindow.currentCanvas.W_HEIGHT - image.getHeight(null)) {
            pos_y = GameWindow.currentCanvas.W_HEIGHT - image.getHeight(null);
            vel_y = 0;
        }
    }

}
