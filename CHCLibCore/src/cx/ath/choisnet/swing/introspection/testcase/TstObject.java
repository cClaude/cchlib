/**
 *
 */
package cx.ath.choisnet.swing.introspection.testcase;

import java.util.Random;
import cx.ath.choisnet.lang.introspection.method.IVIgnore;
import cx.ath.choisnet.lang.introspection.method.IVInt;

/**
 * @see IntrospectionTest
 * @author Claude CHOISNET
 */
class TstObject
{
    private Random rand = new Random();

    private boolean         testBoolean;
    private int             testIntegerJComboBox;
    public final static int testIntegerJComboBoxMin = 10;
    public final static int testIntegerJComboBoxDef = 15;
    public final static int testIntegerJComboBoxMax = 20;
    private String          testFMTString;
    private int             testIntegerJSlider;
    public final static int testIntegerJSliderMin = 20;
    public final static int testIntegerJSliderDef = 25;
    public final static int testIntegerJSliderMax = 30;
    private String          testString;
    private int             testIntegerLimitedIntegerJTextField;
    public final static int testIntegerLimitedIntegerJTextFieldMin = 30;
    public final static int testIntegerLimitedIntegerJTextFieldDef = 35;
    public final static int testIntegerLimitedIntegerJTextFieldMax = 40;
    private int             testIntegerJSpinner;
    public final static int testIntegerJSpinnerMin = 40;
    public final static int testIntegerJSpinnerDef = 45;
    public final static int testIntegerJSpinnerMax = 50;

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
    public void setTestBoolean( boolean testBoolean )
    {
        this.testBoolean = testBoolean;
    }
    
    public int getTestIntegerJComboBox()
    {
        return testIntegerJComboBox;
    }
    @IVInt(minValue=testIntegerJComboBoxMin,maxValue=testIntegerJComboBoxMax,defaultValue=testIntegerJComboBoxDef)
    public void setTestIntegerJComboBox( int testIntegerJComboBox )
    {
        this.testIntegerJComboBox = testIntegerJComboBox;
    }

    public String getTestFMTString()
    {
        return testFMTString;
    }
    public void setTestFMTString( String testFMTString )
    {
        this.testFMTString = testFMTString;
    }

    public int getTestIntegerJSlider()
    {
        return testIntegerJSlider;
    }
    @IVInt(minValue=testIntegerJSliderMin,maxValue=testIntegerJSliderMax,defaultValue=testIntegerJSliderDef)
    public void setTestIntegerJSlider( int testIntegerJSlider )
    {
        this.testIntegerJSlider = testIntegerJSlider;
    }

    public String getTestString()
    {
        return testString;
    }
    public void setTestString( String testString )
    {
        this.testString = testString;
    }

    public int getTestIntegerLimitedIntegerJTextField()
    {
        return testIntegerLimitedIntegerJTextField;
    }
    @IVInt(minValue=testIntegerLimitedIntegerJTextFieldMin,maxValue=testIntegerLimitedIntegerJTextFieldMax,defaultValue=testIntegerLimitedIntegerJTextFieldDef)
    public void setTestIntegerLimitedIntegerJTextField(
            int testIntegerLimitedIntegerJTextField )
    {
        this.testIntegerLimitedIntegerJTextField = testIntegerLimitedIntegerJTextField;
    }

    public int getTestIntegerJSpinner()
    {
        return testIntegerJSpinner;
    }
    @IVInt(minValue=testIntegerJSpinnerMin,maxValue=testIntegerJSpinnerMax,defaultValue=testIntegerJSpinnerDef)
    public void setTestIntegerJSpinner( int testIntegerJSpinner )
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
    private int getRandom(int min,int max)
    {
       return rand.nextInt( max - min + 1 ) + min;
    }
    
    @IVIgnore
   private String getRandomFMT()
    {
        byte[] bytes = new byte[4];
        
        rand.nextBytes( bytes );
        
        return  String.format( "%1$02X-%2$02X-%3$02X-%4$02X", bytes[0], bytes[1], bytes[2], bytes[3]);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
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
        builder.append( "]" );
        return builder.toString();
    }
}
