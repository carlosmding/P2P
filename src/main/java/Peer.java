import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.Socket;
import javax.json.Json;


public class Peer {
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader (new InputStreamReader(System.in));
        System.out.println("> ingrese su usuario & número de puerto separados por un espacio: ");
        String[] setupValues= bufferedReader.readLine().split(" ");

        ServerThread serverThread = new ServerThread (setupValues[1]);
        serverThread.start();
        new Peer().updateListenToPeers (bufferedReader, setupValues [0], serverThread);
    }
    public void updateListenToPeers (BufferedReader bufferedReader, String username, ServerThread serverThread) throws Exception {
        System.out.println(">Ingrese los puertos de los usuario que desea recibir mensajes");
        System.out.println(" Digite s para solo enviar mensajes: ");
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
                else System.out.println("Comando inválido.");
            }
        } communicate (bufferedReader, username, serverThread);
    }

    public void communicate (BufferedReader bufferedReader, String username, ServerThread serverThread) {
        try {
            System.out.println("> Ahora puede establecer comunicación(e para salir, c para cambiar el usuario)");
            boolean flag = true;
            while(flag) {
                String message = bufferedReader.readLine();
                if (message.equals("e")) {
                    flag = false;
                    break;
                } else if (message.equals("c")) {
                    updateListenToPeers(bufferedReader, username, serverThread);
                } else {
                    StringWriter stringWriter = new StringWriter();
                    Json.createWriter(stringWriter).writeObject(Json.createObjectBuilder()
                            .add("Usuario", username)
                            .add("Mensaje", message)
                            .build());
                    serverThread.sendMessage(stringWriter.toString());
                }
            }
            System.exit(0);
            }catch (Exception e){}
    }

}