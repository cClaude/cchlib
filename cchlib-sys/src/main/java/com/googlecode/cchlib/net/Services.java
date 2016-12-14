package com.googlecode.cchlib.net;

import java.io.File;

/**
 * GetServiceByName lookup port numbers in the /etc/services file
 *
 * @since 4.1.8
 */
@SuppressWarnings("squid:S1609") // Probably not a functional interfacce
public interface Services
{
    public File getFile();
}
