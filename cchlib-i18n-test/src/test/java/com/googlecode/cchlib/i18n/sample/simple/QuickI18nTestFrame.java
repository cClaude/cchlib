package com.googlecode.cchlib.i18n.sample.simple;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.i18n.annotation.I18nToolTipText;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.AutoI18nCoreFactory;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;

public class QuickI18nTestFrame extends JFrame implements I18nAutoCoreUpdatable
{
    private static final long serialVersionUID = 1L;
    private final JPanel contentPane;

    @I18nToolTipText private final JButton myButtonWithToolTipText1;
    @I18nIgnore @I18nToolTipText private final JButton myButtonWithToolTipText2;

    /**
     * Create the frame.
     */
    QuickI18nTestFrame()
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
    }

    @Override
    public void performeI18n( final AutoI18nCore autoI18n )
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

    /**
     * Just to show the frame, see also {@link QuickI18nTestPrep}
     *
     * @param args Ignore
     */
    public static void main( final String[] args )
    {
        EventQueue.invokeLater( () -> {
            try {
                final QuickI18nTestFrame frame = newQuickI18nTestFrame();
                frame.setVisible( true );
            }
            catch( final Exception e ) {
                e.printStackTrace();
            }
        });
    }

    static QuickI18nTestFrame newQuickI18nTestFrame()
    {
        final QuickI18nTestFrame frame = new QuickI18nTestFrame();

        final AutoI18nCore autoI18n = AutoI18nCoreFactory.newAutoI18nCore(
                QuickI18nTestFrame.class
                );

        frame.performeI18n( autoI18n );

        return frame;
    }
}
