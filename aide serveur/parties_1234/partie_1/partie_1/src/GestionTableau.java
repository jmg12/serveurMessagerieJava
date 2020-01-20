//classe remplissant un tableau d'entiers avec les arguments passés en ligne de commande, puis l'affiche

class GestionTableau {


	public static void main( String[] args ) {
		if( args.length == 0 )
			System.out.println( "pas d'argument" );
		else {
			int[] tab = new int[args.length];
			for( int i=0; i < args.length; i++ ) 
				tab[i] = Integer.parseInt( args[i] );
			for( int i=0; i < args.length; i++ ) 
				System.out.println( "case indice " + i + ": " + tab[i] );
		}
	}


}
