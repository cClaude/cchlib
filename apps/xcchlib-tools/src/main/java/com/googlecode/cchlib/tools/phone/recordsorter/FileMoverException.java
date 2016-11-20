package com.googlecode.cchlib.tools.phone.recordsorter;

import java.io.IOException;
import java.nio.file.Path;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Contact;

/**
 *
 */
public class FileMoverException extends Exception {

    private static final long serialVersionUID = 1L;

    private final Path file;
    private final Contact contact;
    private final Path target;

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

    public Path getFile()
    {
        return this.file;
    }

    public Contact getContact()
    {
        return this.contact;
    }

    public Path getTarget()
    {
        return this.target;
    }
}
