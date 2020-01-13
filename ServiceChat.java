import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;
//import ServiceChat.class;

public class ServiceChat extends Thread {

	Socket socket;
	final static int NBUSERSMAX = 3;
	BufferedReader entree = null;
	PrintStream output = null;
	static PrintStream[] outputs = new PrintStream[NBUSERSMAX];
	static int nbUsers = 0;
	String msg = "";

	public ServiceChat ( Socket socket ) {
		this.socket = socket;
		this.start();
	}

	public boolean initStreams() {
		try {
			// On ouvre le tube pour lire le message du client
			entree = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );

			// Tube pour envoyer un message à un client
			output = new PrintStream( socket.getOutputStream() );

			if (nbUsers == NBUSERSMAX) {
				output.println("Nombre de connexion maximal atteind");
				socket.close();
				return false;
			}

			// On ouvre le tube pour communiquer avec tout le monde
			outputs[nbUsers] = new PrintStream( socket.getOutputStream() );

			//incrémentation des utilisateurs qui se connecte
			nbUsers++;	
			
		} catch( IOException e ) {
			try {
				socket.close();
			} catch( IOException e2 ) {
				System.out.println( "problème en fermant socket" );
			}
		}

		return true;
	}

	public void mainLoop() {

		while( true ) {

			try {	
				// Lire donnée envoyé par le client au serveur
				msg = entree.readLine();
			} catch (IOException e) {
				System.out.println(e);
			}

			// Envoie le message à tous les clients
			for (int i=0 ; i < nbUsers ; i++) {
				outputs[i].println(msg);
			}

		}

	}

	public void run() {
		if (initStreams()) 
			mainLoop();

	}
}
