package cx.ath.choisnet.swing.introspection;

import java.io.Serializable;
import java.util.Random;
import cx.ath.choisnet.lang.introspection.method.IVIgnore;
import cx.ath.choisnet.lang.introspection.method.IVInt;

@SuppressWarnings("boxing")
class TstObject implements Serializable
{
    private static final long serialVersionUID = 1L;
    private final Random rand = new Random(); // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.useOfRandom

    private boolean         testBoolean;
    private int             testIntegerJComboBox;
    public static final int testIntegerJComboBoxMin = 10;
    public static final int testIntegerJComboBoxDef = 15;
    public static final int testIntegerJComboBoxMax = 20;
    private String          testFMTString;
    private int             testIntegerJSlider;
    public static final int testIntegerJSliderMin = 20;
    public static final int testIntegerJSliderDef = 25;
    public static final int testIntegerJSliderMax = 30;
    private String          testString;
    private int             testIntegerLimitedIntegerJTextField;
    public static final int testIntegerLimitedIntegerJTextFieldMin = 30;
    public static final int testIntegerLimitedIntegerJTextFieldDef = 35;
    public static final int testIntegerLimitedIntegerJTextFieldMax = 40;
    private int             testIntegerJSpinner;
    public static final int testIntegerJSpinnerMin = 40;
    public static final int testIntegerJSpinnerDef = 45;
    public static final int testIntegerJSpinnerMax = 50;

    public TstObject()
    {
        this.testBoolean = true;
        this.testIntegerJComboBox = testIntegerJComboBoxDef;
        this.testFMTString = "FMT";
        this.testIntegerJSlider = testIntegerJSliderDef;
        this.testString = "A String";
        this.testIntegerLimitedIntegerJTextField = testIntegerLimitedIntegerJTextFieldDef;
    }

    public boolean isTestBoolean()
    {
        return testBoolean;
    }
    public void setTestBoolean( final boolean testBoolean )
    {
        this.testBoolean = testBoolean;
    }

    public int getTestIntegerJComboBox()
    {
        return testIntegerJComboBox;
    }
    @IVInt(minValue=testIntegerJComboBoxMin,maxValue=testIntegerJComboBoxMax,defaultValue=testIntegerJComboBoxDef)
    public void setTestIntegerJComboBox( final int testIntegerJComboBox )
    {
        this.testIntegerJComboBox = testIntegerJComboBox;
    }

    public String getTestFMTString()
    {
        return testFMTString;
    }
    public void setTestFMTString( final String testFMTString )
    {
        this.testFMTString = testFMTString;
    }

    public int getTestIntegerJSlider()
    {
        return testIntegerJSlider;
    }
    @IVInt(minValue=testIntegerJSliderMin,maxValue=testIntegerJSliderMax,defaultValue=testIntegerJSliderDef)
    public void setTestIntegerJSlider( final int testIntegerJSlider )
    {
        this.testIntegerJSlider = testIntegerJSlider;
    }

    public String getTestString()
    {
        return testString;
    }
    public void setTestString( final String testString )
    {
        this.testString = testString;
    }

    public int getTestIntegerLimitedIntegerJTextField()
    {
        return testIntegerLimitedIntegerJTextField;
    }
    @IVInt(minValue=testIntegerLimitedIntegerJTextFieldMin,maxValue=testIntegerLimitedIntegerJTextFieldMax,defaultValue=testIntegerLimitedIntegerJTextFieldDef)
    public void setTestIntegerLimitedIntegerJTextField(
            final int testIntegerLimitedIntegerJTextField )
    {
        this.testIntegerLimitedIntegerJTextField = testIntegerLimitedIntegerJTextField;
    }

    public int getTestIntegerJSpinner()
    {
        return testIntegerJSpinner;
    }
    @IVInt(minValue=testIntegerJSpinnerMin,maxValue=testIntegerJSpinnerMax,defaultValue=testIntegerJSpinnerDef)
    public void setTestIntegerJSpinner( final int testIntegerJSpinner )
    {
        this.testIntegerJSpinner = testIntegerJSpinner;
    }

    // Don't need @IVIgnore, does not start by get/set/is
    public void randomize()
    {//randomize
        this.testBoolean = rand.nextBoolean();
        this.testIntegerJComboBox = getRandom( testIntegerJComboBoxMin, testIntegerJComboBoxMax );
        this.testFMTString = getRandomFMT();
        this.testIntegerJSlider = getRandom( testIntegerJSliderMin, testIntegerJSliderMax );
        this.testString = String.format( "A String %08X", rand.nextInt() );
        this.testIntegerLimitedIntegerJTextField = getRandom( testIntegerLimitedIntegerJTextFieldMin, testIntegerLimitedIntegerJTextFieldMax );
        this.testIntegerJSpinner = getRandom( testIntegerJSpinnerMin, testIntegerJSpinnerMax );
    }

    @IVIgnore
    private int getRandom(final int min,final int max)
    {
       return rand.nextInt( (max - min) + 1 ) + min;
    }

    @IVIgnore
   private String getRandomFMT()
    {
        final byte[] bytes = new byte[4];

        rand.nextBytes( bytes );

        return  String.format( "%1$02X-%2$02X-%3$02X-%4$02X", bytes[0], bytes[1], bytes[2], bytes[3]);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "TstObject [testBoolean=" );
        builder.append( testBoolean );
        builder.append( ", testIntegerJComboBox=" );
        builder.append( testIntegerJComboBox );
        builder.append( ", testFMTString=" );
        builder.append( testFMTString );
        builder.append( ", testIntegerJSlider=" );
        builder.append( testIntegerJSlider );
        builder.append( ", testString=" );
        builder.append( testString );
        builder.append( ", testIntegerLimitedIntegerJTextField=" );
        builder.append( testIntegerLimitedIntegerJTextField );
        builder.append( ", testIntegerJSpinner=" );
        builder.append( testIntegerJSpinner );
        builder.append( ']' );
        return builder.toString();
    }
}
