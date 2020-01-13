import java.net.*;
import java.io.*;




class TCPClient {


	public static void main( String[] args ) {
		int port = 12345;
		BufferedReader lecteurFichier;
		BufferedReader entree;
		PrintStream sortie;
		String ligne;
		Socket socket;

		try {
			lecteurFichier = new BufferedReader( new FileReader( args[0] ) );
			socket = new Socket( args[1], port );
			entree = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
			sortie = new PrintStream( socket.getOutputStream() );

			while( (ligne = lecteurFichier.readLine()) != null )
				sortie.println( ligne );
			sortie.println( "xxxxxx" );
			System.out.println( entree.readLine() );
			socket.close();
		} catch( FileNotFoundException e ) {
			System.out.println( "fichier introuvable" );
		} catch( UnknownHostException e ) {
			System.out.println( "destinataire inconnu" );
		} catch( IOException e ) {
			System.out.println( "probleme d'entree/sortie" );
		}
	}


}
