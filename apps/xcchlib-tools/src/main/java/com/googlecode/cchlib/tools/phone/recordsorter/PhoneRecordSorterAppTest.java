package com.googlecode.cchlib.tools.phone.recordsorter;

import static org.fest.assertions.Assertions.assertThat;
import java.util.Locale;
import org.junit.Test;
import com.googlecode.cchlib.lang.Threads;
import com.googlecode.cchlib.swing.batchrunner.ihm.BRFrameBuilder;
import com.googlecode.cchlib.swing.batchrunner.ihm.BRPanelLocaleResources;
import com.googlecode.cchlib.swing.batchrunner.misc.BRXLocaleResources;
import com.googlecode.cchlib.swing.batchrunner.misc.MissingResourceValueException;

public class PhoneRecordSorterAppTest
{
    @Test
    public void runPhoneRecordSorterApp() throws MissingResourceValueException
    {
        final String[] args = new String[ 0 ];

        PhoneRecordSorterApp.main( args );
        Threads.sleep( 3_000 );
    }

    @Test
    public void test_newMyBRFrameBuilder() throws MissingResourceValueException
    {
        final BRFrameBuilder actual = PhoneRecordSorterApp.newBRFrameBuilder();

        assertThat( actual ).isNotNull();
        assertThat( actual.getBRPanelLocaleResources() ).isNotNull();
        assertThat( actual.getBRXLocaleResources() ).isNotNull();

        final BRXLocaleResources xLocalResources = actual.getBRXLocaleResources();

        assertThat( xLocalResources.getFrameIconImage() ).isNotNull();

        final BRPanelLocaleResources panelLocal = actual.getBRPanelLocaleResources();

        assertThat( panelLocal.getTextAddSourceFile() ).isNotNull();
    }

    @Test
    public void test_newMyBRFrameBuilder_ENGLISH() throws MissingResourceValueException
    {
        Locale.setDefault( Locale.ENGLISH );

        final BRFrameBuilder actual = PhoneRecordSorterApp.newBRFrameBuilder();

        assertThat( actual ).isNotNull();
        assertThat( actual.getBRPanelLocaleResources() ).isNotNull();
        assertThat( actual.getBRXLocaleResources() ).isNotNull();

        final BRXLocaleResources xLocalResources = actual.getBRXLocaleResources();

        assertThat( xLocalResources.getFrameIconImage() ).isNotNull();
        assertThat( xLocalResources.getFrameTitle() )
            .isEqualTo( "Main window" );
        assertThat( xLocalResources.getProgressMonitorMessage() )
            .isEqualTo( "Work in progress..." );

        final BRPanelLocaleResources panelLocal = actual.getBRPanelLocaleResources();

        assertThat( panelLocal.getTextAddSourceFile() )
            .isEqualTo( "Select source files to add" );
    }

    @Test
    public void test_newMyBRFrameBuilder_FRENCH() throws MissingResourceValueException
    {
        Locale.setDefault( Locale.FRENCH );

        final BRFrameBuilder actual = PhoneRecordSorterApp.newBRFrameBuilder();

        assertThat( actual ).isNotNull();
        assertThat( actual.getBRPanelLocaleResources() ).isNotNull();
        assertThat( actual.getBRXLocaleResources() ).isNotNull();

        final BRXLocaleResources xLocalResources = actual.getBRXLocaleResources();

        assertThat( xLocalResources.getFrameIconImage() ).isNotNull();
        assertThat( xLocalResources.getFrameTitle() )
            .isEqualTo( "Fenêtre principale" );
        assertThat( xLocalResources.getProgressMonitorMessage() )
            .isEqualTo( "Traitement en cours..." );

        final BRPanelLocaleResources panelLocal = actual.getBRPanelLocaleResources();

        assertThat( panelLocal.getTextAddSourceFile() )
            .isEqualTo( "Ajouter des fichiers source" );
    }
}
