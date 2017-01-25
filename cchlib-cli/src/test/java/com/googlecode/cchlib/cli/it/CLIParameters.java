package com.googlecode.cchlib.cli.it;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Set;
import com.googlecode.cchlib.cli.CommonConsoleParameters;
import com.googlecode.cchlib.cli.ConsoleParametersException;
import com.googlecode.cchlib.cli.apachecli.OptionHelper;

public class CLIParameters extends CommonConsoleParameters
{
    public static final Charset DEFAULT_CHARSET = Charset.forName( "UTF-8" );

    static final String CONFIG         = "config";
    static final String CUSTOM_CONFIG  = "custom-config";
    static final String EXPORT_CHARSET = "export-charset";

    public CLIParameters()
    {
        // Empty
    }

    @Override
    protected String getCommandLineSyntax()
    {
        return "command.sh --config <jsonConfigFile> [ACTION ...]\n"
            + "command.sh --config <jsonConfigFile> --RemoveMissingFiles --CheckupRemoveExtraEntries";
    }

    public Set<CLIParametersOption> getSelectedOptions()
    {
        return OptionHelper.getSelectedOptions( getCommandLine(), CLIParametersOption.class );
    }

    public Iterable<String> getPeriodesToDelete()
    {
        throw new UnsupportedOperationException();
    }

    public Iterable<String> getCollectionsToDelete()
    {
        throw new UnsupportedOperationException();
    }

    public java.sql.Date getPeriodesFromYear()
    {
        throw new UnsupportedOperationException();
    }

    public java.sql.Date getPeriodesToYear()
    {
        throw new UnsupportedOperationException();
    }

    public Charset getExportCharset()
    {
        if( hasOption( EXPORT_CHARSET ) ) {
            final String charsetName = getOptionValue( EXPORT_CHARSET );

            return Charset.forName( charsetName );
        } else {
            return DEFAULT_CHARSET;
        }
    }

    public File getCollectionsConfigFile() throws ConsoleParametersException
    {
        return getFile( CLIParametersOption.AddCustomCollections );
    }

    public File getExportSQLFile() throws ConsoleParametersException
    {
        return getFile( CLIParametersOption.ExportDatabase );
    }

    public File getCheckupPatchSQLFile() throws ConsoleParametersException
    {
        return getFile( CLIParametersOption.CheckupCreatePatch );
    }

    public File getExportCollectionsJsonFile() throws ConsoleParametersException
    {
        return getFile( CLIParametersOption.ExportCollections );
    }

    public File getConfigFile() throws ConsoleParametersException
    {
        return getExistingFile( CONFIG );
    }

    public File getCustomsConfigFile() throws ConsoleParametersException
    {
        return getExistingFile( CUSTOM_CONFIG );
    }

    public RepairMode getRepairMode()
    {
        return getOption( RepairMode.class, RepairMode.CLEAN );
    }
}
