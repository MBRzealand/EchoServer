package discount_attempt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class DiscountClient {

    static Socket socket;

    static String username;

    static NetworkDetails details = new NetworkDetails();

    static void connect() throws IOException {
        System.out.println("Attempting to connect");
        socket = new Socket(details.getClientIP(),details.getPORT());
        System.out.println("connected to:" + details.getServerIp() + " " + details.getPORT());

        new Thread(() -> {

            DataInputStream inputStream = null;
            try {
                inputStream = new DataInputStream(socket.getInputStream());
                String incomingText = inputStream.readUTF(); // Jeg læser en string på port 1978
                System.out.println(incomingText);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();

    }

    static void disconnect(){}

    static void sendMessage() throws IOException {

        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        Scanner messageInput = new Scanner(System.in);

        while(true) {


            String message = messageInput.nextLine();

            outputStream.writeUTF(message);
            outputStream.flush();

//            outputStream.close();
//            messageInput.close();
        }

    }


    public static void main(String[] args) throws IOException {
        connect();
        sendMessage();
    }



}
