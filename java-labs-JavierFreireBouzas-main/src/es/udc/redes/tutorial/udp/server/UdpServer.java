package es.udc.redes.tutorial.udp.server;

import java.net.*;

/**
 * Implements a UDP echo server.
 */
public class UdpServer {

    public static void main(String argv[]) {
        if (argv.length != 1) {
            System.err.println("Format: es.udc.redes.tutorial.udp.server.UdpServer <port_number>");
            System.exit(-1);
        }
        DatagramSocket dtmSocket = null;
        try {
            // Create a server socket
            dtmSocket = new DatagramSocket(Integer.parseInt(argv[0]));
            // Set maximum timeout to 300 secs
            dtmSocket.setSoTimeout(300000);
            byte array[] = new byte[1024];
            while (true) {
                // Prepare datagram for reception
                DatagramPacket dtmPackRec = new DatagramPacket(array, array.length);
                // Receive the message
                dtmSocket.receive(dtmPackRec);
                System.out.println("SERVER: Received "
                        + new String(dtmPackRec.getData(), 0, dtmPackRec.getLength())
                        + " from " + dtmPackRec.getAddress().toString() + ":"
                        + dtmPackRec.getPort());
                // Prepare datagram to send response
                DatagramPacket dtmPackSent = new DatagramPacket(array, array.length, dtmPackRec.getAddress(), dtmPackRec.getPort());
                // Send response
                dtmSocket.send(dtmPackSent);
                System.out.println("SERVER: Sending "
                        + new String(dtmPackSent.getData()) + " to "
                        + dtmPackSent.getAddress().toString() + ":"
                        + dtmPackSent.getPort());
            }
          
        // Uncomment next catch clause after implementing the logic
        } catch (SocketTimeoutException e) {
           System.err.println("No requests received in 300 secs ");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
        // Close the socket
            if (dtmSocket != null)
                dtmSocket.close();
        }
    }
}
