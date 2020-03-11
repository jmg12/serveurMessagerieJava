import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

public class ClientChat extends Thread {
	PrintStream outputConsole, outputNetwork;
	BufferedReader inputConsole, inputNetwork;
	Socket socketClient;
	//InetAddress serveur;
	String messageClient;
	String messageServer;
	final static int port = 1234;

	public static void main(String[] args) {
		new ClientChat(args);
	}
	public ClientChat (String[] args){
		initStreams(args[0]);
		start();
		while (true) {
			listenConsole();

		}
	}

	public void initStreams( String serveur ) {  // ipServ

		try {
			//serveur = InetAddress.getByName(ipServ);

			socketClient = new Socket(serveur, port);

			// Tube pour envoyer un message depuis le serveur
			inputNetwork = new BufferedReader( new InputStreamReader( socketClient.getInputStream() ) );

			// On ouvre le tube pour lire le message envoyer par le client
			outputNetwork = new PrintStream( socketClient.getOutputStream() );

			// Tube pour ecrire un message depuis la console
			inputConsole = new BufferedReader( new InputStreamReader( System.in ) );

			// On ouvre le tube pour afficher dans la console le message envoye depuis le serveur
			outputConsole = new PrintStream( System.out );


		} catch( IOException e ) {
			try {
				socketClient.close();
				socketClient.isClosed();
			} catch( IOException e2 ) {
				System.out.println( "probleme en fermant socket" );
			}
		}
		
	}

	public void listenConsole() {
		try {
			// inputConsole
			messageClient = inputConsole.readLine();

			// traiter le CTRL-C
			//System.out.println(messageClient); // DEBUG
			if( messageClient == null ) {
				System.out.println( "CTRL C detected" );
				messageClient = "/exit";
				
			}

			// outputNetwork
			outputNetwork.print(messageClient + "\n");
			
			// traiter le /exit
			if( messageClient.equals("/exit") ) {
				try {
					socketClient.close();
					socketClient.isClosed();
				} catch (IOException e) {
					System.out.println( "probleme en fermant socket" );
					e.printStackTrace();
				}
			}

		} catch( IOException e) {
			try {
				socketClient.close();
				socketClient.isClosed();
			} catch( IOException e2) {
				System.out.println( "probleme en lisant la console puis en fermant socket" );
				e2.printStackTrace();
			}
		}
	}

	public void listenNetwork() {
		try {
			//inputNetWork
			messageServer = inputNetwork.readLine();
			//outputConsole
			outputConsole.print(messageServer + "\n");
			//System.out.println("" + messageServer ); // option2
		} catch( IOException e) {
			try {
				socketClient.close();
				socketClient.isClosed();
			} catch (IOException e2) {
				System.out.println( "probleme en lisant la socket puis en fermant socket" );
				e2.printStackTrace();
			}
		}
	}

	public void run() {
		while(true) {
			listenNetwork();
			//System.out.println("TEST : "); // DEBUG
			//System.out.println(Thread.currentThread().isInterrupted()); // DEBUG
			//System.out.println(Thread.currentThread().isAlive()); // DEBUG
			//System.out.println(socketClient.isBound()); // DEBUG

		}
	}
}
