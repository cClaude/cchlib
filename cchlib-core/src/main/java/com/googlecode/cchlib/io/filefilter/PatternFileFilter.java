package com.googlecode.cchlib.io.filefilter;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * A {@link FileFilter} and/or {@link FilenameFilter}
 * able to filer files using a regular expression.
 */
public class PatternFileFilter
    implements FileFilter, FilenameFilter, Serializable
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private Pattern pattern;

    /**
     * Create a PatternFileFilter using pattern
     * @param pattern {@link Pattern} to use 
     */
    public PatternFileFilter(Pattern pattern)
    {
        this.pattern = pattern;
    }

    /**
     * Create a PatternFileFilter using regexp
     * @param regex Regular expression to use 
     */
    public PatternFileFilter(String regex)
    {
        this( Pattern.compile(regex) );
    }

    @Override
    public boolean accept(File file)
    {
        return pattern.matcher(file.getPath()).matches();
    }

    @Override
    public boolean accept(File dir, String name)
    {
        return pattern.matcher(name).matches();
    }
}
