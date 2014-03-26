package com.googlecode.cchlib.tools.phone.recordsorter;

import java.io.IOException;
import java.nio.file.Path;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Contact;

public class FileMoverException extends Exception {

    private static final long serialVersionUID = 1L;

    private Path file;
    private Contact contact;
    private Path target;

    public FileMoverException(
        final String      message,
        final Path        file,
        final Contact     contact,
        final Path        target,
        final IOException cause )
    {
        super( message, cause );

        this.file    = file;
        this.contact = contact;
        this.target  = target;
    }

}
