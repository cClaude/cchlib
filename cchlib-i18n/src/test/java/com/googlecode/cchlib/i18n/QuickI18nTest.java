package com.googlecode.cchlib.i18n;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.i18n.annotation.I18nToolTipText;
import com.googlecode.cchlib.i18n.config.AbstractI18nBundle;
import com.googlecode.cchlib.i18n.config.DefaultI18nBundleFactory;
import com.googlecode.cchlib.i18n.config.I18nAutoUpdatable;
import com.googlecode.cchlib.i18n.config.I18nPrepHelperAutoUpdatable;
import javax.swing.JButton;

public class QuickI18nTest extends JFrame implements I18nAutoUpdatable, I18nPrepHelperAutoUpdatable
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

                    AutoI18n autoI18n = getDefaultI18nBundle( frame ).getAutoI18n();

                    frame.performeI18n( autoI18n );
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

    private static AbstractI18nBundle getDefaultI18nBundle( QuickI18nTest frame )
    {
        return DefaultI18nBundleFactory.createDefaultI18nBundle(
                frame.getLocale(),
                frame.getClass().getPackage(),
                frame.getClass().getSimpleName()
                );
    }

    @Override // I18nAutoUpdatable
    public void performeI18n( AutoI18n autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }

    @Override // I18nPrepHelperAutoUpdatable
    public String getMessagesBundleForI18nPrepHelper()
    {
        return getDefaultI18nBundle( this ).getMessagesBundle();
    }
}
