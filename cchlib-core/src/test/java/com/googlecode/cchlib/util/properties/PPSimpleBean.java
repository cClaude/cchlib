package com.googlecode.cchlib.util.properties;

import java.util.Arrays;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

class PPSimpleBean implements Comparable<PPSimpleBean>
{
    @Populator protected String    aString;
    @Populator private   int       aInt;
    @Populator private   float     aFloat;
    @Populator public    boolean[] someBooleans;

    /** default text ERROR */
    @Persistent private JTextField aJTextField;
    /** default state unselected */
    @Persistent private JCheckBox  aJCheckBox;
    /** With a model, but non selected value */
    @Persistent private JComboBox  aJComboBox;

    public PPSimpleBean()
    {
        this.aJTextField = new JTextField();
        this.aJTextField.setText( "ERROR" );
        
        this.aJCheckBox  = new JCheckBox();
        this.aJCheckBox.setSelected(false);
        
        this.aJComboBox  = new JComboBox( getComboBoxModel() );
    }

    public PPSimpleBean(
        final String    aString,
        final int       aInt,
        final float     aFloat,
        final boolean[] booleans,
        final String    jTextFieldString,
        final boolean   jCheckBoxSelected,
        final int       jComboBoxSelectedIndex
        )
    {
    	this();
    	
        this.aString        = aString;
        this.aInt           = aInt;
        this.aFloat         = aFloat;
        this.someBooleans   = booleans;
        this.someBooleans   = booleans;
        
        this.aJTextField.setText( jTextFieldString );
        this.aJCheckBox.setSelected( jCheckBoxSelected );
        this.aJComboBox.setSelectedIndex( jComboBoxSelectedIndex );
    }
    
    public final String getaString()
    {
        return aString;
    }
    public final void setaString(String aString)
    {
        this.aString = aString;
    }
    public final int getaInt()
    {
        return aInt;
    }
    public final void setaInt(int aInt)
    {
        this.aInt = aInt;
    }
    public final float getaFloat()
    {
        return aFloat;
    }
    public final void setaFloat(float aFloat)
    {
        this.aFloat = aFloat;
    }
    
    private static ComboBoxModel getComboBoxModel() 
    {
        final Object[] objects = {
        	"Aa", "Bbb", "Ccc", "Dddd", "Eeeee", 
        	};

        return new DefaultComboBoxModel( objects );
    }
    

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PPSimpleBean [aString=");
        builder.append(aString);
        builder.append(", aInt=");
        builder.append(aInt);
        builder.append(", aFloat=");
        builder.append(aFloat);
        builder.append(", someBooleans=");
        builder.append(Arrays.toString(someBooleans));
        builder.append(", aJTextField(getText)=");
        builder.append( aJTextField.getText() );
        builder.append(", aJCheckBox(isSelected)=");
        builder.append(aJCheckBox.isSelected());
        builder.append(", aJComboBox(getSelectedIndex)=");
        builder.append(aJComboBox.getSelectedIndex());
        builder.append(']');
        return builder.toString();
    }

    private int myCompareTo( final PPSimpleBean o ) // USE THIS ONE 
    {
        int res = this.aString.compareTo( o.aString );
        if( res != 0 ) {
            return res;
            }

        res = this.aInt - o.aInt;
        if( res != 0 ) {
            return res;
            }

        res = Float.compare( this.aFloat, o.aFloat );
        if( res != 0 ) {
            return res;
            }

        res = this.someBooleans.length - o.someBooleans.length;
        if( res != 0 ) {
            return res;
            }

        for( int i = 0; i<this.someBooleans.length; i++ ) {
            res = Boolean.valueOf( this.someBooleans[ i ] ).compareTo( o.someBooleans[ i ] );
            if( res != 0 ) {
                return res;
                }
            }

        res = this.aJTextField.getText().compareTo( o.aJTextField.getText() );
        if( res != 0 ) {
            return res;
            }
        
        if( ! this.aJCheckBox.isSelected() == o.aJCheckBox.isSelected() ) {
        	return 1;
        	}

        res = this.aJComboBox.getSelectedIndex() - o.aJComboBox.getSelectedIndex();
        
        return res;
    }
    
    @Override
    public int compareTo( final PPSimpleBean o ) // USE THIS ONE 
    {
    	return this.myCompareTo( o );
    }
    
    @Override
	public boolean equals( Object o ) // $codepro.audit.disable overridingEqualsAndHashCode
    {
    	throw new UnsupportedOperationException( "equals() not supported" );
    }
}