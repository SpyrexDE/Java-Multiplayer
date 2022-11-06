import utils.Vector2D;

public class Projectile extends GameObject {
    public float speed = 1.0f;
    public float life_time = 0.0f;
    public float max_life_time = 1.0f;

    public Projectile(World world, Vector2D position, String image_path, Vector2D velocity) {
        super(world, position, image_path);
        this.velocity = velocity;
    }

    public void fixedUpdate() {
        // Life time
        life_time += 0.01f;
        if (life_time > max_life_time) {
            // Remove this projectile from the world without crashing
            world.queue_remove(this);
        }

        // Update position
        position.x += velocity.x;
        position.y += velocity.y;

        // Rotate image to velocity direction:
        double angle = Math.atan2(velocity.y, velocity.x);
        angle = Math.toDegrees(angle);
        rotation = angle;


        // Make the projectile collide with the walls and bounce off:
        if (position.x < 0) {
            position.x = 0;
            velocity.x *= -1;
        }
        if (position.x > GameWindow.currentCanvas.W_WIDTH - image.getWidth(null)) {
            position.x = GameWindow.currentCanvas.W_WIDTH - image.getWidth(null);
            velocity.x *= -1;
        }
        if (position.y < 0) {
            position.y = 0;
            velocity.y *= -1;
        }
        if (position.y > GameWindow.currentCanvas.W_HEIGHT - image.getHeight(null)) {
            position.y = GameWindow.currentCanvas.W_HEIGHT - image.getHeight(null);
            velocity.y *= -1;
        }
    }
}
