package com.googlecode.cchlib.i18n.sample;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.config.DefaultI18nBundleFactory;

public class FakePanelApp extends JFrame
{
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

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
                    FakePanelApp frame = new FakePanelApp();
                    frame.setVisible( true );
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
    public FakePanelApp()
    {
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setBounds( 100, 100, 831, 300 );
        contentPane = new JPanel();
        contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        contentPane.setLayout( new BorderLayout( 0, 0 ) );
        setContentPane( contentPane );

        FakePanel panel = new FakePanel();
        
        Locale locale = super.getLocale();
        
        AutoI18n autoI18n = DefaultI18nBundleFactory.createDefaultI18nBundle( locale, FakePanelApp.class ).getAutoI18n();
        panel.performeI18n( autoI18n );
        
        contentPane.add(panel, BorderLayout.CENTER);
    }

}
