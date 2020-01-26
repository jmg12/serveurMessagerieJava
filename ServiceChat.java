import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;
import java.util.regex.*;
//import ServiceChat.class;

public class ServiceChat extends Thread {

	Socket socket;
	final static int NBUSERSMAX = 3;
	BufferedReader entree = null;
	PrintStream output = null;
	static PrintStream[] outputs = new PrintStream[NBUSERSMAX];
	static int nbUsers = 0;
	String msg = "";
	static ArrayList<String> logins = new ArrayList<>();
	int id;

	public ServiceChat ( Socket socket ) {
		this.socket = socket;
		this.start();
	}

	public String choixLogin() {
		try {
			//output.println("Bienvenue au serveur de messagerie\n\rVeuillez entrer votre login :");
			output.println("Veuillez entrer votre login :");
			String ulogin = entree.readLine();
			output.println("Votre login est : "+ ulogin);

			if ( logins.isEmpty() ) {
				logins.add(ulogin);
			} 
			else {
				while( logins.contains(ulogin) ) {
					output.println("Ce login est d�j� pris, veuillez choisir un autre :");
					ulogin = entree.readLine();
				}
				logins.add(ulogin);
			}
			return ulogin; // to remove it will be void func

		} catch ( IOException e ) {
			System.out.println( "probl�me dans la lecture du login" );
			return ""; // to remove it will be void func
		}
	}
	
	public void sendMsgAll( String client, String msg ) {

		for (int i=0 ; i < nbUsers ; i++) {
			if ( id != i ) {
				outputs[i].println("<" + client + "> "+ msg);
			}
		}
	}

	public void bienvenue( String client ) {
			for (int i=0 ; i <= nbUsers ; i++) {

				int Totuser = nbUsers+1;

				if ( id == i ) {
					output.println("Bienvenue au serveur de messagerie ESIEA :)");
					output.println("Il y a " + Totuser + " d'utilisateurs connect�s dans le serveur.");
					output.println("Voici la liste : \n" + logins);
				}
				else {
					outputs[i].println("<System> L'utilisateur " + client + " viens de rejoindre le serveur");
				}
			}
	}

	public boolean initStreams() {
		try {

			// On ouvre le tube pour lire le message du client
			entree = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );

			// Tube pour envoyer un message � un client
			output = new PrintStream( socket.getOutputStream() );

			if (nbUsers == NBUSERSMAX) {
				output.println("Nombre de connexion maximal atteind");
				socket.close();
				return false;
			}

			// Choix du pseudo
			choixLogin();

			// Creation de l'id par rapport au user actuel
			id = nbUsers;

			// On ouvre le tube pour communiquer avec tout le monde
			outputs[id] = new PrintStream( socket.getOutputStream() );

			//Annonce de bienvenue
			bienvenue(logins.get(id));

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

	public void sendMsgPriv( String client, int dest, String msg ) {

		for (int i=0 ; i < nbUsers ; i++) {
			if ( dest == i ) {
				outputs[dest].println("<" + client + "> "+ msg);
			}
		}
	}
	
	public void mainLoop() {

		while( true ) {

			try {	
				// Lire donn�e envoy� par le client au serveur
				msg = entree.readLine();
			} catch (IOException e) {
				System.out.println(e);
			}

			// type de msg
			System.out.println(msg.getClass().getName());
			
			// creation d'un tableau pour le msg re�ue
			char[] msgsend = new char[msg.length()];
			
			// rempli�age du tableau msgsend
			for (int i = 0; i < msg.length(); i++) {
				msgsend[i] = msg.charAt(i);
			}

			// afficher le contenue du tableau
			for (char c : msgsend) {
				System.out.println(c);
			}

			// API regex java match /sendMsg
			String patternSendMsg = "^/sendMsg.*";
			Pattern pattern = Pattern.compile(patternSendMsg); 
			Matcher matcher = pattern.matcher(msg);
			boolean matches = matcher.matches();
			//System.out.println(matches);

			if ( matches ) {
				// Envoie de message priv�
				System.out.println("TESTL!!!");
				sendMsgPriv(logins.get(id), 1 ,msg);
			}
			else {
				// Envoie le message � tous les clients
				sendMsgAll(logins.get(id), msg);
			}

		}

	}

	public void run() {
		if (initStreams()) 
			mainLoop();

	}
}
