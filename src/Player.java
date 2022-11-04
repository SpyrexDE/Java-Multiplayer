import java.awt.event.*;
import utils.Vector2D;

public class Player extends GameObject {
    public float vel_x;
    public float vel_y;

    public float speed = 1.0f;

    private final int SHOOT_COOLDOWN = 5; // in frames
    private int shoot_cooldown = 0;

    public Player(World world, double pos_x, double pos_y, String image_path) {
        super(world, pos_x, pos_y, image_path);
    }

    public void handleInput() {
        if (InputHandler.isActionPressed("move_left")) {
            vel_x += -speed;
        }

        if (InputHandler.isActionPressed("move_right")) {
            vel_x += speed;
        }

        if (InputHandler.isActionPressed("move_up")) {
            vel_y += -speed;
        }

        if (InputHandler.isActionPressed("move_down")) {
            vel_y += speed;
        }
        if(InputHandler.isActionPressed("shoot")) {
            // Shoot a projectile when cooldown is over
            if (shoot_cooldown <= 0) {
                shoot(InputHandler.getMousePosOnCanvas());
                shoot_cooldown = SHOOT_COOLDOWN;
            }
        }
    }

    @Override
    public void fixedUpdate() {
        if (!is_puppet)
            handleInput();

        // Update shoot_cooldown
        shoot_cooldown -= 1;

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

    public void shoot(Vector2D direction) {
        Player p = world.getPlayer();

        // spawn projectile with velocity from player to mouse:
        double vel_x = direction.x - p.pos_x;
        double vel_y = direction.y - p.pos_y;
        double mag = Math.sqrt(vel_x * vel_x + vel_y * vel_y);
        vel_x /= mag;
        vel_y /= mag;
        Projectile pr = new Projectile(world, p.pos_x, p.pos_y, "src/resources/projectile.png",  vel_x * 10,  vel_y * 10);
    }

}
