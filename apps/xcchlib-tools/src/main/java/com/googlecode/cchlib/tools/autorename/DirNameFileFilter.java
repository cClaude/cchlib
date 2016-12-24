package com.googlecode.cchlib.tools.autorename;

import java.io.File;
import java.util.EnumSet;
import java.util.Set;

public class DirNameFileFilter extends DirFileFilter
{
    public enum Attributes
    {
        IGNORE_CASE
    }

    private final String        dirname;
    private EnumSet<Attributes> attributes;

    public DirNameFileFilter( final String dirname, final Set<Attributes> attributes )
    {
        this.dirname = dirname;

        if( attributes == null ) {
            this.attributes = EnumSet.noneOf( Attributes.class );
        }
        else {
            this.attributes = EnumSet.copyOf( attributes );
            }
    }

    @Override
    public boolean accept( final File file )
    {
        if( ! file.isDirectory() ) {
            return false;
        }

        if( this.attributes.contains( Attributes.IGNORE_CASE ) ) {
            if( file.getName().equalsIgnoreCase( this.dirname ) ) {
                return true;
            }
        } else {
            if( file.getName().equals( this.dirname ) ) {
                return true;
            }
        }

        System.out.println( "L2_IGNORE_DIR: " + file );

        return false;
    }

}
