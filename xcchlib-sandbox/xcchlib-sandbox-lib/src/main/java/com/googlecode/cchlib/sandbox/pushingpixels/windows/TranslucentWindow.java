package com.googlecode.cchlib.sandbox.pushingpixels.windows;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
//import com.googlecode.cchlib.sandbox.pushingpixels.AWTUtilitiesFrontEnd;

public class TranslucentWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    public TranslucentWindow() {
		super("Test translucent window");
		this.setLayout(new FlowLayout());
		this.add(new JButton("test"));
		this.add(new JCheckBox("test"));
		this.add(new JRadioButton("test"));
		this.add(new JProgressBar(0, 100));

		this.setSize(new Dimension(400, 300));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
            public void run() {
				Window w = new TranslucentWindow();
				w.setVisible(true);
				//AWTUtilitiesFrontEnd.setWindowOpacity(w, 0.5f);
				w.setOpacity( 0.5f );
			}
		});
	}

}
