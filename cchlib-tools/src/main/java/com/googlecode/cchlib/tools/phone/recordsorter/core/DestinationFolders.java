package com.googlecode.cchlib.tools.phone.recordsorter.core;

import java.io.File;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Config;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Contact;

public class DestinationFolders
{
    private Config config;
    private File destinationFolderFile;

    public DestinationFolders( Config config, File destinationFolderFile )
    {
        assert destinationFolderFile != null;

        this.config                = config;
        this.destinationFolderFile = destinationFolderFile;
    }

    public File getFolder( String number )
    {
        Contact person = this.config.findContactByNumber( number );

        if( person != null ) {
            return new File( destinationFolderFile, person.getName() );
            }

        return null;
    }

    public File createFolder( String folderName, String number )
    {
        Contact person = this.config.findContactByNumber( number );

        assert person == null;

        if( person == null ) {
            person = this.config.addContact( "!NEW!" + number, number );
        }

        assert person != null;

        return new File( destinationFolderFile, person.getName() );
    }

}
