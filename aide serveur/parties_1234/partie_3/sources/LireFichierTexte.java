import java.io.*;




// affiche le fichier texte passé en argument
class LireFichierTexte {


	public static void main( String[] args ) throws IOException {
		BufferedReader lecteur = null;
		String ligne;

		try {
			lecteur = new BufferedReader( new FileReader( args[0] ) );
		} catch( FileNotFoundException exc ) {
			System.out.println( "le fichier n'existe pas" );
			System.exit( 1 );
		}

		while( (ligne=lecteur.readLine()) != null)
			System.out.println( ligne );
		lecteur.close();
	}


}
