package com.googlecode.cchlib.apps.emptydirectories;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class DebugFrame extends JFrame
{
    private static final long serialVersionUID = 1L;

    private JPanel contentPane;

    /**
     * Create the frame.
     */
    public DebugFrame()
    {
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize( 600, 450 );

        this.contentPane = new JPanel();
        this.contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        this.contentPane.setLayout( new BorderLayout( 0, 0 ) );
        setContentPane( this.contentPane );
    }

    protected void addToContentPane( final Component comp )
    {
        contentPane.add( comp  );
    }
}
