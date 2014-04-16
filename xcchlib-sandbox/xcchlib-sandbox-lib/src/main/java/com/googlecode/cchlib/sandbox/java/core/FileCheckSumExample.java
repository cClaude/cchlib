package com.googlecode.cchlib.sandbox.java.core;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileCheckSumExample
{
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException
    {
        InputStream is = FileCheckSumExample.class.getResourceAsStream( "output.txt" );
        //String filepath = "C:\\Users\\nikos7\\Desktop\\output.txt";

        MessageDigest messageDigest = MessageDigest.getInstance("SHA1");

        //FileInputStream fileInput = new FileInputStream(filepath);
        byte[] dataBytes = new byte[1024];
        int    bytesRead = 0;

        while ((bytesRead = is.read(dataBytes)) != -1) {
            messageDigest.update(dataBytes, 0, bytesRead);
            }

        byte[] digestBytes = messageDigest.digest();

        StringBuffer sb = new StringBuffer("");

        for (int i = 0; i < digestBytes.length; i++) {
            sb.append(Integer.toString((digestBytes[i] & 0xff) + 0x100, 16).substring(1));
            }

        System.out.println("Checksum for the File: " + sb.toString());

        is.close();
    }
}
