package es.udc.redes.webserver;
import java.io.File;
import java.util.Date;
import java.io.*;

public class Header {
    private File outFile;

    public Header(File outFile) {
        this.outFile = outFile;
    }

    public void headerPrinter(PrintWriter sOutput, BufferedOutputStream outputData, String protocol, String file, char getOrHead, String modDateStr, boolean ifMod, boolean badRequest) throws Exception {
        Codes code;
        Date date;
        String lstModified;


        File currentFile = new File(WebServer.DIR + file);

        if(badRequest) {
            code = Codes.BAD_RQST;
            outFile = new File(WebServer.DIR + "/error400.html");
        }else if(currentFile.exists() && ifMod){
            outFile = new File(WebServer.DIR + file);
            lstModified = "" + outFile.lastModified();
            if(Long.parseLong(modDateStr) >= Long.parseLong(lstModified))
                code = Codes.NOT_MOD;
            else
                code = Codes.OK;
        }else if(currentFile.exists()){
            code = Codes.OK;
            outFile = new File(WebServer.DIR + file);
        }else{
            code = Codes.NOT_FND;
            outFile = new File(WebServer.DIR + "/error404.html");
        }

        date = new Date();
        int size = (int) outFile.length();
        byte[] info = returnContent(outFile, size);

        sOutput.println(protocol + " " + code.getCode());
        sOutput.println("Date: " + date);
        sOutput.println("Last-Modified: " + outFile.lastModified());
        sOutput.println("Server: WebServer_856");
        sOutput.println("Content-Length: " + outFile.length());
        sOutput.println("Content-Type: " + returnType(outFile));
        sOutput.println("");
        sOutput.flush();


        if(code == Codes.OK){
            if(getOrHead == 'G')
                outputData.write(info, 0, size);
        }else{
            if(getOrHead == 'G' || badRequest)
                outputData.write(info, 0, size);
        }
        outputData.flush();
    }

    public String returnType(File outFile){
        String fileName = outFile.getName().toLowerCase();

        if(fileName.endsWith(".html"))
            return "text/html";
        else if(fileName.endsWith(".txt"))
            return "text/plain";
        else if(fileName.endsWith(".gif"))
            return "image/gif";
        else if(fileName.endsWith(".png"))
            return "image/png";
        else
            return "application/octet-stream";
    }

    private byte [] returnContent (File file, int size) throws IOException  {
        FileInputStream in = null ;
        byte [] info = new byte [size];

        try  {
            in = new FileInputStream (file);
            in.read(info);
        }finally{
            if (in!=null) {
                in.close();
            }
        }
        return info;
    }
}
