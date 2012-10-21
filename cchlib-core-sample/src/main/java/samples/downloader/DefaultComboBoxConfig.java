package samples.downloader;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class DefaultComboBoxConfig
    implements GenericDownloaderAppInterface.ComboBoxConfig
{
    private String labelString;
    private int selectedIndex;
    private List<GenericDownloaderUIPanelEntry.Item> items = new ArrayList<>();
    
    
    private class Item implements GenericDownloaderUIPanelEntry.Item
    {
		private String jComboBoxText;
		private String selectedDescription;
		
		public Item( final String jComboBoxText, final String selectedDescription )
		{
			this.jComboBoxText = jComboBoxText;
			this.selectedDescription = selectedDescription;
		}

		@Override
		public String getJComboBoxText() {
			return jComboBoxText;
		}

		@Override
		public String getSelectedDescription() {
			return selectedDescription;
		}
    }
    /**
     * 
     * @param labelString
     * @param comboBoxValues
     * @param labelStrings
     */
    public DefaultComboBoxConfig(
        final String   labelString,
        final String[] comboBoxValues,
        final String[] labelStrings
       )
    {
       this.labelString = labelString;

       if( comboBoxValues.length != labelStrings.length ) {
           throw new RuntimeException( "Need same number of combobox description that labels" );
           }

       for( int i = 0; i < comboBoxValues.length; i++ ) {
           this.items.add( new Item( comboBoxValues[ i ], labelStrings[ i ] ) );
           }
    }

    /**
     * 
     * @param labelString
     * @param minValue
     * @param maxValue
     * @param comments
     */
    public DefaultComboBoxConfig( 
        final String    labelString,
        final int       minValue, 
        final int       maxValue, 
        final String    comments
        )
    {
        this.labelString = labelString;

        if( minValue >= maxValue ) {
            throw new RuntimeException( "minValue need to be less than maxValue" );
            }

        for( int i = 0; i < (maxValue - minValue); i++ ) {
            //this.comboBoxValueList.add( Integer.toString( i + minValue ) );
            //this.labelStringList.add( comments );
            this.items.add( new Item( Integer.toString( i + minValue ), comments ) );
            }
    }

    @Override
    public int getSelectedIndex()
    {
        return this.selectedIndex;
    }

    @Override
    public void setSelectedIndex( final int selectedIndex )
    {
        this.selectedIndex = selectedIndex;
    }

//    @Override
//    public String getLabelString()
//    {
//        return this.labelString;
//    }

//    @Override
//    public String getLabelString( final int index )
//    {
//    	try {
//            return this.labelStringList.get( index );
//    		}
//    	catch( IndexOutOfBoundsException e ) {
//    		// TODO: logger !!!
//    		
//            return "";
//    		}
//    }

//    @Override
//    public Iterable<String> getComboBoxValues()
//    {
//        return Collections.unmodifiableCollection( this.comboBoxValueList );
//    }

    @Override
    public String getComboBoxSelectedValue()
    {
        //return this.comboBoxValueList.get( getSelectedIndex() );
        return this.items.get( getSelectedIndex() ).getJComboBoxText();
    }

	@Override
	public String getDescription()
	{
		return this.labelString;
	}

	@Override
	public List<GenericDownloaderUIPanelEntry.Item> getJComboBoxEntry()
	{
		return items;
	}
}
