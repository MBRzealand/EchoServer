package first_attempt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    static BufferedReader clientInput;
    static PrintWriter clientOutput;

    public static void main(String[] args) throws IOException {

        System.out.println("Hej, hvilken server vil du tilsutte dig? (Tryk ENTER for localhost.)");
        Scanner scanner = new Scanner(System.in);
        String serverString = scanner.nextLine();

        if (serverString.equals("")) {
            serverString = "localhost";
        }

        System.out.println("Tilslutter til " + serverString);

        var socket = new Socket(serverString, 6969);
        var inputStream = new InputStreamReader(socket.getInputStream());
        clientInput = new BufferedReader(inputStream);
        //Scanner in = new Scanner(socket.getInputStream());
        clientOutput = new PrintWriter(socket.getOutputStream(), true);


        while (clientInput.readLine() != null) {
            var line = clientInput.readLine();
            clientInput.close();
            if (line.startsWith("SUBMITNAME")) {
                System.out.println("Skriv dit navn");
                String mitNavn = scanner.nextLine();
                clientOutput.println(mitNavn);
            } else if (line.startsWith("NAMEACCEPTED")) {
                System.out.println("Chatter - " + line.substring(13));
                sendMessage();
            } else if (line.startsWith("MESSAGE")) {
                System.out.println(line.substring(8) + "\n");
                sendMessage();
            }
        }

    }

    public static void sendMessage() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Skriv en besked");
        String besked =  scanner.nextLine();
        clientOutput.println(besked);
    }
}
