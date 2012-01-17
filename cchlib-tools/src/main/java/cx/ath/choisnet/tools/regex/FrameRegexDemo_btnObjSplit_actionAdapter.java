package cx.ath.choisnet.tools.regex;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class FrameRegexDemo_btnObjSplit_actionAdapter
    implements ActionListener
{
    RegexFrame adaptee;

    FrameRegexDemo_btnObjSplit_actionAdapter( RegexFrame adaptee )
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