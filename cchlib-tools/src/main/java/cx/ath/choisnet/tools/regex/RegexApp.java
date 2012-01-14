package cx.ath.choisnet.tools.regex;

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

public class RegexApp
{
    boolean packFrame = false;

    // Construct the application
    public RegexApp()
    {
        RegexFrame frame = new RegexFrame();
        // Validate frames that have preset sizes
        // Pack frames that have useful preferred size info, e.g. from their
        // layout
        if( packFrame ) {
            frame.pack();
        } else {
            frame.validate();
        }
        
        // Center the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        
        if( frameSize.height > screenSize.height ) {
            frameSize.height = screenSize.height;
        }
        if( frameSize.width > screenSize.width ) {
            frameSize.width = screenSize.width;
        }
        frame.setLocation( (screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2 );
        frame.setVisible( true );
    }

    // Main method
    public static void main( String[] args )
    {
        try {
            UIManager.setLookAndFeel( 
                    UIManager.getSystemLookAndFeelClassName() 
                    );
            }
        catch( Exception e ) {
            e.printStackTrace();
            }
        new RegexApp();
    }
}