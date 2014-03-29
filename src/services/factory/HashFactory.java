package services.factory;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * La class HashFactory
 * @author Mag-stellon Nadarajah & Kamalraj Muruganathan
 *
 */
public class HashFactory {

	/**
	 * Cette méthode permet d'obtenir un cryptage en md5
	 * @param msg Le message à crypter
	 * @return Le message crypté en MD5
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	public static String hashMd5Variable(String msg) throws UnsupportedEncodingException, NoSuchAlgorithmException{

	
		StringBuilder msgBuf= new StringBuilder();
		msgBuf.append(msg);
		msgBuf.append(System.currentTimeMillis());
		String message= msgBuf.toString();
				
				
		byte[] bytesOfMessage = null;
		bytesOfMessage = message.getBytes("UTF-8");

		MessageDigest md = null;
		md = MessageDigest.getInstance("MD5");

		byte[] thedigest = md.digest(bytesOfMessage);

		BigInteger bigInt = new BigInteger(1,thedigest);
		String hashtext = bigInt.toString(16);

		return hashtext;

	}
	/**
	 * Cette méthode permet d'obtenir un cryptage en md5
	 * @param msg Le message à crypter
	 * @return Le message crypté en MD5
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static String hashMd5Statique(String msg) throws UnsupportedEncodingException, NoSuchAlgorithmException{
		
		byte[] bytesOfMessage = null;
		bytesOfMessage = msg.getBytes("UTF-8");

		MessageDigest md = null;
		md = MessageDigest.getInstance("MD5");

		byte[] thedigest = md.digest(bytesOfMessage);

		BigInteger bigInt = new BigInteger(1,thedigest);
		String hashtext = bigInt.toString(16);

		return hashtext;

	}

}
