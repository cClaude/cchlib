package com.googlecode.cchlib.io.filefilter;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * A {@link FileFilter} and/or {@link FilenameFilter}
 * able to filer files using a regular expression.
 *
 * @since 3.01
 */
public class PatternFileFilter
    implements FileFilter, FilenameFilter, Serializable
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private final Pattern pattern;

    /**
     * Create a PatternFileFilter using pattern
     * @param pattern {@link Pattern} to use
     */
    public PatternFileFilter(final Pattern pattern)
    {
        this.pattern = pattern;
    }

    /**
     * Create a PatternFileFilter using regexp
     * @param regex Regular expression to use
     */
    public PatternFileFilter(final String regex)
    {
        this( Pattern.compile(regex) );
    }

    @Override
    public boolean accept(final File file)
    {
        return this.pattern.matcher(file.getPath()).matches();
    }

    @Override
    public boolean accept(final File dir, final String name)
    {
        return this.pattern.matcher(name).matches();
    }
}
