package es.udc.redes.webserver;
import java.io.IOException;
import java.net.*;

public class WebServer {

    static String DIR = "p1-files";

    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Format: es.udc.redes.webserver.WebServer <port>");
            System.exit(-1);
        }
        ServerSocket servSocket = null;
        Socket cltSocket = null;
        ServerThread srvThrd;
        try {
            // Create a server socket
            servSocket = new ServerSocket(Integer.parseInt(args[0]));
            // Set a timeout of 300 secs
            servSocket.setSoTimeout(300000);
            while (true) {
                // Wait for connections
                cltSocket = servSocket.accept();
                // Create a ServerThread object, with the new connection, the default file and the default directory of files and true as parameters
                srvThrd = new ServerThread(cltSocket);
                // Initiate thread using the start() method
                srvThrd.start();
            }
        // Uncomment next catch clause after implementing the logic
        } catch (SocketTimeoutException e) {
            System.err.println("Nothing received in 300 secs");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally{
            //Close the socket
            try {
                if(servSocket != null) {
                    servSocket.close();
                }
                if(cltSocket != null){
                    cltSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
