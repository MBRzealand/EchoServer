package discount_attempt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class DiscountServer {

    Set<String> listOfCurrentUsers = new HashSet<>();
    Set<PrintWriter> writers = new HashSet<>();

    static NetworkDetails details = new NetworkDetails();
    static ServerSocket serverSocket;
    static Socket socket;
    static DataInputStream inputStream = null;
    static DataOutputStream outputStream = null;

    static void startServer()  throws IOException {

        System.out.println("Starting server...");

        serverSocket = new ServerSocket(details.getPORT());

        System.out.println("Now accepting connections on port " + details.getPORT() + ".");


        new Thread(() -> {

            while (true) {

                try {
                    socket = serverSocket.accept();
                    System.out.println("Connection established with " + socket.getRemoteSocketAddress().toString());
                    requestUsername();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }

    void stopServer(){

    }


    static void requestUsername() throws IOException {
        if(socket.isConnected()){
            outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeUTF("Please enter a Username:");
            outputStream.flush();
        }
    }

    static void updateServer() throws IOException {

        new Thread(() -> {

            while (true) {

                try {
                    inputStream = new DataInputStream(socket.getInputStream());
                    outputStream = new DataOutputStream(socket.getOutputStream());
                    String incomingText = inputStream.readUTF(); // Jeg læser en string på port 1978
                    System.out.println("Tekst modtaget: " + incomingText);
                } catch (Exception ignored) {
                }
//                } finally {
//                    inputStream.close();
//                    outputStream.close();
//                }

            }

        }).start();

    }

    public static void main(String[] args)  throws IOException {
        startServer();
        updateServer();
    }

}
