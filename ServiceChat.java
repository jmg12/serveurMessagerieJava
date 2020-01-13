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
	//static String[] logins = null;
	static ArrayList<String> logins = new ArrayList<>();

	public ServiceChat ( Socket socket ) {
		this.socket = socket;
		this.start();
	}

	public String choixLogin() {
		try {
			output.println("Bienvenue au serveur de messagerie\n\rVeuillez entrer votre login :");
			System.out.println(logins);
			String ulogin = entree.readLine();
			output.println("Votre nouveau login est : "+ ulogin);

			//for (String login: logins ) {
			for ( int login =0 ; login < logins.size(); login++ ) {
				System.out.println("TEST");
				//if ( logins[login] == ulogin ) {
				if ( logins.contains(ulogin) ) {
					output.println("Ce login est d�j� pris, veuillez choisir un autre :");
					ulogin = entree.readLine();
				} else {
					System.out.println("TEST2");
					//logins[login] = ulogin;
					logins.add(ulogin);
				}

				/*while (ulogin == login) {
					output.println("Ce login est d�j� pris, veuillez choisir un autre :");
					ulogin = entree.readLine();
				}*/	
			};

			System.out.println(logins);
			return ulogin;

		} catch ( IOException e ) {
			System.out.println( "probl�me dans la lecture du login" );
			return "";
		}
	}
	
	public boolean initStreams() {
		try {

			// On ouvre le tube pour lire le message du client
			entree = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );

			// Tube pour envoyer un message � un client
			output = new PrintStream( socket.getOutputStream() );

			// Choix du pseudo
			choixLogin();

			if (nbUsers == NBUSERSMAX) {
				output.println("Nombre de connexion maximal atteind");
				socket.close();
				return false;
			}

			// On ouvre le tube pour communiquer avec tout le monde
			outputs[nbUsers] = new PrintStream( socket.getOutputStream() );

			//incr�mentation des utilisateurs qui se connecte
			nbUsers++;	
			
		} catch( IOException e ) {
			try {
				socket.close();
			} catch( IOException e2 ) {
				System.out.println( "probl�me en fermant socket" );
			}
		}

		return true;
	}

	public void mainLoop() {

		while( true ) {

			try {	
				// Lire donn�e envoy� par le client au serveur
				msg = entree.readLine();
			} catch (IOException e) {
				System.out.println(e);
			}

			// Envoie le message � tous les clients
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
