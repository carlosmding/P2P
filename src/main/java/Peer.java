import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;


public class Peer {
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader (new InputStreamReader(System.in));
        System.out.println("> enter username & port # for this peer: ");
        String[] setupValues= bufferedReader.readLine().split(" ");

        ServerThread serverThread = new ServerThread (setupValues[1]);
        serverThread.start();
        new Peer().updateListenToPeers (bufferedReader, setupValues [0], serverThread);
    }
    public void updateListenToPeers (BufferedReader bufferedReader, String username, ServerThread serverThread) throws Exception {
        System.out.println("> enter (space separated) hostname:port#");
        System.out.println(" peers to receive messages from (s to skip): ");
        String input=bufferedReader.readLine();
        String[] inputValues = input.split(" ");
        if (!input.equals("s")) for (int i = 0; i <inputValues.length; i++) {
            String[] address = inputValues[i].split(":");
            Socket socket = null;
            try {
                socket = new Socket (address [0], Integer.valueOf(address [1]));
                new PeerThread (socket).start();
            } catch (Exception e) {
                if (socket != null) socket.close();
                else System.out.println("invalid input. skipping to next step.");
            }
        }



        communicate (bufferedReader, username, serverThread);

        public void communicate (Buffered Reader buffered Reader, String username, ServerThread serverThread) {
            try {
                if (socket != null) socket.close();
                else System.out.println("invalid input. skipping to next step.");
                System.out.println("> you can now communicate (e to exit, c to change)");
                boolean flag = true;
                while(flag) {
                    String message = buffered Reader.readLine();
                }
                if (message.equals("e")) {
                    flag = false;
                    break;
                } else if (message.equals("c")) {
                    update ListenToPeers (buffered Reader, username, serverThread);
                } else {
                    StringWriter stringWriter = new StringWriter();
                    Json.createwriter
                            (stringwriter).writeObject(Json.createObjectBuilder()
                            .add("username", username)
                            .add("message", message)
                            .build());
                    serverThread.sendMessage(stringWriter.toString());
                    System.exit(0);
                    }


                }
        }
    }
}