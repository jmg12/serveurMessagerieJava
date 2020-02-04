package applet;


import javacard.framework.*;




public class TheApplet extends Applet {


	static final byte UPDATECARDKEY				= (byte)0x14;
	static final byte UNCIPHERFILEBYCARD			= (byte)0x13;
	static final byte CIPHERFILEBYCARD			= (byte)0x12;
	static final byte CIPHERANDUNCIPHERNAMEBYCARD		= (byte)0x11;
	static final byte READFILEFROMCARD			= (byte)0x10;
	static final byte WRITEFILETOCARD			= (byte)0x09;
	static final byte UPDATEWRITEPIN			= (byte)0x08;
	static final byte UPDATEREADPIN				= (byte)0x07;
	static final byte DISPLAYPINSECURITY			= (byte)0x06;
	static final byte DESACTIVATEACTIVATEPINSECURITY	= (byte)0x05;
	static final byte ENTERREADPIN				= (byte)0x04;
	static final byte ENTERWRITEPIN				= (byte)0x03;
	static final byte READNAMEFROMCARD			= (byte)0x02;
	static final byte WRITENAMETOCARD			= (byte)0x01;
	short SW_VERIFICATION_FAILED = (short) 0x6300;
	short SW_PIN_VERIFICATION_REQUIRED = (short) 0x6301;
	short NVRSIZE = (short)1024;
	byte[] NVR = new byte[NVRSIZE];

	protected TheApplet() {

		this.register();
	}


	public static void install(byte[] bArray, short bOffset, byte bLength) throws ISOException {
		new TheApplet();
	}


	public boolean select() {
		return true;
	}


	public void deselect() {
	}

	// méthode qui va déverrouiller  le code pin
	void verify( APDU apdu, OwnerPIN pin ) {
		apdu.setIncomingAndReceive();
		byte[] buffer = apdu.getBuffer();
		if( !pin.check( buffer, (byte)5, buffer[4] ) )
			ISOException.throwIt( SW_VERIFICATION_FAILED );
	}

	public void process(APDU apdu) throws ISOException {
		if( selectingApplet() == true )
			return;

		byte[] buffer = apdu.getBuffer();

		switch( buffer[1] ) 	{
			case UPDATECARDKEY: updateCardKey( apdu ); break;
			case UNCIPHERFILEBYCARD: uncipherFileByCard( apdu ); break;
			case CIPHERFILEBYCARD: cipherFileByCard( apdu ); break;
			case CIPHERANDUNCIPHERNAMEBYCARD: cipherAndUncipherNameByCard( apdu ); break;
			case READFILEFROMCARD: readFileFromCard( apdu ); break;
			case WRITEFILETOCARD: writeFileToCard( apdu ); break;
			case UPDATEWRITEPIN: updateWritePIN( apdu ); break;
			case UPDATEREADPIN: updateReadPIN( apdu ); break;
			case DISPLAYPINSECURITY: displayPINSecurity( apdu ); break;
			case DESACTIVATEACTIVATEPINSECURITY: desactivateActivatePINSecurity( apdu ); break;
			case ENTERREADPIN: enterReadPIN( apdu ); break;
			case ENTERWRITEPIN: enterWritePIN( apdu ); break;
			case READNAMEFROMCARD: readNameFromCard( apdu ); break;
			case WRITENAMETOCARD: writeNameToCard( apdu ); break;
			default: ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
	}


	void updateCardKey( APDU apdu ) {
	}


	void uncipherFileByCard( APDU apdu ) {
	}


	void cipherFileByCard( APDU apdu ) {
	}


	void cipherAndUncipherNameByCard( APDU apdu ) {
	}


	void readFileFromCard( APDU apdu ) {
	}


	void writeFileToCard( APDU apdu ) {
	}


	void updateWritePIN( APDU apdu ) {
	}


	void updateReadPIN( APDU apdu ) {
	}


	void displayPINSecurity( APDU apdu ) {
	}


	void desactivateActivatePINSecurity( APDU apdu ) {
	}


	void enterReadPIN( APDU apdu ) {
	}


	void enterWritePIN( APDU apdu ) {
		byte[] buffer = apdu.getBuffer();
		// Receive data from the client
		apdu.setIncomingAndReceive();

		byte[] pincode = new byte[4];
		Util.arrayCopy(buffer,(short)4,pincode,(short)0,pincode.length);

		OwnerPIN pin = new OwnerPIN((byte)3,(byte)8);  				// 3 tries 8=Max Size
		pin.update(pincode,(short)0,(byte)4); 				// from pincode, offset 0, length 4

		byte[] buffer = apdu.getBuffer();

		if ( ! pin.isValidated() )
			ISOException.throwIt(SW_PIN_VERIFICATION_REQUIRED);
			// Copy the data receive from the buffer in memory of the card
			Util.arrayCopy(buffer,(short)4,NVR,(short)0,pincode.length);
	}


	void readNameFromCard( APDU apdu ) {

		byte[] buffer = apdu.getBuffer();

		// Copy the data from memory of the card to the buffer
		Util.arrayCopy(NVR, (short)0, buffer, (short)0, NVR[1]);
		// Send the data to the client
		apdu.setOutgoingAndSend( (byte)1, NVR[0] );
	}


	void writeNameToCard( APDU apdu ) {

		byte[] buffer = apdu.getBuffer();

		// Receive data from the client
		apdu.setIncomingAndReceive();
		// Size data + Lc ; Lc étant le bit avec la taille de data
		short sapdu = (short)(buffer[4]+1);
		// Copy the data receive from the buffer in memory of the card
		Util.arrayCopy(buffer,(short)4,NVR,(short)0,sapdu);

	}


}
