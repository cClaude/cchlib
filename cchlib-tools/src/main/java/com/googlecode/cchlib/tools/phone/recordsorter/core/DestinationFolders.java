package com.googlecode.cchlib.tools.phone.recordsorter.core;

import java.io.File;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Config;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Contact;

public class DestinationFolders
{
    private Config config;
    private File destinationFolderFile;

    public DestinationFolders( final Config config, final File destinationFolderFile )
    {
        assert destinationFolderFile != null;

        this.config                = config;
        this.destinationFolderFile = destinationFolderFile;
    }

    public File getFolder( final String number )
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
            final Contact contact = this.config.newContact();

            contact.setName( "!NEW!" + number ).addNumber( number );

            person = this.config.addContact( contact );
        }

        assert person != null;

        return new File( destinationFolderFile, person.getName() );
    }

}
