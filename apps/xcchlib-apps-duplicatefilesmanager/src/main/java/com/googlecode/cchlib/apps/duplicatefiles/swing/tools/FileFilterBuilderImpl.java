package com.googlecode.cchlib.apps.duplicatefiles.swing.tools;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import com.googlecode.cchlib.apps.duplicatefiles.swing.FileFilterBuilder;
import cx.ath.choisnet.lang.ToStringBuilder;

/**
 * Default implementation of {@link FileFilterBuilder}
 */
public class FileFilterBuilderImpl implements FileFilterBuilder, Serializable {

    private static final long serialVersionUID = 1L;

    private final Collection<String> namesList;
    private final Pattern            pattern;

    public FileFilterBuilderImpl( //
            @Nonnull final Collection<String> namesList, //
            @Nonnull final Pattern            pattern //
            )
    {
        this.namesList = Collections.unmodifiableCollection( namesList );
        this.pattern   = pattern;
    }

    @Override
    public Collection<String> getNamePart()
    {
        return this.namesList;
    }

    @Override
    public Pattern getRegExp()
    {
        return this.pattern;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.toString( this, FileFilterBuilder.class );
    }
}
