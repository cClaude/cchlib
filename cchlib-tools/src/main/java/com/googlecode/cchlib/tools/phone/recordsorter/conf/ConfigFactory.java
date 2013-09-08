package com.googlecode.cchlib.tools.phone.recordsorter.conf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface ConfigFactory 
{
    void encodeToFile( Config config, File file ) throws FileNotFoundException, IOException;
    Config decodeFromFile( File file ) throws FileNotFoundException, IOException;
    Config newConfig();
}
