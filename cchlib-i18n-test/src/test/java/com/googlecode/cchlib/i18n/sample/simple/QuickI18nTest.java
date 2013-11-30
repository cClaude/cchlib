// $codepro.audit.disable numericLiterals
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
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;

public class QuickI18nTest extends JFrame implements I18nAutoCoreUpdatable
{
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    @I18nToolTipText private JButton myButtonWithToolTipText1;
    @I18nIgnore @I18nToolTipText private JButton myButtonWithToolTipText2;

    /**
     * Launch the application.
     */
    public static void main( String[] args )
    {
        EventQueue.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                try {
                    QuickI18nTest frame = new QuickI18nTest();
                    frame.setVisible( true );

                   // AutoI18n autoI18n = null;//getDefaultI18nBundle( frame ).getAutoI18n();

                   // frame.performeI18n( autoI18n );
                }
                catch( Exception e ) {
                    e.printStackTrace();
                }
            }
        } );
    }

    /**
     * Create the frame.
     */
    public QuickI18nTest()
    {
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setBounds( 100, 100, 450, 300 );
        this.contentPane = new JPanel();
        this.contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        this.contentPane.setLayout( new BorderLayout( 0, 0 ) );
        setContentPane( this.contentPane );

        this.myButtonWithToolTipText1 = new JButton("my button with tool tip text 1");
        this.myButtonWithToolTipText1.setToolTipText("my tool tip text 1");
        this.contentPane.add(this.myButtonWithToolTipText1, BorderLayout.CENTER);

        this.myButtonWithToolTipText2 = new JButton("my button with tool tip text 2");
        this.myButtonWithToolTipText2.setToolTipText("my tool tip text 2");
        this.contentPane.add(this.myButtonWithToolTipText2, BorderLayout.SOUTH);
    }

    @Override
    public void performeI18n( final AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }
}
