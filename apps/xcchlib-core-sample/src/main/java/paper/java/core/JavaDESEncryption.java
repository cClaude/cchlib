package paper.java.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import com.googlecode.cchlib.io.FileHelper;

/**
 * <pre>
 * Java DES Encryption Decryption File Tutorial
 *
 * Filed in: Core Java, security Tags: DES, File Encryption
 * In this tutorial we are going to see how can you Encrypt and Decrypt a
 * file in Java, using the DES encryption algorithm. DES (Data Encryption
 * Standard) is a block cipher algorithm. It’s one of the most basic
 * symmetric encryption mechanisms, which means that both the encryptor
 * and the decryptor has to know the secret key in order to perform their
 * respective actions.

 * So the basic steps of this tutorial are :
 *
 * + Generate a secure, secret key using a KeyGnerator
 * + Create one DES Chiper to encrypt and one to decrypt,
 *   using the same secret key as well as specifing an Initialization Vector (IV)
 *   for the block algorithm initialization.
 * + Write and Read encrypted  or decrypted data  using CipherOutputStream and CipherInputStream
 * </pre>
 */
public class JavaDESEncryption
{
    private static final byte[] initialization_vector = { 22, 33, 11, 44, 55, 99, 66, 77 };

    private JavaDESEncryption()
    {
        // All static
    }

    public static void main(final String[] args) {

        final File folderFile    = FileHelper.getUserHomeDirFile();
        final File clearFile     = new File( folderFile, "input.txt" );
        final File encryptedFile = new File( folderFile, "encrypted.txt" );
        final File decryptedFile = new File( folderFile, "decrypted.txt" );

        try {
            final SecretKey              secret_key      = KeyGenerator.getInstance("DES").generateKey();
            final AlgorithmParameterSpec alogrithm_specs = new IvParameterSpec(initialization_vector);

            // set encryption mode ...
            final Encrypt encrypt = new Encrypt( "DES/CBC/PKCS5Padding", secret_key, alogrithm_specs );

            // encrypt file
            try( final InputStream is = new FileInputStream( clearFile ) ) {
                try( final OutputStream os = new FileOutputStream( encryptedFile ) ) {
                    encrypt.encrypt( is, os );
                    }
            }

            // set decryption mode
            final Decrypt decrypt = new Decrypt( "DES/CBC/PKCS5Padding", secret_key, alogrithm_specs );

            // decrypt file
            try( final InputStream is = new FileInputStream( encryptedFile ) ) {
                try( final OutputStream os = new FileOutputStream( decryptedFile ) ) {
                    decrypt.decrypt( is, os );
                    }
            }

            System.out.println("End of Encryption/Decryption procedure!");

        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | InvalidAlgorithmParameterException
                | IOException e) {
            e.printStackTrace();
        }

    }

    private static class Encrypt
    {
        private final Cipher encrypt;

        public Encrypt(
                final String                 transformation,
                final Key                    secret_key,
                final AlgorithmParameterSpec alogrithm_specs
                )
            throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException
        {
            this.encrypt = Cipher.getInstance( transformation /*"DES/CBC/PKCS5Padding"*/ );
            this.encrypt.init(Cipher.ENCRYPT_MODE, secret_key, alogrithm_specs);
        }

        void encrypt(final InputStream input, OutputStream output) throws IOException
        {
            output = new CipherOutputStream(output, this.encrypt);
            writeBytes(input, output);
        }
    }

    private static class Decrypt
    {
        private final Cipher decrypt;

        public Decrypt(
            final String                 transformation,
            final SecretKey              secret_key,
            final AlgorithmParameterSpec alogrithm_specs
            )
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException
        {
            this.decrypt = Cipher.getInstance( transformation /*"DES/CBC/PKCS5Padding"*/ );
            this.decrypt.init(Cipher.DECRYPT_MODE, secret_key, alogrithm_specs);
        }

        void decrypt(InputStream input, final OutputStream output) throws IOException
        {
            input = new CipherInputStream(input, this.decrypt);
            writeBytes(input, output);
        }
    }

    private static void writeBytes(final InputStream input, final OutputStream output)
        throws IOException
    {
        final byte[] writeBuffer = new byte[512];
        int    readBytes   = 0;

        try {
            try {
                while( (readBytes = input.read(writeBuffer)) >= 0) {
                    output.write(writeBuffer, 0, readBytes);
                    }
                }
            finally {
                output.close();
                }
            }
        finally {
            input.close();
            }
    }
}