package es.udc.redes.tutorial.tcp.server;
import java.net.*;
import java.io.*;

/** Thread that processes an echo server connection. */

public class ServerThread extends Thread {

  private Socket socket;

  public ServerThread(Socket s) {
    // Store the socket s
    this.socket = s;
  }

  public void run() {
    try {
      // Set the input channel
      BufferedReader sInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      // Set the output channel
      PrintWriter sOutput = new PrintWriter(socket.getOutputStream(), true);
      // Receive the message from the client
      String recMessage = sInput.readLine();
      System.out.println("SERVER: Received " + recMessage.toString()
              + " from " + socket.getInetAddress().toString()
              + ":" + socket.getPort());
      // Sent the echo message to the client
      sOutput.println(recMessage);
      System.out.println("SERVER: Sending " + recMessage.toString()
              + " from " + socket.getInetAddress().toString()
              + ":" + socket.getPort());
      // Close the streams
      sOutput.close();
      sInput.close();
    // Uncomment next catch clause after implementing the logic
    } catch (SocketTimeoutException e) {
      System.err.println("Nothing received in 300 secs");
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
      } finally {
	// Close the socket
      try {
        if(socket != null) {
          socket.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
