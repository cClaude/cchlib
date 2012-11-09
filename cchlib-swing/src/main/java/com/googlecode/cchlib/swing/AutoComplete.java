package com.googlecode.cchlib.swing;

import java.util.List;

/**
 * TODOC
 *
 */
public interface AutoComplete
{

    /**
     * TODOC
     * @return TODOC
     */
    public boolean isCaseSensitive();

    /**
     * TODOC
     * @param isCaseSensitive
     */
    public void setCaseSensitive( boolean isCaseSensitive );

    /**
     * TODOC
     * @return TODOC
     */
    public boolean isStrict();

    /**
     * TODOC
     * @param isStrict
     */
    public void setStrict( boolean isStrict );

    /**
     * TODOC
     * @return TODOC
     */
    public List<String> getDataList();

    /**
     * TODOC
     * @param dataList
     */
    public void setDataList( List<String> dataList );

}
