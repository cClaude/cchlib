package com.googlecode.cchlib.swing.batchrunner.ihm;

import java.util.ResourceBundle;

/**
 * Default implementation of {@link BRLocaleResources} based on
 * a {@link ResourceBundle}
 *
 * @since 4.1.8
 */
public class DefaultBRLocaleResources implements BRLocaleResources
{
    protected ResourceBundle resourceBundle;

    public DefaultBRLocaleResources( ResourceBundle resourceBundle )
    {
        if( resourceBundle == null ) {
            this.resourceBundle = ResourceBundle.getBundle(
                DefaultBRLocaleResources.class.getPackage().getName()
                    + ".DefaultResourceBundle"
                );
            }
        else {
            this.resourceBundle = resourceBundle;
            }
    }

    public DefaultBRLocaleResources()
    {
        this( null );
    }

    @Override
    public String getTextAddSourceFile()
    {
        return resourceBundle.getString( "BRLocaleResources.TextAddSourceFile" );
    }

    @Override
    public String getTextSetDestinationFolder()
    {
        return resourceBundle.getString( "BRLocaleResources.TextSetDestinationFolder" );
    }

    @Override
    public String getTextClearSourceFileList()
    {
        return resourceBundle.getString( "BRLocaleResources.TextClearSourceFileList" );
    }

    @Override
    public String getTextDoAction()
    {
        return resourceBundle.getString( "BRLocaleResources.TextDoAction" );
    }

    @Override
    public String getTextJFileChooserInitializerTitle()
    {
        return resourceBundle.getString( "BRLocaleResources.TextJFileChooserInitializerTitle" );
    }

    @Override
    public String getTextJFileChooserInitializerMessage()
    {
        return resourceBundle.getString( "BRLocaleResources.TextJFileChooserInitializerMessage" );
    }

    @Override
    public String getTextNoSourceFile()
    {
        return resourceBundle.getString( "BRLocaleResources.TextNoSourceFile" );
    }

    @Override
    public String getTextNoDestinationFolder()
    {
        return resourceBundle.getString( "BRLocaleResources.TextNoDestinationFolder" );
    }

    @Override
    public String getTextWorkingOn_FMT()
    {
        return resourceBundle.getString( "BRLocaleResources.TextWorkingOn_FMT" );
    }

    @Override
    public String getTextUnexpectedExceptionTitle()
    {
        return resourceBundle.getString( "BRLocaleResources.TextUnexpectedExceptionTitle" );
    }

    @Override
    public String getTextExitRequestTitle()
    {
        return resourceBundle.getString( "BRLocaleResources.TextExitRequestTitle" );
    }

    @Override
    public String getTextExitRequestMessage()
    {
        return resourceBundle.getString( "BRLocaleResources.TextExitRequestMessage" );
    }

    @Override
    public String getTextExitRequestYes()
    {
        return resourceBundle.getString( "BRLocaleResources.TextExitRequestYes" );
    }

    @Override
    public String getTextExitRequestNo()
    {
        return resourceBundle.getString( "BRLocaleResources.TextExitRequestNo" );
    }



    //
    // LazyBatchRunnerLocaleResources
    //
/*

    @Override//LazyBatchRunnerLocaleResources
    public String getTextEndOfBatch()
    {
        return resourceBundle.getString( "LazyBatchRunnerLocaleResources.TextEndOfBatch" );
    }
    @Override//LazyBatchRunnerLocaleResources
    public String getTextIOExceptionDuringBatch()
    {
        return resourceBundle.getString( "LazyBatchRunnerLocaleResources.TextIOExceptionDuringBatch" );
    }
    @Override//LazyBatchRunnerLocaleResources
    public String[] getTextIOExceptionDuringBatchButtons()
    {
        String[] buttons = {
            resourceBundle.getString( "LazyBatchRunnerLocaleResources.TextIOExceptionDuringBatchButtons.Continue" ),
            resourceBundle.getString( "LazyBatchRunnerLocaleResources.TextIOExceptionDuringBatchButtons.Cancel" )
            };

        return buttons;
    }

    @Override//LazyBatchRunnerLocaleResources
    public String getTextProgressMonitorTitle_FMT()
    {
        return resourceBundle.getString( "LazyBatchRunnerLocaleResources.TextProgressMonitorTitle_FMT" );
    }
*/

}
