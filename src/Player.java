import java.awt.event.*;
import utils.Vector2D;

public class Player extends GameObject {
    public float speed = 1.0f;

    private final int SHOOT_COOLDOWN = 5; // in frames
    private int shoot_cooldown = 0;

    public Player(World world, Vector2D position, String image_path) {
        super(world, position, image_path);
    }

    public void handleInput() {
        if(is_puppet)
            return;

        if (InputHandler.isActionPressed("move_left")) {
            velocity.x += -speed;
        }

        if (InputHandler.isActionPressed("move_right")) {
            velocity.x += speed;
        }

        if (InputHandler.isActionPressed("move_up")) {
            velocity.y += -speed;
        }

        if (InputHandler.isActionPressed("move_down")) {
            velocity.y += speed;
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
        position.x += velocity.x;
        position.y += velocity.y;

        // Friction
        velocity.x *= 0.9f;
        velocity.y *= 0.9f;

        // Stop slowing down when velocity is too low
        if (velocity.x < 0.01f && velocity.x > -0.01f) {
            velocity.x = 0;
        }
        if (velocity.y < 0.01f && velocity.y > -0.01f) {
            velocity.y = 0;
        }

        // Make the player slide along the walls:
        if (position.x < 0) {
            position.x = 0;
            velocity.x = 0;
        }
        if (position.x > GameWindow.currentCanvas.W_WIDTH - image.getWidth(null)) {
            position.x = GameWindow.currentCanvas.W_WIDTH - image.getWidth(null);
            velocity.x = 0;
        }
        if (position.y < 0) {
            position.y = 0;
            velocity.y = 0;
        }
        if (position.y > GameWindow.currentCanvas.W_HEIGHT - image.getHeight(null)) {
            position.y = GameWindow.currentCanvas.W_HEIGHT - image.getHeight(null);
            velocity.y = 0;
        }
    }

    public void shoot(Vector2D direction) {
        Player p = world.getPlayer();

        // spawn projectile with velocity from player to mouse:
        double vel_x = direction.x - p.position.x;
        double vel_y = direction.y - p.position.y;
        double mag = Math.sqrt(vel_x * vel_x + vel_y * vel_y);
        vel_x /= mag;
        vel_y /= mag;
        new Projectile(world, p.position, "src/resources/projectile.png",  new Vector2D(vel_x, vel_y));
    }

}
