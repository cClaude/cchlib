package com.googlecode.cchlib.i18n;

import java.io.Serializable;
import java.util.MissingResourceException;

public interface LateKey extends Serializable
{
    public String getKey();
    public String getKey( int index );
    public String getValue() throws MissingResourceException;
    public String getValue( int index ) throws MissingResourceException;

}