package com.googlecode.cchlib.cli.it;

import org.apache.commons.cli.Options;
import com.googlecode.cchlib.cli.CommonConsoleParametersFactory;
import com.googlecode.cchlib.cli.apachecli.OptionBuilderInstance;
import com.googlecode.cchlib.cli.apachecli.OptionHelper;

public class CLIParametersFactory extends CommonConsoleParametersFactory<CLIParameters>
{
    public CLIParametersFactory()
    {
        // Empty
    }

    @Override
    protected void customizeCLIOptions()
    {
        final Options               options = getOptions();
        final OptionBuilderInstance builder = getOptionBuilderInstance();

        addOption(
                builder.withLongOpt( CLIParameters.CONFIG )
                    .withDescription( "MySQL/Samba JSON configuration file" )
                    .hasArgs( 1 )
                    .create( "m" )
                );
        addOption(
                builder.withLongOpt( CLIParameters.CUSTOM_CONFIG )
                    .withDescription( "Custom support configuration file" )
                    .hasArg( true )
                    .create( "c" )
                );

        OptionHelper.addOptionsFromOptionEnum( options, CLIParametersOption.class );
        OptionHelper.addOptionsFromOptionEnum( options, RepairMode.class );

        addOption(
                builder.withLongOpt( CLIParameters.EXPORT_CHARSET )
                    .withDescription(
                        "Specify charset for export (default: "
                            + CLIParameters.DEFAULT_CHARSET
                            )
                    .hasArg( true )
                    .create( "C" )
                );
    }
}
