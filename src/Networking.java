import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Networking {
    
	public static final int PORT = 12346;
    public static boolean is_master = false;

    public static Client client;
    public static Server server;

    public static void startServer() {
        server = new Server(PORT);
        new Thread(server).start();
    }

    public static void startClient(String ip) {
        client = new Client(ip, PORT);
        new Thread(client).start();
    }

    public static class Client implements Runnable{
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

        public void run() {
            while (true) {
                String msg = receive();
                if (msg != null) {
                    // Process new data from server
                }
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
                if (in.available() > 0)
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

    public static class Server implements Runnable {

        protected int          serverPort;
        protected ServerSocket serverSocket = null;
        protected boolean      isStopped    = false;
        protected Thread       runningThread= null;
    
        public ArrayList<Socket> sockets = new ArrayList<Socket>();

        public Server(int port){
            this.serverPort = PORT;
        }
    
        public void run(){
            synchronized(this){
                this.runningThread = Thread.currentThread();
            }
            openServerSocket();
            while(! isStopped()){
                try {
                    System.out.println("Waiting for client to connect...");
                    sockets.add(this.serverSocket.accept());
                    System.out.println("Client connected!");
                } catch (IOException e) {
                    if(isStopped()) {
                        System.out.println("Server Stopped.");
                        return;
                    }
                    throw new RuntimeException(
                        "Error accepting client connection", e);
                }
            }
            System.out.println("Server Stopped.") ;
        }
    

        public ArrayList<String> receive() {
            try {
                ArrayList<String> messages = new ArrayList<String>();
                for(int i = 0; i < sockets.size(); i++) {
                    InputStream in = sockets.get(i).getInputStream();
                    DataInputStream dis = new DataInputStream(in);
                    messages.add(dis.readUTF());
                }
                return messages;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public void send(String msg) {
            try {
                for(int i = 0; i < sockets.size(); i++) {
                    OutputStream output = sockets.get(i).getOutputStream();
                    DataOutputStream dataOutput = new DataOutputStream(output);
                    dataOutput.writeUTF(msg);
                    dataOutput.flush();
                    dataOutput.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
        private synchronized boolean isStopped() {
            return this.isStopped;
        }
    
        public synchronized void stop(){
            this.isStopped = true;
            try {
                this.serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException("Error closing server", e);
            }
        }
    
        private void openServerSocket() {
            try {
                this.serverSocket = new ServerSocket(this.serverPort);
            } catch (IOException e) {
                throw new RuntimeException("Cannot open port " + PORT, e);
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
