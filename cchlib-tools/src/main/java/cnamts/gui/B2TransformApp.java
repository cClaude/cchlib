package cnamts.gui;

import cnamts.DelNonAlphaChar;
import com.googlecode.cchlib.swing.DialogHelper;

/**
 *
 * @author Claude
 */
public class B2TransformApp
    extends CNAMVeryLazyBatchRunnerApp<DelNonAlphaChar>
{
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
