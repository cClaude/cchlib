package com.googlecode.cchlib.apps.editresourcesbundle.html;

import java.util.function.BiConsumer;
import com.googlecode.cchlib.apps.editresourcesbundle.compare.CompareResourcesBundleFrame;

enum HTMLPreviewDialogAction
{
    ACTIONCMD_W3C_LENGTH_UNITS(
        (d,frame) -> d.actionW3cLengthUnits( frame )
        ),
    ACTIONCMD_HONOR_DISPLAY_PROPERTIES(
        (d,frame) -> d.actionHonorDisplayProperties( frame )
        );

    private BiConsumer<HTMLPreviewDialog,CompareResourcesBundleFrame> action;

    private HTMLPreviewDialogAction(
        final BiConsumer<HTMLPreviewDialog,CompareResourcesBundleFrame> action
        )
    {
        this.action = action;
    }

    public void performe(
        final HTMLPreviewDialog           htmlPreviewDialog,
        final CompareResourcesBundleFrame frame
        )
    {
        this.action.accept( htmlPreviewDialog, frame );
    }
}
