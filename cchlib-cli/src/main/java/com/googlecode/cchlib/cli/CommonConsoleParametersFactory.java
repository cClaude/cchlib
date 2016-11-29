package com.googlecode.cchlib.cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.cli.apachecli.OptionBuilderInstance;
import com.googlecode.cchlib.cli.apachecli.OptionHelper;
import com.googlecode.cchlib.cli.apachecli.OptionHelperRuntimeException;

public abstract class CommonConsoleParametersFactory<T extends ConsoleParameters> extends ConsoleParametersFactory<T>
{
    private static final Logger   LOGGER = Logger.getLogger( CommonConsoleParametersFactory.class );

    private Options               options;
    private OptionBuilderInstance builder;

    @Override
    protected final Options createCLIOptions()
    {
        addOption(
            getOptionBuilderInstance().withLongOpt( CommonConsoleParameters.HELP )
                .withDescription( "Display this message" )
                .hasArg( false )
                .create( "h" )
            );

        customizeCLIOptions(); // customize

        return getOptions();
    }

    protected Options getOptions()
    {
        if( this.options == null ) {
            this.options = new Options();
        }

        return this.options;
    }

    protected OptionBuilderInstance getOptionBuilderInstance()
    {
        if( this.builder == null ) {
            this.builder = new OptionBuilderInstance();
        }

        return this.builder;
    }

    protected void addOption( final Option option )
    {
        try {
            OptionHelper.addOption( getOptions(), option );
        } catch( final OptionHelperRuntimeException cause ) {
            LOGGER.fatal( getOptions() );

            throw cause;
        }
    }

    protected abstract void customizeCLIOptions();
}
