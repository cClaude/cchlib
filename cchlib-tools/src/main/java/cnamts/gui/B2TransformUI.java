package cnamts.gui;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import org.apache.log4j.Logger;
import cnamts.DelNonAlphaChar;
import com.googlecode.cchlib.i18n.I18nString;
import com.googlecode.cchlib.swing.DialogHelper;

public class B2TransformUI extends B2TransformWB
{
    private static final long serialVersionUID = 1L;
    private static final transient Logger logger = Logger.getLogger( B2TransformUI.class );

//    private WaitingJFileChooserInitializer jFileChooserInitializer;
//    private ActionListener myActionListener;

//    @I18nString private String jFileChooserInitializerTitle     = "Waiting...";
//    @I18nString private String jFileChooserInitializerMessage   = "Analyze disk structure";
    @I18nString private static String txtAddSourceFile = "Ajouter des fichiers source";
    @I18nString private static String txtSetDestinationFolder = "Dossier de destination";
    @I18nString private static String txtClearSourceFileList = "Effacer la liste";
    @I18nString private static String txtDoActionButton = "Convert";

    public B2TransformUI()
    {
        super(  txtAddSourceFile,
                txtSetDestinationFolder,
                txtClearSourceFileList,
                txtDoActionButton
                );
    }

    protected void convert()
    {
        if( this.getSourceFilesCount() == 0 ) {
            // TODO: display alert
            setCurrentState( "<<< Pas de fichiers source >>>" );
            return;
            }
        final File destinationFolderFile = this.getDestinationFolderFile();
        if( destinationFolderFile == null ) {
            // TODO: display alert
            setCurrentState( "<<< Pas de dossier destination >>>" );
            return;
            }

        final DelNonAlphaChar instance = new DelNonAlphaChar( DelNonAlphaChar.REPLACEMENT_CHAR, 128 );
        final Enumeration<File> enumFile = getSourceFileElements();

        setCurrentState( "Conversion en cours" );
        new Thread( new Runnable()
        {
            @Override
            public void run()
            {
                while( enumFile.hasMoreElements() ) {
                    final File sourceFile = enumFile.nextElement();

                    logger.info( "Convert " + sourceFile );
                    setCurrentState( "Conversion de : " + sourceFile.getPath() );

                    File destinationFile = new File(
                            destinationFolderFile,
                            sourceFile.getName() + ".128"
                            );

                    try {
                        instance.delNonAlphaChar(sourceFile, destinationFile);
                        setCurrentState( "Conversion terminée" );
                        logger.info( "Convert done" );
                        }
                    catch( IOException e ) {
                        final String title = "Erreur de conversion";

                        logger.error( title, e );
                        setCurrentState( title );

                        DialogHelper.showMessageExceptionDialog( B2TransformUI.this, title, e );
                        }
                    }
            }
        }).start();
    }
}
