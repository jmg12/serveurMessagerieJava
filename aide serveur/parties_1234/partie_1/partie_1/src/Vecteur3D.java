class Vecteur3D {
	
	
	protected int x,y,z;


	public Vecteur3D( int x, int y, int z ) {
		this.x = x;
		this.y = y;
		this.z = z;
	}


	public Vecteur3D( int v ) {
		this( v, v, v );
	}


	public Vecteur3D() {
		this( 0 );
	}


	public int getX() { 
		return x; 
	}
	
	
	public int getY() { 
		return y; 
	}

	
	public int getZ() { 
		return z; 
	}


	public void ajout( Vecteur3D v ) {
		x += v.getX();
		y += v.getY();
		z += v.getZ();
	}


	public String toString() {
		return "x=" + x + "\ty=" + y + "\tz=" + z;
	}


}
