import utils.Vector2D;

public class Projectile extends GameObject {
    public float speed = 1.0f;
    public float life_time = 0.0f;
    public float max_life_time = 1.0f;

    public Projectile(World world, Vector2D position, String image_path, Vector2D velocity) {
        super(world, position, image_path);
        this.vel_x = velocity.x;
        this.vel_y = velocity.y;
    }

    public void fixedUpdate() {
        // Life time
        life_time += 0.01f;
        if (life_time > max_life_time) {
            // Remove this projectile from the world without crashing
            world.queue_remove(this);
        }

        // Update position
        pos_x += vel_x;
        pos_y += vel_y;

        // Rotate image to velocity direction:
        double angle = Math.atan2(vel_y, vel_x);
        angle = Math.toDegrees(angle);
        rotation = angle;


        // Make the projectile collide with the walls and bounce off:
        if (pos_x < 0) {
            pos_x = 0;
            vel_x *= -1;
        }
        if (pos_x > GameWindow.currentCanvas.W_WIDTH - image.getWidth(null)) {
            pos_x = GameWindow.currentCanvas.W_WIDTH - image.getWidth(null);
            vel_x *= -1;
        }
        if (pos_y < 0) {
            pos_y = 0;
            vel_y *= -1;
        }
        if (pos_y > GameWindow.currentCanvas.W_HEIGHT - image.getHeight(null)) {
            pos_y = GameWindow.currentCanvas.W_HEIGHT - image.getHeight(null);
            vel_y *= -1;
        }
    }
}
