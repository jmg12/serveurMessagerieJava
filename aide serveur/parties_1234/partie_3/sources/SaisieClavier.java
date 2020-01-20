import java.io.*;
import java.util.StringTokenizer;




// classe d'exemple qui va lire tous les entiers entrés au clavier, séparés par un espace,
// une tabulation ou ENTREE (on sort en tapant 2x sur ENTREE)
class SaisieClavier {


	public static void main( String[] args ) throws IOException, NumberFormatException {
		int somme = 0;
		String ligne;
		StringTokenizer st;
		BufferedReader entree = new BufferedReader( new InputStreamReader( System.in ) );
		ligne = entree.readLine();
		while( ligne.length() > 0 ) {
			st = new StringTokenizer( ligne );
			while( st.hasMoreTokens() )
				somme += Integer.parseInt( st.nextToken() );
			ligne = entree.readLine();
		}
		System.out.println( "somme=" + somme );
		entree.close();
	}


}
