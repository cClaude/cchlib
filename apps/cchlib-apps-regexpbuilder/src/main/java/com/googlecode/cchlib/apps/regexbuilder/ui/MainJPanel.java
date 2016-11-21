package com.googlecode.cchlib.apps.regexbuilder.ui;

import javax.swing.JPanel;
import java.awt.BorderLayout;

public class MainJPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private InputJPanel inputJPanel;
    private MatchJPanel matchJPanel;

    /**
     * Create the panel.
     */
    public MainJPanel()
    {
        setLayout(new BorderLayout(0,0));
        {
            this.inputJPanel = new InputJPanel();
            add(this.inputJPanel, BorderLayout.NORTH);
        }
        {
            this.matchJPanel = new MatchJPanel();
            add(this.matchJPanel, BorderLayout.CENTER);
        }
    }

}
