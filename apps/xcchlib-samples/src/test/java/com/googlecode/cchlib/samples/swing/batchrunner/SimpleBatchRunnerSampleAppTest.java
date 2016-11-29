package com.googlecode.cchlib.samples.swing.batchrunner;

import static org.fest.assertions.Assertions.assertThat;
import java.util.Locale;
import org.junit.Test;
import com.googlecode.cchlib.lang.Threads;
import com.googlecode.cchlib.swing.batchrunner.ihm.BRFrameBuilder;
import com.googlecode.cchlib.swing.batchrunner.ihm.BRPanelLocaleResources;
import com.googlecode.cchlib.swing.batchrunner.misc.BRXLocaleResources;
import com.googlecode.cchlib.swing.batchrunner.misc.MissingLocaleStringException;

public class SimpleBatchRunnerSampleAppTest
{
    @Test
    public void runSimpleBatchRunnerSampleApp() throws MissingLocaleStringException
    {
        final String[] args = new String[ 0 ];

        SimpleBatchRunnerSampleApp.main( args );
        Threads.sleep( 3_000 );
    }

    @Test
    public void test_newMyBRFrameBuilder() throws MissingLocaleStringException
    {
        final BRFrameBuilder actual = SimpleBatchRunnerSampleApp.newBRFrameBuilder();

        assertThat( actual ).isNotNull();
        assertThat( actual.getBRPanelLocaleResources() ).isNotNull();
        assertThat( actual.getBRXLocaleResources() ).isNotNull();

        final BRXLocaleResources xLocalResources = actual.getBRXLocaleResources();

        assertThat( xLocalResources.getFrameIconImage() ).isNotNull();
        assertThat( xLocalResources.getFrameTitle() )
            .isEqualTo( "Sample ! Just copie selected files into a other folder" );
        assertThat( xLocalResources.getProgressMonitorMessage() )
            .isEqualTo( "Copy in progress..." );

        final BRPanelLocaleResources panelLocal = actual.getBRPanelLocaleResources();

        assertThat( panelLocal.getTextAddSourceFile() ).isNotNull();
    }

    @Test
    public void test_newMyBRFrameBuilder_ENGLISH() throws MissingLocaleStringException
    {
        Locale.setDefault( Locale.ENGLISH );

        final BRFrameBuilder actual = SimpleBatchRunnerSampleApp.newBRFrameBuilder();

        assertThat( actual ).isNotNull();
        assertThat( actual.getBRPanelLocaleResources() ).isNotNull();
        assertThat( actual.getBRXLocaleResources() ).isNotNull();

        final BRPanelLocaleResources panelLocal = actual.getBRPanelLocaleResources();

        assertThat( panelLocal.getTextAddSourceFile() )
            .isEqualTo( "Select source files to add" );
    }

    @Test
    public void test_newMyBRFrameBuilder_FRENCH() throws MissingLocaleStringException
    {
        Locale.setDefault( Locale.FRENCH );

        final BRFrameBuilder actual = SimpleBatchRunnerSampleApp.newBRFrameBuilder();

        assertThat( actual ).isNotNull();
        assertThat( actual.getBRPanelLocaleResources() ).isNotNull();
        assertThat( actual.getBRXLocaleResources() ).isNotNull();

        final BRPanelLocaleResources panelLocal = actual.getBRPanelLocaleResources();

        assertThat( panelLocal.getTextAddSourceFile() )
            .isEqualTo( "Ajouter des fichiers source" );
    }
}
