import java.io.*;




class EcrireFichierTexte {


	public static void main( String[] args ) throws IOException {
		PrintWriter ecrivain;
		int n=5;
		ecrivain = new PrintWriter( new BufferedWriter( new FileWriter( args[0] ) ) );
		ecrivain.print( "entier: " );
		ecrivain.println( n );
		ecrivain.print( "entier: " );
		ecrivain.println( 2*n );
		ecrivain.close();
	}


}
