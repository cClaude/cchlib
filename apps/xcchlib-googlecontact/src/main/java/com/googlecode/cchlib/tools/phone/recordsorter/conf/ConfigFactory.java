package com.googlecode.cchlib.tools.phone.recordsorter.conf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@SuppressWarnings({
    "squid:S1160", // More than on exception
    "squid:RedundantThrowsDeclarationCheck",
    })
public interface ConfigFactory
{
    Config load( InputStream inStream ) throws IOException, UnsupportedOperationException;
    Config load( File file ) throws FileNotFoundException, IOException, UnsupportedOperationException;

    Config newConfig() throws UnsupportedOperationException;

    void encodeToFile( Config config, File file ) throws FileNotFoundException, IOException, UnsupportedOperationException;
}
