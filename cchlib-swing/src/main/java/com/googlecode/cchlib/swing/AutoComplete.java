package com.googlecode.cchlib.swing;

import java.util.List;

/**
 * NEEDDOC
 */
public interface AutoComplete
{
    /**
     * NEEDDOC
     * @return NEEDDOC
     */
    boolean isCaseSensitive();

    /**
     * NEEDDOC
     * @param isCaseSensitive NEEDDOC
     */
    void setCaseSensitive( boolean isCaseSensitive );

    /**
     * NEEDDOC
     * @return NEEDDOC
     */
    boolean isStrict();

    /**
     * NEEDDOC
     * @param isStrict NEEDDOC
     */
    void setStrict( boolean isStrict );

    /**
     * NEEDDOC
     * @return NEEDDOC
     */
    List<String> getDataList();

    /**
     * NEEDDOC
     * @param dataList NEEDDOC
     */
    void setDataList( List<String> dataList );
}
