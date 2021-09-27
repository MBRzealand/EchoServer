package echo_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    static Socket socket =null;
    static ServerSocket serverSocket =null;
    static NetworkDetails networkDetails = new NetworkDetails();
    static ArrayList<ConnectionThread> listOfConnections = new ArrayList<>();

//    static Map<String, Socket> map = new HashMap<String, Socket>();

    public static void main(String[] args){

        System.out.println("Starting server...");

        try{
            serverSocket = new ServerSocket(networkDetails.getPORT());
            System.out.println("Serversocket started on:\n" + "IP: " + networkDetails.getServerIP() + "\nPORT: " + networkDetails.getPORT());

        } catch(IOException e) {
            e.printStackTrace();
            System.out.println("Server error");

        }

        while(true){
            try{
                socket = serverSocket.accept();
                System.out.println("Connection established with " + socket.getRemoteSocketAddress().toString());
//                map.put("someKey", socket);
                ConnectionThread connectionThread = new ConnectionThread(socket);
                listOfConnections.add(connectionThread);

                System.out.println(listOfConnections.toString());

                connectionThread.start();

            } catch(Exception e) {
                e.printStackTrace();
                System.out.println("Connection Error");
            }

        }

    }

}
