package com.googlecode.cchlib.swing.textfield;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AutoCompleteTextFieldTestJFrame extends JFrame
{
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    AutoCompleteTextFieldTestJFrame frame = new AutoCompleteTextFieldTestJFrame();
                    frame.setVisible(true);
                    }
                catch (Exception e) {
                    e.printStackTrace();
                    }
                }
            });
    }

    /**
     * Create the frame.
     */
    public AutoCompleteTextFieldTestJFrame()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        //contentPane = new AutoCompleteTextFieldTestJPanel( valuesList );
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        AutoCompleteTextFieldTestJPanel testPane = new AutoCompleteTextFieldTestJPanel(
            new AutoCompleteTextFieldTestJPanel.Params() {
                @Override
                public List<String> getDataList()
                {
                    return getValuesList();
                }
            });

        contentPane.add( testPane );
    }

    private List<String> getValuesList()
    {
        List<String> valuesList = new ArrayList<String>();

        valuesList.add( "Aabc" );
        valuesList.add( "Un" );
        valuesList.add( "Deux" );
        valuesList.add( "Trois" );

        return valuesList;
    }

}
