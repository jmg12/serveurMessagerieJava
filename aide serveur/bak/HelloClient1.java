import java.net.*;
import java.io.*;

class HelloClient1 {
    public static void main(String argv[]) throws Exception
    {
        // On compose le numero de telephone de la personne a contacte 
        Socket socket = new Socket("localhost", 1111);
        //Socket socket = new Socket("127.0.0.1", 1111);


         // On ouvre un tube pour lire ce que nous envoie le serveur
        InputStreamReader inputStream = new InputStreamReader( socket.getInputStream() );
        BufferedReader input = new BufferedReader(inputStream ); 

        // On affiche a la console les elements envoyes par le serveur

        	System.out.println( input.readLine() );

        //On raccroche
        socket.close();
    }
}
