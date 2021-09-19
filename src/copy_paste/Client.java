package copy_paste;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    static NetworkDetails networkDetails = new NetworkDetails();
    static Socket socket =null;
    static String line=null;
    static BufferedReader br=null;
    static BufferedReader inputStream =null;
    static PrintWriter outputStream =null;

public static void main(String[] args) throws IOException{

    try {
        socket =new Socket(networkDetails.getServerIP(), networkDetails.getPORT());
        br= new BufferedReader(new InputStreamReader(System.in));
        inputStream =new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputStream = new PrintWriter(socket.getOutputStream());
    }
    catch (IOException e){
        e.printStackTrace();
        System.err.print("IO Exception");
    }

    InetAddress address=InetAddress.getLocalHost();
    System.out.println("Client Address : " + address);
    System.out.println("Enter Data to echo Server ( Enter QUIT to end):");

    String response=null;
    try{
        line=br.readLine(); 
        while(line.compareTo("QUIT") != 0){
            outputStream.println(line);
            outputStream.flush();
            response= inputStream.readLine();
            System.out.println("Server Response : " + response);
            line = br.readLine();

        }

    } catch(IOException e){

        e.printStackTrace();
        System.out.println("Socket read Error");

    } finally {

        inputStream.close();
        outputStream.close();br.close();
        socket.close();
        System.out.println("Connection Closed");

    }

}
}