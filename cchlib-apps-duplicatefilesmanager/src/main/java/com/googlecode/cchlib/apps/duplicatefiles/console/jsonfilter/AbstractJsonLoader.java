package com.googlecode.cchlib.apps.duplicatefiles.console.jsonfilter;

import java.io.File;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParameters;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIParametersException;
import com.googlecode.cchlib.apps.duplicatefiles.console.HashFile;
import com.googlecode.cchlib.apps.duplicatefiles.console.JSONHelper;
import com.googlecode.cchlib.apps.duplicatefiles.console.JSONHelperException;

public abstract class AbstractJsonLoader
{
    protected abstract File getJsonInputFile();

    protected List<HashFile> loadJsonInputFile() throws CLIParametersException
    {
        try {
            return JSONHelper.load(
                    getJsonInputFile(),
                    new TypeReference<List<HashFile>>() {}
                    );
        }
        catch( final JSONHelperException e ) {
            throw new CLIParametersException( CLIParameters.JSON_IN, "Error while reading :" + getJsonInputFile(), e );
        }
    }
}
