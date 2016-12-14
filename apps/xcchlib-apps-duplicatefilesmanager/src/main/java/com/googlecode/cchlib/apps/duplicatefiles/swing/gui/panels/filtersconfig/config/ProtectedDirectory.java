package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.filtersconfig.config;

import com.googlecode.cchlib.apps.duplicatefiles.swing.ConfigMode;

// TODO not use yet
public class ProtectedDirectory
{
    private String     os;
    private ConfigMode level;
    private String     regex;
    private String     exceptionRegex;

    public String getOs()
    {
        return this.os;
    }

    public void setOs( final String os )
    {
        this.os = os;
    }

    public ConfigMode getLevel()
    {
        return this.level;
    }

    public void setLevel( final ConfigMode level )
    {
        this.level = level;
    }

    public String getRegex()
    {
        return this.regex;
    }

    public void setRegex( final String regex )
    {
        this.regex = regex;
    }

    public String getExceptionRegex()
    {
        return this.exceptionRegex;
    }

    public void setExceptionRegex( final String exceptionRegex )
    {
        this.exceptionRegex = exceptionRegex;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();

        builder.append( "ProtectedDirectory [os=" );
        builder.append( this.os );
        builder.append( ", level=" );
        builder.append( this.level );
        builder.append( ", regex=" );
        builder.append( this.regex );
        builder.append( ", exceptionRegex=" );
        builder.append( this.exceptionRegex );
        builder.append( ']' );

        return builder.toString();
    }
}
