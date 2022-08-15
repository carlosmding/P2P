import java.io. BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ServerThreadThread extends Thread {

    private ServerThread serverThread;
    private Socket socket;
    private Printwriter printwriter;
    public ServerThreadThread (Socket socket, ServerThread serverThread) {
        this.serverThread = serverThread;
        this.socket = socket;
    }
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader (this.socket.getInputStream()));
            this.printwriter = new PrintWriter (socket.getOutputStream(), true);
            while(true) serverThread.sendMessage( bufferedReader.readLine());
        } catch (Exception e) { serverThread.getServer ThreadThreads().remove(this); }
    }

    public Printwriter getPrintwriter() { return printwriter; }
}
