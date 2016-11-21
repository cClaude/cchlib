package com.googlecode.cchlib.sandbox.pushingpixels.windows.reflection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.*;

public class ColoredPanel extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;

    private float currHue = 0.0f;
	private Color currColor;

	@Override
    public synchronized void actionPerformed(ActionEvent e) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
            public void run() {
				currHue += 0.01f;
				if (currHue > 1.0f)
					currHue = 0.0f;
				updateBackgrColor();
				repaint();
			}
		});
	}

	public ColoredPanel() {
		Timer timer = new Timer(50, this);
		timer.setRepeats(true);
		timer.setCoalesce(true);
		timer.start();
		setLayout(new BorderLayout(0, 0));
		
		JPanel controls = new JPanel();
		controls.setOpaque(false);
		controls.setLayout(new FlowLayout());
		controls.add(new JButton("sample"));
		this.add(controls);
	}

	private synchronized void updateBackgrColor() {
		int c = Color.HSBtoRGB(this.currHue, 0.8f, 0.8f);
		this.currColor = new Color(c);
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (this.currColor == null)
			return;
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setPaint(new LinearGradientPaint(0.0f, 0.0f, getWidth(), 0.0f,
				new float[] { 0.0f, 0.5f, 1.0f }, new Color[] { this.currColor,
						this.currColor.darker(), this.currColor }));
		g2d.fillRect(0, 0, getWidth(), getHeight());

		// g2d.setColor(this.currColor);
		// g2d.fillRect(0, 0, getWidth(), getHeight());
		//
		g2d.setColor(Color.black);
		g2d.drawString(new Date().toString(), 50, 150);
		g2d.dispose();
	}

}
