package es.udc.redes.webserver;

public enum Codes {
    OK("200 OK"),
    NOT_MOD("304 Not Modified"),
    BAD_RQST("400 Bad Request"),
    NOT_FND("404 Not Found");

    private final String code;

    Codes(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }
}
