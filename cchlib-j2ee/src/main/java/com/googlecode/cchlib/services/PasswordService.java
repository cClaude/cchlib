package com.googlecode.chclib.services;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import cx.ath.choisnet.util.base64.Base64Encoder;

/**
 * Encode password to hash code and then encode encode hash code
 * to base 64. 
 * 
 * @author Claude CHOISNET
 */
public final class PasswordService
{
  private static PasswordService instance;

  private PasswordService()
  {
  }

  /**
   * Encrypt password
   * 
   * @param plaintext password in plain text
   * @return encrypted password 
   * @throws SystemUnavailableException
   */
  public synchronized String encrypt( final String plaintext )
      throws SystemUnavailableException
  {
    MessageDigest md = null;

    try {
        md = MessageDigest.getInstance("SHA");
        }
    catch(NoSuchAlgorithmException e) {
        throw new SystemUnavailableException(e.getMessage());
        }
    
    try {
        md.update(plaintext.getBytes("UTF-8"));
        }
    catch(UnsupportedEncodingException e) {
        throw new SystemUnavailableException(e.getMessage());
        }

    byte raw[] = md.digest();
    //String hash = (new sun.misc.BASE64Encoder()).encode(raw);
    String hash;
    try {
        hash = Base64Encoder.encode( raw );
        }
    catch( UnsupportedEncodingException e ) {
        throw new SystemUnavailableException(e.getMessage());
        }

    return hash; 
  }

  /**
   * Returns PasswordService instance
   * 
   * @return PasswordService instance
   */
  public static synchronized PasswordService getInstance()
  {
    if( instance == null ) {
       instance = new PasswordService(); 
       }

    return instance;
  }
}
