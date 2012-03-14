package com.googlecode.cchlib.swing;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import cx.ath.choisnet.swing.CustomJSlider;
import com.googlecode.cchlib.swing.textfield.LimitedIntegerJTextField;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class CustomJSliderTest extends JFrame
{
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private LimitedIntegerJTextField textField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CustomJSliderTest frame = new CustomJSliderTest();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public CustomJSliderTest()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0};
        gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
        gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);

        final JLabel lblNewLabel = new JLabel("New label");
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.gridx = 1;
        gbc_lblNewLabel.gridy = 0;
        contentPane.add(lblNewLabel, gbc_lblNewLabel);

        textField = new LimitedIntegerJTextField();
        textField.setMaxValue(100);
        GridBagConstraints gbc_textField = new GridBagConstraints();
        gbc_textField.insets = new Insets(0, 0, 0, 5);
        gbc_textField.gridx = 1;
        gbc_textField.gridy = 2;
        contentPane.add(textField, gbc_textField);
        textField.setColumns(10);
        
        final CustomJSlider slider = new CustomJSlider();
        slider.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent e) {
        		int v = slider.getValue();
        		textField.setValue( v );
        		lblNewLabel.setText( Integer.toString( v ) );
        	}
        });
        GridBagConstraints gbc_slider = new GridBagConstraints();
        gbc_slider.fill = GridBagConstraints.HORIZONTAL;
        gbc_slider.gridwidth = 3;
        gbc_slider.insets = new Insets(0, 0, 5, 5);
        gbc_slider.gridx = 0;
        gbc_slider.gridy = 1;
        contentPane.add(slider, gbc_slider);
    }

}
