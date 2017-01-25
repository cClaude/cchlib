package com.googlecode.cchlib.cli.it;

import java.util.Set;
import org.apache.commons.cli.Option;
import com.googlecode.cchlib.cli.apachecli.IsOption;

public enum CLIParametersOption implements IsOption
{
    ExportDatabase( "Export DB", 1 ),
    RemoveMissingFiles( "Delete missing files" ),

    /** Do not use if you use KODI only on Windows... */
    CheckupRemoveNonUnixPaths( "Fix paths in DB to use Unix notation" ),
    CheckupRemoveExtraEntries( "Clean up DB (remove unused values" ),
    CheckupFixPaths("Description CheckupFixPaths"),
    CheckupCreatePatch( "Description CheckupCreatePatch", 1 ),

    AddInformationsFromPicasa("Description AddInformationsFromPicasa"),
    AddCustomLabels("Description AddCustomLabels"),

    ResetPeriodes( "Delete all periodes" ),
    DeletePeriodes( "Delete a periode base on periode name", 1 ),
    AddCustomPeriodes("Description AddCustomPeriodes"),

    ExportCollections( "Export all collections configuration to a JSON file", 1 ),
    ResetCollections( "Delete all collections" ),
    DeleteCollections( "Delete a collections base on periode name", 1 ),
    AddCustomCollections( "Create collection based on JSON file (require JSON config file)", 1 ),
    ;

    private Option option;

    private CLIParametersOption( final String description, final int numberOfArgs )
    {
        this.option = new Option( name(), description );
        this.option.setArgs( numberOfArgs );
    }

    private CLIParametersOption( final String description )
    {
        this( description, 0 );
    }

    @Override
    public Option getOption()
    {
        return this.option;
    }

    public static boolean isPeriodes( final Set<CLIParametersOption> options )
    {
        return options.contains( CLIParametersOption.AddCustomPeriodes )
                || options.contains( CLIParametersOption.ResetPeriodes )
                || options.contains( CLIParametersOption.DeletePeriodes );
    }

    public static boolean isCollections( final Set<CLIParametersOption> options )
    {
        return options.contains( CLIParametersOption.AddCustomCollections )
                // options.contains( Option.ExportCollections ) Not in same class
                || options.contains( CLIParametersOption.ResetCollections )
                || options.contains( CLIParametersOption.DeleteCollections );
    }

    public static boolean isCheckup(final Set<CLIParametersOption> options)
    {
        return options.contains( CLIParametersOption.CheckupRemoveExtraEntries )
                || options.contains( CLIParametersOption.CheckupFixPaths )
                || options.contains( CLIParametersOption.CheckupCreatePatch );
    }
}
