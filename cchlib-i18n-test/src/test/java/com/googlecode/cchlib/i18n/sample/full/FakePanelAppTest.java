package com.googlecode.cchlib.i18n.sample.full;

import static org.fest.assertions.api.Assertions.assertThat;
import java.util.concurrent.ExecutionException;
import org.junit.Test;
import com.googlecode.cchlib.swing.RunnableSupplierHelper;

public class FakePanelAppTest
{
    @Test
    public void doTest_DO_I18N() throws ExecutionException
    {
        final FakePanel fakePanel = FakePanelApp.start(
            RunnableSupplierHelper.newRunnableSupplier(
                () -> FakePanelApp.newFakePanelApp_DO_I18N()
                )
            );

        assertThat( fakePanel.getPanelBorderTitle() ).isEqualTo( "OK: panelTitleBorder" );

        assertThat( fakePanel.getJLabelToI18nText() ).isEqualTo( "OK: jLabelToI18n" );
        assertThat( fakePanel.getJLabelToI18nToolTipText() )
            .isEqualTo( "OK: jLabelToI18n.ToolTipText" );

        assertThat( fakePanel.getProgressBarNoI18nText() ).isEqualTo( "JProgressBar (No_I18n)" );
        assertThat( fakePanel.getProgressBarNoI18nToolTipText() )
            .isEqualTo( "OK: jProgressBarNoI18n.ToolTipText" );

        assertThat( fakePanel.getProgressBarToI18nText() ) //FIXME I18n is missing here
            .isEqualTo( "JProgressBarNoI18nYet" );
        assertThat( fakePanel.getProgressBarToI18nToolTipText() )
            .isEqualTo( "OK: jProgressBarToI18n.ToolTipText" );

        assertThat( fakePanel.getJTextAreaNoI18nText() )
            .isEqualTo( FakePanel.JTEXT_AREA_NO_I18N_INIT_TEXT );
        assertThat( fakePanel.getJTextAreaNoI18nToolTipText() )
            .isEqualTo( "OK: jTextAreaNoI18n.ToolTipText" );

        assertThat( fakePanel.getJTextAreaToI18nText() )
            .isEqualTo( "OK: jTextAreaToI18n" );
        assertThat( fakePanel.getJTextAreaToI18nToolTipText() )
            .isEqualTo( "<html>jTextAreaToI18n to I18n<br>but not ToolTipText</html>" );

        // TODO need to add all others values

        assertThat( fakePanel.getJButtonText() ).isEqualTo( "OK: JButtonID" );
    }

    @Test
    public void doTest_NO_I18N() throws ExecutionException
    {
        final FakePanel fakePanel = FakePanelApp.start(
                RunnableSupplierHelper.newRunnableSupplier(
                    () -> FakePanelApp.newFakePanelApp_NO_I18N()
                    )
                );

        assertThat( fakePanel.getPanelBorderTitle() ).isEqualTo( "TitleBorderNoI18nYet" );

        assertThat( fakePanel.getJLabelToI18nText() ).isEqualTo( "JLabelNoI18nYet" );
        assertThat( fakePanel.getJLabelToI18nToolTipText() )
            .isEqualTo( "<html>jLabel<br/>toolTipText to I18n</html>" );

        assertThat( fakePanel.getProgressBarNoI18nText() ).isEqualTo( "JProgressBar (No_I18n)" );
        assertThat( fakePanel.getProgressBarNoI18nToolTipText() )
            .isEqualTo( "<html>JProgressBar<br/>ToolTipText to i18n</html>" );

        assertThat( fakePanel.getProgressBarToI18nText() )
            .isEqualTo( "JProgressBarNoI18nYet" );
        assertThat( fakePanel.getProgressBarToI18nToolTipText() )
            .isEqualTo( "<html>JProgressBar to I18n<br/>ToolTipText to i18n</html>" );

        assertThat( fakePanel.getJTextAreaNoI18nText() )
            .isEqualTo( FakePanel.JTEXT_AREA_NO_I18N_INIT_TEXT );
        assertThat( fakePanel.getJTextAreaNoI18nToolTipText() )
            .isEqualTo( "<html>jTextAreaNoI18n<br/>ToolTipText to i18n</html>" );

        assertThat( fakePanel.getJTextAreaToI18nText() ).isEqualTo( "jTextAreaToI18n" );
        assertThat( fakePanel.getJTextAreaToI18nToolTipText() )
            .isEqualTo( "<html>jTextAreaToI18n to I18n<br>but not ToolTipText</html>" );

        // TODO need to add all others values

        assertThat( fakePanel.getJButtonText() ).isEqualTo( "JButtonNoI18nYet" );
   }
}
