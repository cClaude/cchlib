package cnamts.gui;

import java.io.File;
import org.apache.log4j.Logger;
import cnamts.DelNonAlphaChar;
import com.googlecode.cchlib.swing.DialogHelper;

/**
 *
 * @author Claude
 */
public class B2TransformApp
    extends CNAMVeryLazyBatchRunnerApp<DelNonAlphaChar>
{
    private static final transient Logger logger = Logger.getLogger( B2TransformApp.class );
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
            logger.fatal( "FATAL Error", e );
            DialogHelper.showMessageExceptionDialog( B2TransformApp.class.getName(), e );
            }
    }

    //
    //BEGIN: VeryLazyBatchRunnerApp
    //
    @Override//VeryLazyBatchRunnerApp
    public DelNonAlphaChar buildTask()
    {
         return new DelNonAlphaChar( DelNonAlphaChar.REPLACEMENT_CHAR, 128 );
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
