package es.udc.redes.tutorial.tcp.server;

import java.net.*;
import java.io.*;

/**
 * MonoThread TCP echo server.
 */
public class MonoThreadTcpServer {

    public static void main(String argv[]) {
        if (argv.length != 1) {
            System.err.println("Format: es.udc.redes.tutorial.tcp.server.MonoThreadTcpServer <port>");
            System.exit(-1);
        }
        ServerSocket servSocket = null;
        Socket cltSocket = null;
        try {

            // Create a server socket
            servSocket = new ServerSocket(Integer.parseInt(argv[0]));
            // Set a timeout of 300 secs
            servSocket.setSoTimeout(300000);
            while (true) {
                // Wait for connections
                cltSocket = servSocket.accept();
                // Set the input channel
                BufferedReader sInput = new BufferedReader(new InputStreamReader(cltSocket.getInputStream()));
                // Set the output channel
                PrintWriter sOutput = new PrintWriter(cltSocket.getOutputStream(), true);
                // Receive the client message
                String recMessage = sInput.readLine();
                System.out.println("SERVER: Received " + recMessage.toString()
                        + " from " + cltSocket.getInetAddress().toString()
                        + ":" + cltSocket.getPort());
                // Send response to the client
                sOutput.println(recMessage);
                System.out.println("SERVER: Sending " + recMessage.toString()
                        + " from " + cltSocket.getInetAddress().toString()
                        + ":" + cltSocket.getPort());
                // Close the streams
                sOutput.close();
                sInput.close();
            }
        // Uncomment next catch clause after implementing the logic            
        } catch (SocketTimeoutException e) {
            System.err.println("Nothing received in 300 secs ");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
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
