package com.googlecode.cchlib.tools.downloader.comboconfig;

import java.util.ArrayList;
import java.util.List;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderUIPanelEntry;

/**
 *
 */
public class DefaultComboBoxConfig
    implements GenericDownloaderAppComboBoxConfig
{
    private final String labelString;
    private int selectedIndex;
    private final List<GenericDownloaderUIPanelEntry.Item> items = new ArrayList<>();

    private static class Item implements GenericDownloaderUIPanelEntry.Item
    {
        private final String jComboBoxText;
        private final String selectedDescription;

        public Item( final String jComboBoxText, final String selectedDescription )
        {
            this.jComboBoxText = jComboBoxText;
            this.selectedDescription = selectedDescription;
        }

        @Override
        public String getJComboBoxText() {
            return this.jComboBoxText;
        }

        @Override
        public String getSelectedDescription() {
            return this.selectedDescription;
        }

        @Override
        public String toString()
        {
            final StringBuilder builder = new StringBuilder();
            builder.append( "Item [jComboBoxText=" );
            builder.append( this.jComboBoxText );
            builder.append( ", selectedDescription=" );
            builder.append( this.selectedDescription );
            builder.append( ']' );
            return builder.toString();
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

    @Override
    public String getComboBoxSelectedValue()
    {
        if( this.items.size() > 0 ) {
            return this.items.get( getSelectedIndex() ).getJComboBoxText();
            }
        else {
            return null;
            }
    }

    @Override
    public String getDescription()
    {
        return this.labelString;
    }

    @Override
    public List<GenericDownloaderUIPanelEntry.Item> getJComboBoxEntry()
    {
        return this.items;
    }

    @Override
    public String toString()
    {
        final int maxLen = 10;
        final StringBuilder builder = new StringBuilder();
        builder.append( "DefaultComboBoxConfig [getSelectedIndex()=" );
        builder.append( getSelectedIndex() );
        builder.append( ", getComboBoxSelectedValue()=" );
        builder.append( getComboBoxSelectedValue() );
        builder.append( ", getDescription()=" );
        builder.append( getDescription() );
        builder.append( ", items=" );
        builder.append( this.items != null ? this.items.subList( 0,
                Math.min( this.items.size(), maxLen ) ) : null );
        builder.append( ']' );
        return builder.toString();
    }
}
