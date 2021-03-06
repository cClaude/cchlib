package com.googlecode.cchlib.sandbox.pushingpixels.windows.popup;

import java.awt.Component;

import javax.swing.Popup;
import javax.swing.PopupFactory;

public class FadingPopupFactory extends PopupFactory {
	@Override
	public Popup getPopup(Component owner, Component contents, int x, int y)
			throws IllegalArgumentException {
		// A more complete implementation would cache and reuse
		// popups
		return new FadingPopup(owner, contents, x, y);
	}
}
