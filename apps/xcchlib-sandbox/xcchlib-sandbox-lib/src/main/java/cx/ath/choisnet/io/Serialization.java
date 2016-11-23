/*
 ** -----------------------------------------------------------------------
 ** Nom           : cx/ath/choisnet/io/Serialization.java
 ** Description   :
 **
 **  3.01.002 2006.02.28 Claude CHOISNET - Version initiale
 ** -----------------------------------------------------------------------
 **
 ** cx.ath.choisnet.io.Serialization
 **
 */
package cx.ath.choisnet.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 ** Outils autour de la serialisation, cette classe permet, entre autre, de verifier si un object est "serializable" ou
 * pas
 **
 ** @author Claude CHOISNET
 ** @since 3.01.002
 ** @version 3.01.002
 **
 ** @see java.io.Serializable
 */
public class Serialization
{
    private Serialization()
    {
        // All static
    }

    /**
     ** Clone object using serialisation
     **
     ** @param anObject
     *            Object to clone,
     ** @param clazz
     *            Class of object to clone,
     **
     ** @return a new object
     **
     ** @throws IOException if any
     ** @throws ClassNotFoundException
     */
    public static <T> T clone( final T anObject, final Class<T> clazz ) // ------------------
            throws IOException, ClassNotFoundException
    {
        final byte[] byteArray;

        //
        // Serializing
        //
        try( final ByteArrayOutputStream output = new ByteArrayOutputStream() ) {
            try( final ObjectOutputStream oos = new ObjectOutputStream( output ) ) {
                oos.writeObject( anObject ); // serializing

                byteArray = output.toByteArray();
            }
        }

        //
        // De-serializing
        //
        final T objectClone;

        try( final ByteArrayInputStream input = new ByteArrayInputStream( byteArray ) ) {
            try( final ObjectInputStream ois = new ObjectInputStream( input ) ) {
                objectClone = clazz.cast( ois.readObject() ); // de-serializing
            }
        }

        return objectClone;
    }

    /**
     ** Copy an object in a byte array using serialisation
     **
     ** @param anObject
     *            Object to copy,
     **
     ** @return a array of bytes whithin an serialized object
     **
     ** @throws IOException if any
     */
    public static <T> byte[] toByteArray( // ----------------------------------
            final T anObject, final Class<T> clazz ) throws IOException
    {
        final byte[] bytes;

        //
        // Serializing
        //
        try( final ByteArrayOutputStream output = new ByteArrayOutputStream() ) {
            try( final ObjectOutputStream oos = new ObjectOutputStream( output ) ) {
                oos.writeObject( anObject ); // serializing

                bytes = output.toByteArray();
            }
        }

        return bytes;
    }

    /**
     ** Creat an object from a byte array using serialisation
     **
     ** @param aSerializedObject
     *            a byte array whithin an serialized object
     ** @param clazz
     *            Class of object to restaure,
     **
     ** @return a new object
     **
     ** @throws java.io.IOException
     ** @throws ClassNotFoundException
     */
    public static <T> T newFromByteArray( // ----------------------------------
            final byte[] aSerializedObject, final Class<T> clazz ) throws java.io.IOException, ClassNotFoundException
    {
        final T objectClone;

        //
        // De-serializing
        //
        try( final ByteArrayInputStream input = new ByteArrayInputStream( aSerializedObject ) ) {
           try( final ObjectInputStream ois = new ObjectInputStream( input ) ) {
               objectClone = clazz.cast( ois.readObject() ); // de-serializing
           }
        }

        return objectClone;
    }

} // class
