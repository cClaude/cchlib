package com.googlecode.cchlib.i18n.prep;

import java.io.File;
import java.io.IOException;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderFactory;

/**
 * Exception from {@link I18nPrepHelper}
 *
 * @see I18nPrepHelper#defaultPrep(com.googlecode.cchlib.i18n.core.I18nPrep,
 *      com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable...)
 * @deprecated use {@link I18nResourceBuilderFactory} instead
 */
@Deprecated
public class I18nPrepException extends Exception
{
    private static final long serialVersionUID = 1L;
    private final File        outputFile;

    I18nPrepException( final File outputFile, final IOException cause )
    {
        super( outputFile.toString(), cause );

        this.outputFile = outputFile;
    }

    public File getOutputFile()
    {
        return this.outputFile;
    }
}
