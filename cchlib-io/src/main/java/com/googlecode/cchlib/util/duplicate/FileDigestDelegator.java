package com.googlecode.cchlib.util.duplicate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;

// not public
final class FileDigestDelegator implements Serializable {

     private static final long serialVersionUID = 1L;
     private transient MessageDigest md;
     private byte[] lastDigest = null;

     FileDigestDelegator(
         final MessageDigest messageDigest
         )
     {
         this.md  = messageDigest;
     }

     //Serializable
     private void writeObject( final ObjectOutputStream out )
         throws IOException
     {
         out.defaultWriteObject();
         out.writeUTF( getAlgorithm() );
     }

     //Serializable
     private void readObject( final ObjectInputStream in )
         throws IOException, ClassNotFoundException
     {
         in.defaultReadObject();
         final String algorithm = in.readUTF();

         try {
             this.md = MessageDigest.getInstance( algorithm );
             }
         catch( final NoSuchAlgorithmException e ) {
             throw new ClassNotFoundException( algorithm, e );
             }
     }

     /**
      * Completes the hash computation by performing final
      * operations such as padding. The digest is reset after
      * this call is made.
      * <p>
      * Cache result that could be retrieve using {@link #digest()}
      * or {@link #digestString()}.
      * <p>
      * @return the array of bytes for the resulting hash value.
      * @see java.security.MessageDigest#digest()
      * @throws IllegalStateException if digest not yet
      *         initialized
      */
     public byte[] digestDelegator()
     {
         this.lastDigest = this.md.digest();
         return this.lastDigest;
     }

     /**
      * Returns a string that identifies the algorithm, independent
      * of implementation details.
      *
      * @return the name of the algorithm.
      * @see java.security.MessageDigest#getAlgorithm()
      */
     public final String getAlgorithm()
     {
         // The name should be a standard Java
         // Security name (such as "SHA", "MD5", and so on).
         // See Appendix A in the Java Cryptography Architecture
         // API Specification & Reference for information
         // about standard algorithm names.
         return this.md.getAlgorithm();
     }

     /**
      * Returns the length of the digest in bytes, or 0 if
      * this operation is not supported by the provider
      * and the implementation is not cloneable
      *
      * @return the length of the digest in bytes.
      * @see java.security.MessageDigest#getDigestLength()
      */
     public final int getDigestLength()
     {
         return this.md.getDigestLength();
     }

     /**
      * Returns the provider of message digest object.
      * @return the provider of message digest object.
      * @see java.security.MessageDigest#getProvider()
      */
     public final Provider getProvider()
     {
         return this.md.getProvider();
     }

     /**
      * @see java.security.MessageDigest#reset()
      */
     protected void reset()
     {
         this.lastDigest = null;
         this.md.reset();
     }

     /**
       * @see java.security.MessageDigest#update(byte[], int, int)
      */
     public void update( final byte[] input, final int offset, final int len )
     {
         this.md.update( input, offset, len );
     }

     /**
      * @param input
      * @see java.security.MessageDigest#update(java.nio.ByteBuffer)
      */
     protected final void update( final ByteBuffer input )
     {
         this.md.update( input );
     }

     /**
      * Return last hash computation
      *
      * @return the array of bytes for the resulting hash value.
      * @throws IllegalStateException if digest not yet
      *         initialized
      */
     public byte[] digest()
     {
         if( this.lastDigest == null ) {
             throw new IllegalStateException();
             }

         return this.lastDigest;
     }
 }