package oldies.tools.phone.contacts.myphoneexplorer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import oldies.tools.phone.BadFileFormatException;
import oldies.tools.phone.contacts.ContactProperties;
import oldies.tools.phone.contacts.DefaultContactProperties;

import au.com.bytecode.opencsv.CSVReader;


/**
 * Create a {@link ContactProperties} for a MyPhoneExplorer CVS file
 */
public class MPECVSContactProperties
    extends DefaultContactProperties
        implements ContactProperties
{
    private static final long serialVersionUID = 1L;

    /* *
     *
     * @param myPhoneExplorerCVSFile
     * @param charset
     * @param cvsSeparator
     * @throws BadFileFormatException
     * @throws IOException
     * /
    public MPECVSContactProperties(
        final File 		myPhoneExplorerCVSFile,
        final Charset 	charset,
        final char 		cvsSeparator
        )
        throws IOException, BadFileFormatException
    {
        super( new MPECVSContactPropertiesBuilder(
                myPhoneExplorerCVSFile,
                charset,
                cvsSeparator
                ) );
    }*/

    /**
     *
     * @param csvReader
     * @param cvsFile
     * @param charset
     * @param cvsSeparator
     * @throws IOException
     * @throws BadFileFormatException
     */
    public MPECVSContactProperties(
        final CSVReader	csvReader,
        final File 		cvsFile,
        final Charset 	charset,
        final char 		cvsSeparator
        )
        throws 	BadFileFormatException,
                IOException
    {
        super( new MPECVSContactPropertiesBuilder(
                csvReader,
                cvsFile,
                charset,
                cvsSeparator
                ) );
    }
}
