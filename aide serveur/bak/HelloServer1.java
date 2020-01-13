import java.net.*;
import java.io.*;

class HelloServer1 {
    public static void main(String argv[]) throws Exception
    {
        //On installe le combine sur le numero de telephone
        ServerSocket serversocket = new ServerSocket(1111);

        //On attend les appels entrants
        Socket socket = serversocket.accept();

        //On ouvre un tube pour envoyer un message
        PrintStream out = new PrintStream( socket.getOutputStream() );

        //On ecrit et on envoie le message
        out.println( "Hello World!" );

        //On raccroche
        socket.close();
    }
}
