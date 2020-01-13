class FooVecteur3D {


	public static void main( String[] args ) {
		Vecteur3D v1 = new Vecteur3D( 1, 2, 3 );
		Vecteur3D v2 = new Vecteur3D( 4, 5, 6 );
		System.out.println( "v1:\t" + v1 );
		System.out.println( "v2:\t" + v2 );
		v1.ajout( v2 );
		System.out.println( "v1+v2:\t" + v1 );
	}


}
