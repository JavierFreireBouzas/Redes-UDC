package es.udc.redes.webserver;
import java.net.*;
import java.io.*;
import java.util.StringTokenizer;


public class ServerThread extends Thread {

    Socket socket;
    File outFile = null;
    Header header;

    //constructor
    public ServerThread(Socket s) {
        this.socket = s;
    }


    public void run() {
        BufferedReader sInput;
        PrintWriter sOutput;
        BufferedOutputStream outputData;
        try {
            // Set the input channel
            sInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Set the output channel
            sOutput = new PrintWriter(socket.getOutputStream(), true);
            //Create a Buffered Output Stream
            outputData = new BufferedOutputStream(socket.getOutputStream());
            // Receive the message from the client

            String recMessage = "";
            String line;
            do{
               line = sInput.readLine();
               recMessage = recMessage.concat(line + "\n");
            }while(!line.equals(""));

             String[] recString = recMessage.split("\n");


            if(recString[0] != null) {
                StringTokenizer strDiv = new StringTokenizer(recString[0]);
                String methodType = strDiv.nextToken();
                String file = strDiv.nextToken();
                String protocol = strDiv.nextToken();
                String modDateStr = "";
                boolean ifMod = false;
                for(String str : recString){
                    if(str.substring(0, str.indexOf(" ")).equals("If-Modified-Since:")){
                        StringTokenizer strTok = new StringTokenizer(str);
                        strTok.nextToken();
                        modDateStr = strTok.nextToken();
                        ifMod = true;
                        break;
                    }
                }

                if(methodType.equals("GET")){
                    header = new Header(outFile);
                    header.headerPrinter(sOutput, outputData, protocol, file, 'G', modDateStr, ifMod, false);
                }else if(methodType.equals("HEAD")){
                    header = new Header(outFile);
                    header.headerPrinter(sOutput, outputData, protocol, file, 'H', modDateStr, ifMod, false);
                }else{
                    header = new Header(outFile);
                    header.headerPrinter(sOutput, outputData, protocol, "/error400.html", 'H', modDateStr, ifMod, true);
                }
            }

          // Uncomment next catch clause after implementing the logic
        } catch (SocketTimeoutException e) {
            System.err.println("Nothing received in 300 secs");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            // Close the client socket
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


