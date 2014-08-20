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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
** Outils autour de la serialisation, cette classe permet, entre autre,
** de verifier si un object est "serializable" ou pas
**
** @author Claude CHOISNET
** @since   3.01.002
** @version 3.01.002
**
** @see java.io.Serializable
*/
public class Serialization
{

/**
** Clone object using serialisation
**
** @param anObject  Object to clone,
** @param clazz     Class of object to clone,
**
** @return a new object
**
** @throws java.io.IOException
** @throws ClassNotFoundException
*/
public static <T> T clone( T anObject, Class<T> clazz ) // ------------------
    throws
        java.io.IOException,
        ClassNotFoundException
{
 //
 // Serializing
 //
 ByteArrayOutputStream  output  = new ByteArrayOutputStream();
 ObjectOutputStream     oos     = new ObjectOutputStream( output );

 oos.writeObject( anObject ); // serializing
 oos.flush();
 oos.close();

 //
 // De-serializing
 //
 ByteArrayInputStream   input   = new ByteArrayInputStream( output.toByteArray() );
 ObjectInputStream      ois     = new ObjectInputStream( input );

 T objectClone = clazz.cast( ois.readObject() ); // de-serializing

 ois.close();

 return objectClone;
}

/**
** Copy an object in a byte array using serialisation
**
** @param anObject  Object to copy,
**
** @return a array of bytes whithin an serialized object
**
** @throws java.io.IOException
*/
public static <T> byte[] toByteArray( // ----------------------------------
    T           anObject,
    Class<T>    clazz
    )
    throws java.io.IOException
{
 //
 // Serializing
 //
 ByteArrayOutputStream  output  = new ByteArrayOutputStream();
 ObjectOutputStream     oos     = new ObjectOutputStream( output );

 oos.writeObject( anObject ); // serializing
 oos.flush();
 oos.close();

 return output.toByteArray();
}

/**
** Creat an object from a byte array using serialisation
**
** @param aSerializedObject a byte array whithin an serialized object
** @param clazz             Class of object to restaure,
**
** @return a new object
**
** @throws java.io.IOException
** @throws ClassNotFoundException
*/
public static <T> T newFromByteArray( // ----------------------------------
    byte[]      aSerializedObject,
    Class<T>    clazz
    )
    throws
        java.io.IOException,
        ClassNotFoundException
{
 //
 // De-serializing
 //
 ByteArrayInputStream   input   = new ByteArrayInputStream( aSerializedObject );
 ObjectInputStream      ois     = new ObjectInputStream( input );

 T objectClone = clazz.cast( ois.readObject() ); // de-serializing

 ois.close();

 return objectClone;
}

} // class
