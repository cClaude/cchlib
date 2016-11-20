package com.googlecode.cchlib.sandbox.pushingpixels.windows.reflection;

import java.awt.*;

import javax.swing.*;

public class SimpleFrame extends JReflectionFrame {
    private static final long serialVersionUID = 1L;

    public SimpleFrame() {
		super("Reflection");
		this.setLayout(new BorderLayout());
		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bottom.add(new JButton("sample"));
		this.add(bottom, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 200);
		this.setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
            public void run() {
				Window w = new SimpleFrame();
				w.setVisible(true);
			}
		});
	}

}
