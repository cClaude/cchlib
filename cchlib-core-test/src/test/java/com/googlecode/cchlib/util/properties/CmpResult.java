package com.googlecode.cchlib.util.properties;

enum CmpResult
{
    CMP_ERR_A_STRING_FIELD( 1 ),
    CMP_ERR_A_INT_FIELD( 2 ),
    CMP_ERR_A_FLOAT_FIELD( 3 ),
    CMP_ERR_SOMEBOOL1_FIELD( 4 ),
    CMP_ERR_SOMEBOOL2_FIELD( 5 ),
    CMP_ERR_SOMEBOOL3_FIELD( 6 ),
    CMP_ERR_SOMEBOOL4_FIELD( 7 ),
    CMP_ERR_TEXEFIELD_TEXT( 8 ),
    CMP_ERR_CHECKBOX_IS_SELECTED( 9 ),
    CMP_ERR_COMBOBOX_SELECTED_INDEX( 10 ),
    ;
    private final int cmpResult;

    private CmpResult( final int cmpResult )
    {
        this.cmpResult = cmpResult;
    }

    public int getValue()
    {
        return cmpResult;
    }
}