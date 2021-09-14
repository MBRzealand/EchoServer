package discount_attempt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class DiscountServer {

    static NetworkDetails details = new NetworkDetails();
    static ServerSocket serverSocket;
    static Socket socket;
    static DataInputStream inputStream = null;
    static DataOutputStream outputStream = null;

    static void startServer() throws IOException {

        System.out.println("Starting server...");

        serverSocket = new ServerSocket(details.getPORT());

        System.out.println("Now accepting connections on port " + details.getPORT() + ".");

        socket = serverSocket.accept();

        System.out.println("Connection established with " + socket.getRemoteSocketAddress().toString());

    }

    void stopServer(){

    }

    static void updateServer() throws IOException {

        while (true) {

            if(!socket.isClosed()){

            try {
                inputStream = new DataInputStream(socket.getInputStream());
                outputStream = new DataOutputStream(socket.getOutputStream());
                String incomingText = inputStream.readUTF(); // Jeg læser en string på port 1978
                System.out.println("Tekst modtaget: " + incomingText);
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                inputStream.close();
                outputStream.close();
            }

            } else {
                socket = serverSocket.accept();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        startServer();
        updateServer();
    }

}
