package com.googlecode.cchlib.swing.batchrunner.ihm;

import java.lang.reflect.Method;
import java.util.Locale;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 * The class {@link DefaultSBRLocaleResourcesTest} contains tests for the
 * class  {@link DefaultBRLocaleResources}.
 *
 * @version $Revision: 1.0 $
 */
public class DefaultSBRLocaleResourcesTest
{
    private static final Logger LOGGER = Logger.getLogger( DefaultSBRLocaleResourcesTest.class );

    private static class SBRLocaleResourcesEN implements BRPanelLocaleResources
    {
        private static final long serialVersionUID = 1L;

        @Override public String getTextAddSourceFile() { return "Select source files to add";  }
        @Override public String getTextSetDestinationFolder() { return "Select destination folder";  }
        @Override public String getTextClearSourceFileList() { return "Clear source files list";  }
        @Override public String getTextDoAction() { return "Start";  }
        @Override public String getTextJFileChooserInitializerTitle() { return "Please wait...";  }
        @Override public String getTextJFileChooserInitializerMessage() { return "Check disk structure";  }
        @Override public String getTextNoSourceFile() { return "<<< No source file - Please add at least one file >>>";  }
        @Override public String getTextNoDestinationFolder() { return "<<< No destination folder - Please select one >>>";  }
        @Override public String getTextWorkingOn_FMT() { return "Working on : %s";  }
        @Override public String getTextUnexpectedExceptionTitle() { return "Unexpected Exception";  }
        @Override public String getTextExitRequestTitle() { return "Exit ?";  }
        @Override public String getTextExitRequestMessage() { return "A task is running, do you want to cancel and exit ?";  }
        @Override public String getTextExitRequestYes() { return "Yes. Cancel current task.";  }
        @Override public String getTextExitRequestNo() { return "No";  }
    }

    private static class SBRLocaleResourcesFR implements BRPanelLocaleResources
    {
        private static final long serialVersionUID = 1L;

        @Override public String getTextAddSourceFile() { return "Ajouter des fichiers source";  }
        @Override public String getTextSetDestinationFolder() { return "Dossier de destination";  }
        @Override public String getTextClearSourceFileList() { return "Effacer la liste";  }
        @Override public String getTextDoAction() { return "Démarrer";  }
        @Override public String getTextJFileChooserInitializerTitle() { return "Patientez...";  }
        @Override public String getTextJFileChooserInitializerMessage() { return "Analyze de vos disques";  }
        @Override public String getTextNoSourceFile() { return "<<< Pas de fichiers source >>>";  }
        @Override public String getTextNoDestinationFolder() { return "<<< Pas de dossier destination >>>";  }
        @Override public String getTextWorkingOn_FMT() { return "Traitement de : %s";  }
        @Override public String getTextUnexpectedExceptionTitle() { return "Erreur inatendue";  }
        @Override public String getTextExitRequestTitle() { return "Quitter ?";  }
        @Override public String getTextExitRequestMessage() { return "Un traitement est en cours. Souhaitez-vous l'annuler et quitter ?";  }
        @Override public String getTextExitRequestYes() { return "Oui. Je veux quitter maintenant.";  }
        @Override public String getTextExitRequestNo() { return "Non";  }
    }

    /**
     * Run the DefaultSBRLocaleResources() constructor test for {@link Locale#ENGLISH}
     */
    @Test
    public void testDefaultSBRLocaleResources_En() throws Exception
    {
        Locale.setDefault( Locale.ENGLISH );

        final DefaultBRLocaleResources result = new DefaultBRLocaleResources();

        doAssert( new SBRLocaleResourcesEN(), result );
   }

    /**
     * Run the DefaultSBRLocaleResources() constructor test for {@link Locale#FRENCH}
     */
    @Test
    public void testDefaultSBRLocaleResources_Fr() throws Exception
    {
        Locale.setDefault( Locale.FRENCH );

        final DefaultBRLocaleResources result = new DefaultBRLocaleResources();

        doAssert( new SBRLocaleResourcesFR(), result );
    }

    private void doAssert( final BRPanelLocaleResources ref, final BRPanelLocaleResources result ) throws Exception
    {
        final Method[] methods = BRPanelLocaleResources.class.getDeclaredMethods();

        for( final Method m : methods ) {
            final Object valueRef = m.invoke( ref, (Object[])null );
            final Object valueRes = m.invoke( result, (Object[])null );

            LOGGER.info( m + " => " + valueRes );

            Assert.assertEquals( m.getName(), valueRef, valueRes );
            }
    }
}
