//classe affichant les arguments éventuels passés en ligne de commande

class AfficheArgs {


	public static void main( String[] args ) {
		if( args.length == 0 )
			System.out.println( "pas d'argument" );
		else for( int i=0; i < args.length; i++ )
			System.out.println( "argument " + (i+1) + ": " + args[i] );
	}


}
