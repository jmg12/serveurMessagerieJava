import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

public class ClientChat extend Thread {
	BufferedReader inputConsole, inputNetwork;
	PrintStream outputConsole, outputNework;

	public static void main(String[] args) {
		new ClientChat(args);
	}
	public ClientChat (String[] args){
		initStreams(args); //try catch
		start();
		listenConsole();
	}
	public voir run() {
		listenNetwork();
	}
}
