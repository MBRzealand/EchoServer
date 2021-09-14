package first_attempt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.Executors;

public class Server {


    //-----------------------------------------------------------------------------------

    public static void main(String[] args) throws IOException {

        System.out.println("server started");

        var pool = Executors.newFixedThreadPool(500);
        try (var listener = new ServerSocket(6969)) {
            while (true) {
                pool.execute(new Handler(listener.accept()));
            }
        }



    }




}

    //------------------------------------------------------------------------------

class Handler implements Runnable {
    private String nameOfUser;
    private Socket socket;
    private BufferedReader serverInput;
    private PrintWriter serverOutput;

    Set<String> listOfCurrentUsers = new HashSet<>();
    Set<PrintWriter> writers = new HashSet<>();


    public Handler(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {

        try {

            var inputStream = new InputStreamReader(socket.getInputStream());
            serverInput = new BufferedReader(inputStream);
            serverOutput = new PrintWriter(socket.getOutputStream(), true);


            while (true) {
                serverOutput.println("SUBMITNAME");
                nameOfUser = serverInput.readLine();

                inputStream.close();

                if (nameOfUser == null) {
                    return;
                }

                synchronized (listOfCurrentUsers) {
                    if (!nameOfUser.isBlank() && !listOfCurrentUsers.contains(nameOfUser)) {
                        listOfCurrentUsers.add(nameOfUser);
                        break;
                    }
                }

            }

            serverOutput.println("NAMEACCEPTED " + nameOfUser);

            for (PrintWriter writer : writers) {
                writer.println("MESSAGE " + nameOfUser + " has joined");
            }

            writers.add(serverOutput);

        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            if (serverOutput != null) {
                writers.remove(serverOutput);
            }

            if (nameOfUser != null) {
                System.out.println(nameOfUser + " is leaving");
                listOfCurrentUsers.remove(nameOfUser);

                for (PrintWriter writer : writers) {
                    writer.println("MESSAGE " + nameOfUser + " has left");
                }

            } try {
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
