package cnamts.gui;

import java.io.File;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
import cnamts.DelNonAlphaChar;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.swing.batchrunner.lazy.LazyBatchRunnerCustomJPanelFactory;

/**
 *
 * @author Claude
 */
public class B2Transform2App
    extends CNAMVeryLazyBatchRunnerApp<DelNonAlphaChar>
{
    private static final transient Logger logger = Logger.getLogger( B2Transform2App.class );

    private B2Transform2App(
        final LazyBatchRunnerCustomJPanelFactory customJPanelFactory
        )
    {
        super( customJPanelFactory , null );
    }

    /**
     * Launch the application.
     */
    public static void main( final String[] args )
    {
        try {
            B2Transform2App instance = new B2Transform2App( new CustomJPanelB2TransformFactory());

            instance.start();
            }
        catch( Exception e ) {
            logger.fatal( "FATAL Error", e );
            DialogHelper.showMessageExceptionDialog( B2Transform2App.class.getName(), e );
            }
    }

    /**
     * @return CustomJPanelB2Transform
     */
    public CustomJPanelB2Transform getCustomJPanelB2Transform()
    {
        JPanel panel = getCustomJPanel();

        return CustomJPanelB2Transform.class.cast( panel );
    }

    //
    //BEGIN: VeryLazyBatchRunnerApp
    //
    @Override//VeryLazyBatchRunnerApp
    public DelNonAlphaChar buildTask()
    {
        CustomJPanelB2Transform panel = getCustomJPanelB2Transform();

        Character rCharacter    = panel.getReplacementChar();
        Integer   lineLength    = panel.getLineLength();

        logger.info( "User getReplacementChar() = " + rCharacter );
        logger.info( "User getLineLength() = " + lineLength );

        return new DelNonAlphaChar( rCharacter, lineLength );
    }

    @Override//VeryLazyBatchRunnerApp
    public File buildBasicOuputFile( File destinationFolderFile, File sourceFile )
    {
        CustomJPanelB2Transform panel = getCustomJPanelB2Transform();

        String fileExtension  = panel.getFileExtension();
        logger.info( "User getFileExtension() = " + fileExtension );

        return new File(
                destinationFolderFile,
                sourceFile.getName() + fileExtension
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
