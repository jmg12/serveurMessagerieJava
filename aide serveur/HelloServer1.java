import java.net.*;
import java.io.*;

class HelloServer1 { 
	public static void main(String argv[]) throws Exception
	{
		//On installe la combine sur le numero de telephone
		ServerSocket serversocket = new ServerSocket(1111);

		//On attend les appels entrants
		Socket socket = serversocket.accept();

		//On ouvre un tube pour envoyer un message
		PrintStream out = new PrintStream( socket.getOutputStream() );
		
		//On ouvre un tube pour lire ce que nous envoie le client
		InputStreamReader in = new InputStreamReader( socket.getInputStream() );

		//On ecrit et on envoie le message
		out.println( "Hello World!" );

		// Lire fichier index.html
		try {
		out.println("TEST");
		String rfile = readFile("index.html");
		out.println(rfile);

		} catch (IOException e) {
			System.out.println(e);
		}
		//On raccroche
		socket.close();
	}

	//Implementation fonction readFile
	public static String readFile(String file) throws Exception {
		String fichier="";
		BufferedReader buf = new BufferedReader (new FileReader(file));
		String read = buf.readLine();
		while ( read != null){
			fichier = fichier + read;
		}
		buf.close();
		return fichier; 

		/*InputStream in = new FileInputStream(file);
		int data = in.read();
		while (data != -1) {
			data = in.read();
		}
		in.close();
		*/
	}
}
