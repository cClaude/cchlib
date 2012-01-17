package com.googlecode.cchlib.apps.regexbuilder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class FrameRegexDemo_btnObjSplit_actionAdapter
    implements ActionListener
{
    RegExpFrame adaptee;

    FrameRegexDemo_btnObjSplit_actionAdapter( RegExpFrame adaptee )
    {
        this.adaptee = adaptee;
    }

    public FrameRegexDemo_btnObjSplit_actionAdapter(
			RegExpBuilderWB regExpBuilderWB) {
		// TODO Auto-generated constructor stub
	}

	public void actionPerformed( ActionEvent e )
    {
        adaptee.btnObjSplit_actionPerformed( e );
    }
}