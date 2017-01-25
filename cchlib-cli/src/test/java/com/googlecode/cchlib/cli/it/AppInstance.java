package com.googlecode.cchlib.cli.it;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.File;
import java.nio.charset.Charset;
import com.googlecode.cchlib.cli.ConsoleParametersException;

public class AppInstance
{
    private final CLIParameters parameters;

    public AppInstance( final CLIParameters consoleParams )
    {
        this.parameters = consoleParams;
    }

    public void doExport() throws ConsoleParametersException
    {
        final File    exportFile    = this.parameters.getExportSQLFile();
        final Charset exportCharset = this.parameters.getExportCharset();

        assertThat( exportFile ).isNotNull();
        assertThat( exportCharset ).isNotNull();
    }

    public void doRemoveMissingFiles()
    {
        // Empty (no error)
    }
}
