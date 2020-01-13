//classe affichant la moyenne des entiers passés en argument en ligne de commande

class Moyenne {


	public static void main( String[] args ) {
		if( args.length == 0 )
			System.out.println( "pas d'argument" );
		else {
			int somme=0;
			for( int i=0; i < args.length; i++ ) 
				somme += Integer.parseInt( args[i] );
			System.out.println( "moyenne = " + ((double)somme/args.length) );
		}
	}


}
