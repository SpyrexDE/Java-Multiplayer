import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Sync {
        
    public static boolean data_processed = false;


    public static void sendGOData() {

        String data = "";
        for (GameObject go : World.gameObjects) {
            if (!go.is_puppet)
                data += go.id + ":" + go.getClass().getName() + ":" + go.packSyncData() + "|";
        }

        if (Networking.is_host) {
            Networking.server.send(data);
        } else {
            Networking.client.send(data);
        }
    }

    public static void processGOData(String data) {
        String[] gameObjects = data.split("\\|");
        for (String go : gameObjects) {
            String[] fields = go.split(":");
            int id = Integer.parseInt(fields[0]);
            String goClassName = fields[1];
            String goData = fields[2];

            try {
                GameObject gameObject = World.gameObjects.get(id);
                if (gameObject.is_puppet)
                    gameObject.applySyncData(goData);
            } catch (IndexOutOfBoundsException e) {
                // Object doesn't exist yet, create it as puppet from goData
                GameObject newGO;
                try {
                    newGO = (GameObject) Class.forName(goClassName).getConstructor().newInstance();
                    newGO.applySyncData(goData);
                    newGO.is_puppet = true;
                    World.queue_add(newGO);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        data_processed = true;
    }

    public static void receiveAndProcessData() {
        if (Networking.is_host) {
            ArrayList<String> data = Networking.server.receive();
            if(data == null)
                return;
            for (String d : data) {
                processGOData(d);
            }
        } else {
            String data = Networking.client.receive();
            if (data == null)
                return;
            processGOData(data);
        }
    }
}
