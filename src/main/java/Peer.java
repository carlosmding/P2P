import javax.json.Json;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.Socket;


public class Peer {
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader (new InputStreamReader(System.in));
        System.out.println("Para iniciar, ingrese su usuario y número de puerto separados por un espacio: ");
        String[] setupValues= bufferedReader.readLine().split(" ");

        ServerThread serverThread = new ServerThread (setupValues[1]);
        serverThread.start();
        new Peer().updateListenToPeers (bufferedReader, setupValues [0], serverThread);
    }
    public void updateListenToPeers (BufferedReader bufferedReader, String username, ServerThread serverThread) throws Exception {
        System.out.println("Ingrese los puertos de los usuario con los que desea recibir mensajes");
        System.out.println("Digite (s) para solo enviar mensajes: ");
        String input=bufferedReader.readLine();
        String[] inputValues = input.split(" ");
        if (!input.equals("s")) for (String inputValue : inputValues) {
            String[] address = inputValue.split(":");
            Socket socket = null;
            try {
                socket = new Socket(address[0], Integer.valueOf(address[1]));
                new PeerThread(socket).start();
            } catch (Exception e) {
                if (socket != null) socket.close();
                else System.out.println("Comando inválido.");
            }
        }
        communicate (bufferedReader, username, serverThread);
    }

    public void communicate (BufferedReader bufferedReader, String username, ServerThread serverThread) {
        try {
            System.out.println("Ya puede establecer comunicación; recuerd (e) para salir y (c) para cambiar los usuarios con los que desea comunicarse");
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
                            .add("username", username)
                            .add("message", message)
                            .build());
                    serverThread.sendMessage(stringWriter.toString());
                }
            }
            System.exit(0);
            }catch (Exception e){
                System.out.println("Error en enviar mensaje");
            }
    }

}