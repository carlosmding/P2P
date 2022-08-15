import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;
public class ServerThread extends Thread {
    private ServerSocket serverSocket;
    private Set<ServerThreadThread> serverThreadThreads = new HashSet<ServerThreadThread> ();
    public ServerThread (String portNumb) throws IOException {
        serverSocket = new ServerSocket (Integer.valueOf(portNumb));
    }

    public void run(){
    try {
        while (true) {
            ServerThreadThread serverThread Thread = new ServerThread Thread (serverSocket.accept(), this);
            serverThread Threads.add(server ThreadThread);
            serverThreadThread.start();
        } catch (Exception e) { e.printStackTrace(); }
        void sendMessage(String message) {
            try { serverThread Threads.forEach(t-> t.getPrintWriter().println(message));
            } catch (Exception e) { e.printStackTrace(); }
        }
        public Set<Server Thread Thread> getServerThread Threads() { return serverThreadThreads; }
    }
}
}