package it.egeos.geoserver.utils;

import org.jasypt.digest.StandardByteDigester;
import org.apache.commons.codec.binary.Base64;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * 
 * @author Federico C. Guizzardi - cippinofg <at> gmail.com
 * 
 * This class implements the encoding made by geoserver (Weak PBE).
 *
 * To use it, is enough call 
 * 
 * PasswordEncoder.encode("apassword")
 * 
 * to get an encoded string.
 */

public class PasswordEncoder {	
	/*
	 * Main method to get an encoded password from raw
	 */
	public static String encoder(String passwd){		
        StandardByteDigester digester = new StandardByteDigester();
        digester.setAlgorithm("SHA-256");
        digester.setIterations(100000);
        digester.setSaltSizeBytes(16);
        digester.initialize();
        return "digest1:"+new String(Base64.encodeBase64(digester.digest(toBytes(passwd.toCharArray()))));
	}

	/*
	 * converter from char[] to byte[]
	 */
    private static byte[] toBytes(char[] ch) {
    	Charset charset= Charset.defaultCharset();
        ByteBuffer buff = charset.encode(CharBuffer.wrap(ch));
        byte[] tmp = new byte[buff.limit()];
        buff.get(tmp);
        return tmp;    
    }
}
