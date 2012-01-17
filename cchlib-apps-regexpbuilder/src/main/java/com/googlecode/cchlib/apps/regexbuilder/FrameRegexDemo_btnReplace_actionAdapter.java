package com.googlecode.cchlib.apps.regexbuilder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class FrameRegexDemo_btnReplace_actionAdapter
    implements ActionListener
{
    RegExpFrame adaptee;

    FrameRegexDemo_btnReplace_actionAdapter( RegExpFrame adaptee )
    {
        this.adaptee = adaptee;
    }

    public FrameRegexDemo_btnReplace_actionAdapter(
			RegExpBuilderWB regExpBuilderWB) {
		// TODO Auto-generated constructor stub
	}

	public void actionPerformed( ActionEvent e )
    {
        adaptee.btnReplace_actionPerformed( e );
    }
}