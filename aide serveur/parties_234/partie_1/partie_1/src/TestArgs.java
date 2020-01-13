// classe testant la présence d'argument en ligne de commande

class TestArgs {


	public static void main( String[] args ) {
		if( args.length == 0 )
			System.out.println( "pas d'argument" );
		else
			System.out.println( "presence d'argument(s)" );
	}


}
