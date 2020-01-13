import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.net.MalformedURLException;




// joue le .wav ou .au passé en argument
public class SoundApplication {
        

    public SoundApplication( String fichier ) {
    	URL codeBase = null;
    	String chosenFile = fichier;
    	AudioClip onceClip;
	URL cu = null;

        try {
            codeBase = new URL("file:" + System.getProperty("user.dir") + "/");
        } catch (MalformedURLException e) {
            System.err.println(e.getMessage());
        }

	try {
		cu = new URL( codeBase, chosenFile );
	} catch( Exception e ) {}

	onceClip = Applet.newAudioClip( cu );

        onceClip.play();     //Play it once.

	while( true );
    }


    public static void main( String[] args ) {
	new SoundApplication( args[0] );
    }


}
