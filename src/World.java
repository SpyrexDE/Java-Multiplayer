import java.util.ArrayList;

import utils.Vector2D;

public class World {
    public static ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
    private static ArrayList<GameObject> gameObjectsToRemove = new ArrayList<GameObject>();
    private static ArrayList<GameObject> gameObjectsToAdd = new ArrayList<GameObject>();

    public static Player getPlayer() {
        for (GameObject go : gameObjects) {
            if (go instanceof Player)
                return (Player) go;
        }
        return null;
    }

    public static void queue_remove(GameObject go) {
        if(gameObjectsToAdd.contains(go))
            gameObjectsToAdd.remove(go);
        gameObjectsToRemove.add(go);
    }

    public static void queue_add(GameObject go) {
        if(gameObjectsToRemove.contains(go))
            gameObjectsToRemove.remove(go);
        
        go.id = gameObjects.size();
        gameObjectsToAdd.add(go);
    }

    public void update() {
        
        //if player does not exist and Sync.data_processed is true, create player
        if (getPlayer() == null) {
            if(Sync.data_processed || Networking.is_host) {
                new Player(this, new Vector2D(100, 100), "src/resources/player.png");
            }
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

        
        Sync.sendGOData();
        Sync.receiveAndProcessData();
    }
}
