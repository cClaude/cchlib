package com.googlecode.cchlib.apps.duplicatefiles.console;

import java.io.File;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.googlecode.cchlib.apps.duplicatefiles.console.model.HashFiles;
import com.googlecode.cchlib.json.JSONHelper;
import com.googlecode.cchlib.json.JSONHelperException;

public class JSONLoaderHelper
{
    private JSONLoaderHelper()
    {
        // All static
    }

    public static List<HashFiles> loadDuplicate( final CLIParameters cli )
        throws CLIParametersException
    {
        final File duplicateInputFile = cli.getJsonInputFile();

        try {
            return JSONHelper.load(
                    duplicateInputFile,
                    new TypeReference<List<HashFiles>>() { /* define ref only */ }
                    );
        }
        catch( final JSONHelperException cause ) {
            throw new CLIParametersException(
                    cli.getJsonInputFileParameter(),
                    "Error while reading :" + duplicateInputFile,
                    cause
                    );
        }
    }
}
