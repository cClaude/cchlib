package com.googlecode.cchlib.apps.duplicatefiles.console;

import java.io.File;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.googlecode.cchlib.apps.duplicatefiles.console.model.HashFiles;
import com.googlecode.cchlib.json.JSONHelper;
import com.googlecode.cchlib.json.JSONHelperException;

/**
 *
 */
public class JSONLoaderHelper
{
    private JSONLoaderHelper()
    {
        // All static
    }

    /*
     *
     * @param hashJsonInputFile
     * @return
     * @throws CLIParametersException
     *
    public static List<HashFile> loadHash( final File hashJsonInputFile  )
        throws CLIParametersException
    {
        try {
            return JSONHelper.load(
                    hashJsonInputFile,
                    new TypeReference<List<HashFile>>() {}
                    );
        }
        catch( final JSONHelperException e ) {
            throw new CLIParametersException(
                    CLIParameters.JSON_IN, "Error while reading :" + hashJsonInputFile, e
                    );
        }
    }
*/
    /**
     *
     * @param duplicateInputFile
     * @return
     * @throws CLIParametersException
     */
    public static List<HashFiles> loadDuplicate( final File duplicateInputFile )
        throws CLIParametersException
    {
        try {
            return JSONHelper.load(
                    duplicateInputFile,
                    new TypeReference<List<HashFiles>>() { /* define ref only */ }
                    );
        }
        catch( final JSONHelperException e ) {
            throw new CLIParametersException(
                    CLIParameters.JSON_IN, "Error while reading :" + duplicateInputFile, e
                    );
        }
    }
}
