import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Networking {
    
	public static final String PORT = "12346";
    public static boolean is_master = false;

    public static Client client;
    public static Server server;

    public static void startServer() {
        server = new Server(Integer.parseInt(PORT));
    }

    public static void startClient(String ip) {
        client = new Client(ip, Integer.parseInt(PORT));
    }

    // Class for handling the connection to the server
    public static class Client {
        private Socket socket;
        private DataInputStream in;
        private DataOutputStream out;

        public Client(String ip, int port) {
            try {
                socket = new Socket(ip, port);
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void send(String msg) {
            try {
                out.writeUTF(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String receive() {
            try {
                return in.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public void close() {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Class for handling the connection to the client without blocking the main thread
    public static class Server {
        private ServerSocket serverSocket;
        private Socket socket;
        private DataInputStream in;
        private DataOutputStream out;

        public Server(int port) {
            try {
                serverSocket = new ServerSocket(port);
                socket = serverSocket.accept();
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void send(String msg) {
            try {
                out.writeUTF(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String receive() {
            try {
                return in.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public void close() {
            try {
                in.close();
                out.close();
                socket.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getOwnIP() {
        try {
            return java.net.InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
