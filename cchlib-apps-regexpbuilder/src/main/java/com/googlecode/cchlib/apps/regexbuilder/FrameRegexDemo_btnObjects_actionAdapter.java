package com.googlecode.cchlib.apps.regexbuilder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class FrameRegexDemo_btnObjects_actionAdapter
    implements ActionListener
{
    RegExpFrame adaptee;

    FrameRegexDemo_btnObjects_actionAdapter( RegExpFrame adaptee )
    {
        this.adaptee = adaptee;
    }

    public FrameRegexDemo_btnObjects_actionAdapter(
			RegExpBuilderWB regExpBuilderWB) {
		// TODO Auto-generated constructor stub
	}

	public void actionPerformed( ActionEvent e )
    {
        adaptee.btnObjects_actionPerformed( e );
    }
}