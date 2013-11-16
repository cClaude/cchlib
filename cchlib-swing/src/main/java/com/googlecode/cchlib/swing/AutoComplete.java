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
    boolean isCaseSensitive();

    /**
     * TODOC
     * @param isCaseSensitive
     */
    void setCaseSensitive( boolean isCaseSensitive );

    /**
     * TODOC
     * @return TODOC
     */
    boolean isStrict();

    /**
     * TODOC
     * @param isStrict
     */
    void setStrict( boolean isStrict );

    /**
     * TODOC
     * @return TODOC
     */
    List<String> getDataList();

    /**
     * TODOC
     * @param dataList
     */
    void setDataList( List<String> dataList );

}
