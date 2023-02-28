package exceptions;

public class ClientRequestException extends Exception{
    public ClientRequestException(String message){
        super(message);
    }
}
