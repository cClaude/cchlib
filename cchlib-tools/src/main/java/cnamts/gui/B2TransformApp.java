package cnamts.gui;

import java.io.File;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.swing.DialogHelper;

/**
 * @deprecated no replacement
 */
@Deprecated
public class B2TransformApp
    extends CNAMVeryLazyBatchRunnerApp<cnamts.DelNonAlphaChar>
{
    private static final Logger LOGGER = Logger.getLogger( B2TransformApp.class );

    private B2TransformApp() {}

    /**
     * Launch the application.
     */
    public static void main( final String[] args )
    {
        try {
            B2TransformApp instance = new B2TransformApp();

            instance.start();
            }
        catch( Exception e ) {
            LOGGER.fatal( "FATAL Error", e );
            DialogHelper.showMessageExceptionDialog( B2TransformApp.class.getName(), e );
            }
    }

    //
    //BEGIN: VeryLazyBatchRunnerApp
    //
    @Override//VeryLazyBatchRunnerApp
    public cnamts.DelNonAlphaChar buildTask()
    {
         return new cnamts.DelNonAlphaChar(
             new Character( cnamts.DelNonAlphaChar.REPLACEMENT_CHAR ),
             new Integer( 128 )
             );
    }

    @Override//VeryLazyBatchRunnerApp
    public File buildBasicOuputFile( File destinationFolderFile, File sourceFile )
    {
        return new File(
                destinationFolderFile,
                sourceFile.getName() + ".b2-128"
                );
    }
    //
    //END: VeryLazyBatchRunnerApp
    //

    //
    //BEGIN: LazyBatchRunnerLocaleResources
    //
    @Override//LazyBatchRunnerLocaleResources
    public String getTextFrameTitle()
    {
        return "Transformation B2 en fichier text 128 caractères par ligne";
    }

    @Override//LazyBatchRunnerLocaleResources
    public String getTextDoAction()
    {
        return "Convertir";
    }
    //
    //END: LazyBatchRunnerLocaleResources
    //
}
