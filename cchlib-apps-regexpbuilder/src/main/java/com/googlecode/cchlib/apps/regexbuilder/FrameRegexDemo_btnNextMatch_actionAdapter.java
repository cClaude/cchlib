package com.googlecode.cchlib.apps.regexbuilder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class FrameRegexDemo_btnNextMatch_actionAdapter
    implements ActionListener
{
    RegExpFrame adaptee;

    FrameRegexDemo_btnNextMatch_actionAdapter( RegExpFrame adaptee )
    {
        this.adaptee = adaptee;
    }

    public FrameRegexDemo_btnNextMatch_actionAdapter(
			RegExpBuilderWB regExpBuilderWB) {
		// TODO Auto-generated constructor stub
	}

	public void actionPerformed( ActionEvent e )
    {
        adaptee.btnNextMatch_actionPerformed( e );
    }
}