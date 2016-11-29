package com.googlecode.cchlib.cli;

import java.io.File;
import com.googlecode.cchlib.cli.apachecli.IsOption;
import com.googlecode.cchlib.cli.apachecli.OptionHelper;

public abstract class CommonConsoleParameters extends ConsoleParameters
{
    public static final String HELP = "help";

    public boolean isHelpRequired()
    {
        if( hasOption( HELP ) ) {
            ConsoleParametersFactory.printHelp( getOptions(), getCommandLineSyntax() );

            return true;
        }

        return false;
    }

    protected <E extends Enum<E> & IsOption> boolean hasOption( final E option )
    {
        return OptionHelper.hasOption( getCommandLine(), option );
    }

    protected <E extends Enum<E> & IsOption> boolean hasOption( final Class<E> enumType )
    {
        for( final E optionEnum : enumType.getEnumConstants() ) {
            if( hasOption( optionEnum ) ) {
                return true;
            }
        }

        return false;
    }

    protected <E extends Enum<E> & IsOption> String getOptionValue( final E option )
    {
        return OptionHelper.getOptionValue( getCommandLine(), option );
    }

    protected <E extends Enum<E> & IsOption> E getOption(
        final Class<E> enumType,
        final E        defaultValue
        )
    {
        for( final E optionEnum : enumType.getEnumConstants() ) {
            if( hasOption( optionEnum ) ) {
                return optionEnum;
            }
        }

        return defaultValue;
    }

    protected <E extends Enum<E> & IsOption> String[] getOptionValues( final E option )
    {
        return OptionHelper.getOptionValues( getCommandLine(), option );
    }

    protected File getExistingFile( final String param ) throws ConsoleParametersException
    {
        final File file = getFile( param );

        if( file.exists() ) {
            return file;
        }

        throw ConsoleParametersException.newFileNotFoundConsoleParametersException( param, file );
    }

    protected <E extends Enum<E> & IsOption> File getExistingFile( final E option )
        throws ConsoleParametersException
    {
        final File file = getFile( option );

        if( file.exists() ) {
            return file;
        }

        throw ConsoleParametersException.newFileNotFoundConsoleParametersException( option, file );
    }

    protected File getFile( final String param ) throws ConsoleParametersException
    {
        if( hasOption( param ) ) {
            return new File( getOptionValue( param ) );
        }
        throw new ConsoleParametersException( param, "Parameter not found" );
    }

    protected <E extends Enum<E> & IsOption> File getFile( final E option )
        throws ConsoleParametersException
    {
        if( OptionHelper.hasOption( getCommandLine(), option ) ) {
            return new File( OptionHelper.getOptionValue( getCommandLine(), option ) );
        }

        throw new ConsoleParametersException( option, "Parameter not found" );
    }
}
