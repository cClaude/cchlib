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
//import com.googlecode.cchlib.i18n.AutoI18nBasicInterface;
import com.googlecode.cchlib.swing.textfield.LimitedIntegerJTextField;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
//import org.apache.log4j.Logger;
//import org.junit.Assert;
//import org.junit.Test;

public class CustomJSliderTest extends JFrame
{
    private static final long serialVersionUID = 1L;
    //private static final Logger logger = Logger.getLogger( CustomJSliderTest.class );
    private JPanel contentPane;
    private LimitedIntegerJTextField textField;
    private CustomJSlider customJSlider;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
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

        {
            textField = new LimitedIntegerJTextField();
            textField.setMaxValue(100);
            GridBagConstraints gbc_textField = new GridBagConstraints();
            gbc_textField.insets = new Insets(0, 0, 0, 5);
            gbc_textField.gridx = 1;
            gbc_textField.gridy = 2;
            contentPane.add(textField, gbc_textField);
            textField.setColumns(10);
        }
        {
            final JLabel lblNewLabel = new JLabel();
            GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
            gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
            gbc_lblNewLabel.gridx = 1;
            gbc_lblNewLabel.gridy = 0;
            contentPane.add(lblNewLabel, gbc_lblNewLabel);
            
            {
                customJSlider = new CustomJSlider();
                customJSlider.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        int v = customJSlider.getValue();
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
                contentPane.add(customJSlider, gbc_slider);
            }
        }
    }

//    @Test
//    public void testAutoI18nBasicInterface()
//    {
//        AutoI18nBasicInterface autoI18nBasicInterface = customJSlider;
//        
//        final String customJSlider_DefaultString = "";
//        final String defaultString = autoI18nBasicInterface.getI18nString();
//        
//        logger.info( "defaultString = " + defaultString );
//        Assert.assertEquals( customJSlider_DefaultString , defaultString );
//        
//        final String localString = "My NEW String";
//        
//        autoI18nBasicInterface.setI18nString( localString  );
//        String newString = autoI18nBasicInterface.getI18nString();
//        Assert.assertEquals( localString , newString );
//    }
    
//    public void testI18n_process()
//    {
//        AutoI18nBasicInterface
//        customJSlider;
//    }
}
