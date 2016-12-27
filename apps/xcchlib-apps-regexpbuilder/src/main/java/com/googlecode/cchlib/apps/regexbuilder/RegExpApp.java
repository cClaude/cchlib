package com.googlecode.cchlib.apps.regexbuilder;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.UIManager;

/**
 * <p>
 * Regular Expressions Demo
 * </p>
 * <p>
 * Demonstration showing how to use the java.util.regex package that is part of
 * JDK 1.4 and later
 * </p>
 * <p>
 * Copyright (c) 2003 Jan Goyvaerts. All rights reserved.
 * </p>
 * <p>
 * Visit http://www.regular-expressions.info for a detailed tutorial to regular
 * expressions.
 * </p>
 *
 * @author Jan Goyvaerts
 * @author Claude CHOISNET
 * @version 1.1
 */

public class RegExpApp
{
    public static final boolean PACK_FRAME = false;
    private final RegExpBuilderFrame frame;

    // Construct the application
    public RegExpApp()
    {
    	this.frame = new RegExpBuilderFrame();
    }

    public void show( final boolean packFrame )
    {
        // Validate frames that have preset sizes
        // Pack frames that have useful preferred size info, e.g. from their
        // layout
        if( packFrame ) {
            this.frame.pack();
        } else {
            this.frame.validate();
        }

        // Center the window
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final Dimension frameSize = this.frame.getSize();

        if( frameSize.height > screenSize.height ) {
            frameSize.height = screenSize.height;
        }
        if( frameSize.width > screenSize.width ) {
            frameSize.width = screenSize.width;
        }
        this.frame.setLocation( (screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2 );
        this.frame.setVisible( true );
    }

    // Main method
    public static void main( final String[] args )
    {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
                    );
            }
        catch( final Exception e ) {
            e.printStackTrace();
            }

        final RegExpApp instance = new RegExpApp();

        instance.show( PACK_FRAME );
    }
}