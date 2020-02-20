import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;
import java.util.regex.*;
import java.util.HashMap;

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
	static ArrayList<String> mots2passe = new ArrayList<>();
	static HashMap<String,String> bd = new HashMap<String, String>();
	static HashMap<Integer, Integer> idPort = new HashMap<Integer, Integer>();
	boolean flagCheckMdp = false;
	static int indexDecoId;
	static boolean flagDeco = false;

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
			// effectue un check du mot de passe a la reconnexion
			else if ( bd.containsKey(ulogin) ) { 
				// Si le logins est déjà connecté
				if (logins.contains(ulogin) == true) {
					while( logins.contains(ulogin) ) {
						output.println("Ce login est deja existant et connecte, veuillez choisir un autre login :");
						ulogin = entree.readLine();
					}
					logins.add(ulogin);
					return ulogin; // to remove it will be void func
				}
				output.println("Vous avez deja un compte :) ");
				checkPassword();
				logins.add(ulogin);
			}
			else {
				logins.add(ulogin);
			}
			return ulogin; // to remove it will be void func

		} catch ( IOException e ) {
			System.out.println( "probleme dans la lecture du login" );
			return ""; // to remove it will be void func
		}
	}

	public String choixLoginDecoId(int indexId) {
		try {
			//output.println("Bienvenue au serveur de messagerie\n\rVeuillez entrer votre login :");
			output.println("Veuillez entrer votre login :");
			String ulogin = entree.readLine();
			output.println("Votre login est : "+ ulogin);

			if ( logins.isEmpty() ) {
				//logins.add(indexId, ulogin);
				logins.set(indexId, ulogin);
			} 
			// effectue un check du mot de passe a la reconnexion
			else if ( bd.containsKey(ulogin) ) { 
				// Si le logins est déjà connecté
				if (logins.contains(ulogin) == true) {
					while( logins.contains(ulogin) ) {
						output.println("Ce login est deja existant et connecte, veuillez choisir un autre login :");
						ulogin = entree.readLine();
					}
					logins.set(indexId, ulogin);
					return ulogin; // to remove it will be void func
				}
				output.println("Vous avez deja un compte :) ");
				checkPassword();
				//logins.add(indexId, ulogin);
				logins.set(indexId, ulogin);
			}
			else {
				logins.set(indexId, ulogin);
			}
			return ulogin; // to remove if void func

		} catch ( IOException e ) {
			System.out.println( "probleme dans la lecture du login" );
			return ""; // to remove if void func
		}
	}

	public boolean checkPassword () {
		int c = 0;
		try {
			while ( c < 3) {
				output.println("Veuillez entrer votre mot de passe :");
				String umot2passe = entree.readLine();

				// check si mot de passe existe dans la bd
				if ( bd.containsValue(umot2passe) ) {
					// ajout du mdp saissie dans la base de mdp local 
					mots2passe.add(umot2passe);
					// Set flag check mdp a true
					flagCheckMdp = true;
					return true;
				}
				c++;
			}
			output.println("Nombre de tentative maximal atteind");

			while(socket.isBound()) {
				entree.close();
				output.close();
				socket.close();
			}
			return false;

		} catch ( IOException e ) {
			System.out.println( "probleme lors de la verification du mot de passe" );
			return false;
		}
	}

	public String choixMotDePasse() {
		try {
			output.println("Veuillez entrer votre nouveau mot de passe :");
			String umot2passe = entree.readLine();
			mots2passe.add(umot2passe);
			return umot2passe;

		} catch ( IOException e ) {
			System.out.println( "probleme dans la lecture du mot de passe" );
			return ""; // to remove it will be void func
		}
	}
	
	public String choixMotDePasseDecoId(int indexId) {
		try {
			output.println("Veuillez entrer votre nouveau mot de passe :");
			String umot2passe = entree.readLine();
			//mots2passe.add(indexId, umot2passe);
			mots2passe.set(indexId, umot2passe);
			return umot2passe;

		} catch ( IOException e ) {
			System.out.println( "probleme dans la lecture du mot de passe" );
			return ""; // to remove if void func
		}
	}
	
	public void bienvenue( String client ) {

		for (int i=0 ; i <= nbUsers ; i++) {

			int Totuser = nbUsers+1;
			if ( id == i ) {
				output.println("Bienvenue au serveur de messagerie ESIEA :)");
				output.println("Il y a " + Totuser + " d'utilisateurs connectes dans le serveur.");
				output.println("Voici la liste : \n" + logins);
			}
			else {
				outputs[i].println("<System> L'utilisateur " + client + " viens de rejoindre le serveur");
			}
		}
	}

	public void sendMsgPriv( String client, String destname, String msg ) {
		//System.out.println(logins.contains(destname)); // DEBUG
		if(logins.contains(destname)) {
			int destID = logins.indexOf(destname);
			for (int i=0 ; i < nbUsers ; i++) {
				//System.out.println(i+ "= i"); // DEBUG
				//System.out.println(destID+ " = destID"); // DEBUG
				if ( destID == i ) {
					outputs[destID].println("<" + client + "> "+ msg);
				}
			}
		}
		else{
			output.println("L'utilisateur "+ destname +" n'est pas dans le serveur");
		}
	}
	
	public void sendMsgAll( String client, String msg ) {

		for (int i=0 ; i < nbUsers ; i++) {
			if ( id != i ) {
				outputs[i].println("<" + client + "> "+ msg);
			}
		}
	}

	public void addToBD(String login, String mdp) {
		bd.put(login,mdp);
	}

	public void deconnexionLogin(String myLogin) {
		// flag decoconnexion; après déplacer sous le while
		flagDeco = true;
		int myID = logins.indexOf(myLogin);
		indexDecoId = myID;
		System.out.println("Index Deco ID : "+ indexDecoId); // DEBUG
		//System.out.println("Going to remove : "+logins.get(myID)); // DEBUG
		//System.out.println("BD LOCAL logins Avant : "+logins); // DEBUG
		//System.out.println("BD LOCAL mdp Avant : "+mots2passe); // DEBUG
		
		// Afin de liberer un place dans logins, remplacement du user
		logins.set(myID, "");
		mots2passe.set(myID, "");

		//logins.remove(logins.get(myID));
		//mots2passe.remove(mots2passe.get(myID));
		
		// Decrementer le nombre d'utilisateur
		nbUsers--;
		
		//System.out.println("Apres : "+logins); // DEBUG
		//System.out.println("Apres : "+mots2passe); // DEBUG
		//System.out.println("Nb USER : " + nbUsers); // DEBUG
		System.out.println("My BD : "+bd); // DEBUG
		System.out.println("BD LOCAL logins : " + logins); // DEBUG
		System.out.println("BD LOCAL mdp : " + mots2passe); // DEBUG


		boolean isAlive = true; // après déplacer en haut
		try {
			//while(socket.isBound()) {
			while(isAlive) {
				entree.close();
				output.close();
				socket.close();
				isAlive = false; // tmp set to true; finir deco id
			}
			System.out.println("DON'T PRINT");
			System.out.println("OUT WHILE!!!");
		} catch ( IOException e ) {
			System.out.println( "probleme dans la deconnexion" );
		}
	}

	public boolean initStreams() {
		try {

			// On ouvre le tube pour lire le message du client
			entree = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );

			// Tube pour envoyer un message à un client
			output = new PrintStream( socket.getOutputStream() );

			// Nombre d'utilisateur maximal atteind
			if (nbUsers == NBUSERSMAX) {
				output.println("Nombre de connexion maximal atteind");
				socket.close();
				return false;
			}

			if (flagDeco == true) {
				//System.out.println("Index Deco ID : "+ indexDecoId); // DEBUG
				//System.out.println( "ajout id apres deco" ); // DEBUG
				id = indexDecoId;
			} else {
				//System.out.println( "ajout id normal" ); // DEBUG
				id = nbUsers;
			}

			// On ouvre le tube pour communiquer avec tout le monde
			outputs[id] = new PrintStream( socket.getOutputStream() );

		} catch( IOException e ) {
			try {
				socket.close();
				socket.isClosed();
			} catch( IOException e2 ) {
				System.out.println( "probleme en fermant socket" );
			}
		}

		return true;
	}

	public boolean connection() {
		if (flagDeco == true) {
			//System.out.println("Je passe par flag deco"); // DEBUG

			// Choix du pseudo
			String loginToBD = choixLoginDecoId(indexDecoId);

			// Choix du mot de passe
			if ( flagCheckMdp == false ) {
				String mdpToBD = choixMotDePasseDecoId(indexDecoId);
				// Ajout login et mot de passe dans la base de donnee
				addToBD(loginToBD, mdpToBD);
			}
			flagCheckMdp = false;
			flagDeco = false;
		} else { 
			//System.out.println("Connexion normal"); // DEBUG

			// Choix du pseudo
			String loginToBD = choixLogin();

			// Choix du mot de passe
			if ( flagCheckMdp == false ) {
				String mdpToBD = choixMotDePasse();
				// Ajout login et mot de passe dans la base de donnee
				addToBD(loginToBD, mdpToBD);
			}
			flagCheckMdp = false;
		}

		//Annonce de bienvenue
		bienvenue(logins.get(id));

		//incrémentation des utilisateurs qui se connecte
		nbUsers++;

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

			// echo sur le serveur
			System.out.println(msg);

			// type de msg
			//System.out.println(msg.getClass().getName()); // DEBUG
			
			// split des commandes
			String[] cmdPattern = msg.split("\\s+",3);

			// lister tous les logins connectes
			String[] listLogins = msg.split("\\s+",2);

			if ( cmdPattern[0].equals("/sendMsg") && cmdPattern[2] != null ) {

				// Envoie de message prive
				sendMsgPriv(logins.get(id), cmdPattern[1], cmdPattern[2]);
			}
			else if ( cmdPattern[0].equals("/exit") ) {

				// Deconnexion de l'utilisateur
				deconnexionLogin(logins.get(id));
			}
			else if ( listLogins[0].equals("/list") ) {

				// Afficher tous les logins
				output.println("Voici la liste : \n" + logins);
			}
			else {
				/*try{
					Thread.sleep(1);
				}
				catch (Exception e) 
				{ 
					System.out.println("Thread  interrupted."); 
				} */
				
				//System.out.println("My ID in msgAll : " + id);  // DEBUG

				// Envoie le message a tous les clients
				sendMsgAll(logins.get(id), msg);
			}

		}

	}

	public void run() {
		if (initStreams()) 
			if(connection())
				mainLoop();
	}
}
