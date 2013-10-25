package com.googlecode.cchlib.sandbox.pushingpixels.windows.reflection;
/*
import java.awt.BorderLayout;
import java.awt.Window;

import javax.swing.*;

import org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel;

import quicktime.QTSession;
import quicktime.app.view.MoviePlayer;
import quicktime.app.view.QTFactory;
import quicktime.std.StdQTConstants;
import quicktime.std.movies.Movie;
import quicktime.std.movies.media.DataRef;

public class QTFrame extends JReflectionFrame {
	public QTFrame() {
		super("Reflection frame");
		try {
			QTSession.open();
			String url = "http://movies.apple.com/movies/us/apple/getamac/apple_getamac_holiday_480x272.mov";
			DataRef dRef = new DataRef(url);
			Movie mov = Movie.fromDataRef(dRef, StdQTConstants.newMovieActive);
			MoviePlayer player = new MoviePlayer(mov);
			mov.start();
			JComponent qtPlayer = QTFactory.makeQTJComponent(player)
					.asJComponent();
			this.add(qtPlayer, BorderLayout.CENTER);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(480, 272);
		this.setLocationRelativeTo(null);
	}

	public static void main(String[] args) throws Exception {
		JFrame.setDefaultLookAndFeelDecorated(true);
		UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Window w = new QTFrame();
				w.setVisible(true);
			}
		});
	}

}
*/