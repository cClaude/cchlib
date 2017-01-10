package com.googlecode.cchlib.i18n.sample.simple;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.EnumSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.i18n.annotation.I18nToolTipText;
import com.googlecode.cchlib.i18n.core.AutoI18n;
import com.googlecode.cchlib.i18n.core.AutoI18nFactory;
import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;

public class QuickI18nTestFrameApp extends JFrame implements I18nAutoUpdatable
{
    private static final long serialVersionUID = 1L;
    private final JPanel contentPane;

    @I18nToolTipText
    private final JButton myButtonWithToolTipText1;

    @I18nIgnore // Field is ignore for I18n
    @I18nToolTipText // but not tool tip text on the field.
    private final JButton myButtonWithToolTipText2;

    @I18nString
    private String missingI18nEntry;

    /**
     * Create the frame.
     */
    private QuickI18nTestFrameApp()
    {
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setBounds( 100, 100, 450, 300 );
        this.contentPane = new JPanel();
        this.contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        this.contentPane.setLayout( new BorderLayout( 0, 0 ) );
        setContentPane( this.contentPane );

        this.myButtonWithToolTipText1 = new JButton("my button with tool tip text 1 (no I18n)");
        this.myButtonWithToolTipText1.setToolTipText("my tool tip text 1  (no I18n)");
        this.contentPane.add(this.myButtonWithToolTipText1, BorderLayout.CENTER);

        this.myButtonWithToolTipText2 = new JButton("my button with tool tip text 2 (no I18n)");
        this.myButtonWithToolTipText2.setToolTipText("my tool tip text 2 (no I18n)");
        this.contentPane.add(this.myButtonWithToolTipText2, BorderLayout.SOUTH);

        noFinalStaticI18n();
    }

    private void noFinalStaticI18n()
    {
        this.missingI18nEntry = "This entry have no I18n";
    }

    @Override
    public void performeI18n( final AutoI18n autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }

    JButton getMyButtonWithToolTipText1()
    {
        return this.myButtonWithToolTipText1;
    }

    JButton getMyButtonWithToolTipText2()
    {
        return this.myButtonWithToolTipText2;
    }

    String getMissingI18nEntry()
    {
        return this.missingI18nEntry;
    }

    /**
     * Just to show the frame, see also {@link QuickI18nTestPrep}
     *
     * @param args Ignore
     */
    public static void main( final String[] args )
    {
        EventQueue.invokeLater( () -> {
            try {
                final QuickI18nTestFrameApp frame = newQuickI18nTestFrame();
                frame.setVisible( true );
            }
            catch( final Exception e ) {
                e.printStackTrace();
            }
        });
    }

    static QuickI18nTestFrameApp newQuickI18nTestFrame()
    {
        final QuickI18nTestFrameApp frame    = new QuickI18nTestFrameApp();
        final AutoI18n          autoI18n = newAutoI18n();

        frame.performeI18n( autoI18n );

        return frame;
    }

    static AutoI18n newAutoI18n()
    {
        return AutoI18nFactory.newAutoI18n(
                EnumSet.of( AutoI18nConfig.DO_DEEP_SCAN ),
                QuickI18nTestFrameApp.class
                );
    }
}
