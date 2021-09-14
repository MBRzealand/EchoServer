package discount_attempt;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class DiscountClient {

    static Socket socket;

    static NetworkDetails details = new NetworkDetails();

    static void connect() throws IOException {
        System.out.println("Attempting to connect");
        socket = new Socket(details.getClientIP(),details.getPORT());
        System.out.println("connected to:" + details.getServerIp() + " " + details.getPORT());
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
