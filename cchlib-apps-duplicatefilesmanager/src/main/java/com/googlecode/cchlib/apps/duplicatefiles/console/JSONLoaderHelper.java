package com.googlecode.cchlib.apps.duplicatefiles.console;

import java.io.File;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 *
 */
public class JSONLoaderHelper
{
    private JSONLoaderHelper()
    {
        // All static
    }

    /**
     *
     * @param hashJsonInputFile
     * @return
     * @throws CLIParametersException
     */
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

    /**
     *
     * @param duplicateInputFile
     * @return
     */
    public static List<HashFiles> loadDuplicate( final File duplicateInputFile )
    {
        // TODO Auto-generated method stub
        return null;
    }
}
