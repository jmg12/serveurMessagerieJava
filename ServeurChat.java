import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;
//import ServiceChat.class;

public class ServeurChat {
	public static void main(String[] args) {
		
		int port = 1234;
		ServerSocket serverSocket;
		Socket socket;

		try {
			serverSocket = new ServerSocket(port);
			while (true) {
				System.out.println( "waiting..." );
				socket = serverSocket.accept();
				System.out.println( "connexion!" );
				new ServiceChat( socket );
			}

		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println( "problème de connexion" );
		}
		
	}
}
