import java.util.ArrayList;

public class World {
    public ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
    private ArrayList<GameObject> gameObjectsToRemove = new ArrayList<GameObject>();
    private ArrayList<GameObject> gameObjectsToAdd = new ArrayList<GameObject>();

    public Player getPlayer() {
        for (GameObject go : gameObjects) {
            if (go instanceof Player)
                return (Player) go;
        }
        return null;
    }

    public void queue_remove(GameObject go) {
        gameObjectsToRemove.add(go);
    }

    public void queue_add(GameObject go) {
        gameObjectsToAdd.add(go);
    }

    public void update() {
        if (Networking.server != null) {
            String r = Networking.server.receive();
            if (r != null)
                System.out.println(Networking.server.receive());
        }

        for (GameObject go : gameObjects) {
            go.update();
        }
        for (GameObject go : gameObjectsToRemove) {
            gameObjects.remove(go);
        }
        gameObjectsToRemove.clear();
        for (GameObject go : gameObjectsToAdd) {
            gameObjects.add(go);
        }
        gameObjectsToAdd.clear();
    }
}
