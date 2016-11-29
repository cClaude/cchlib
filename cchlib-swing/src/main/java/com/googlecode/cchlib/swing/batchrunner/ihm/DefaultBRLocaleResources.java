package com.googlecode.cchlib.swing.batchrunner.ihm;

import java.util.ResourceBundle;
import com.googlecode.cchlib.swing.batchrunner.misc.MissingLocaleStringException;

/**
 * Default implementation of {@link BRPanelLocaleResources} based on
 * a {@link ResourceBundle}
 *
 * @since 4.1.8
 */
public class DefaultBRLocaleResources implements BRPanelLocaleResources
{
    private static final long serialVersionUID = 1L;

    private final String valueTextAddSourceFile;
    private final String valueTextClearSourceFileList;
    private final String valueTextDoAction;
    private final String valueTextExitRequestMessage;
    private final String valueTextExitRequestNo;
    private final String valueTextExitRequestTitle;
    private final String valueTextExitRequestYes;
    private final String valueTextJFileChooserInitializerMessage;
    private final String valueTextJFileChooserInitializerTitle;
    private final String valueTextNoDestinationFolder;
    private final String valueTextNoSourceFile;
    private final String valueTextSetDestinationFolder;
    private final String valueTextUnexpectedExceptionTitle;
    private final String valueTextWorkingOn_FMT;

    /**
     * NEEDDOC
     * @param builder NEEDDOC
     * @throws MissingLocaleStringException if a resource missing
     */
    public DefaultBRLocaleResources(
            final DefaultBRLocaleResourcesBuilder builder
            ) throws MissingLocaleStringException
    {
        this.valueTextAddSourceFile =
            builder.getString( "BRLocaleResources.TextAddSourceFile" );
        this.valueTextClearSourceFileList =
            builder.getString( "BRLocaleResources.TextClearSourceFileList" );
        this.valueTextDoAction =
            builder.getString( "BRLocaleResources.TextDoAction" );
        this.valueTextExitRequestMessage =
            builder.getString( "BRLocaleResources.TextExitRequestMessage" );
        this.valueTextExitRequestNo =
            builder.getString( "BRLocaleResources.TextExitRequestNo" );
        this.valueTextExitRequestTitle =
            builder.getString( "BRLocaleResources.TextExitRequestTitle" );
        this.valueTextExitRequestYes =
            builder.getString( "BRLocaleResources.TextExitRequestYes" );
        this.valueTextJFileChooserInitializerMessage =
            builder.getString( "BRLocaleResources.TextJFileChooserInitializerMessage" );
        this.valueTextJFileChooserInitializerTitle =
            builder.getString( "BRLocaleResources.TextJFileChooserInitializerTitle" );
        this.valueTextNoDestinationFolder =
            builder.getString( "BRLocaleResources.TextNoDestinationFolder" );
        this.valueTextNoSourceFile =
            builder.getString( "BRLocaleResources.TextNoSourceFile" );
        this.valueTextSetDestinationFolder =
            builder.getString( "BRLocaleResources.TextSetDestinationFolder" );
        this.valueTextUnexpectedExceptionTitle =
            builder.getString( "BRLocaleResources.TextUnexpectedExceptionTitle" );
        this.valueTextWorkingOn_FMT =
            builder.getString( "BRLocaleResources.TextWorkingOn_FMT" );
    }

    public DefaultBRLocaleResources() throws MissingLocaleStringException
    {
        this( new DefaultBRLocaleResourcesBuilder() );
    }

    @Override
    public String getTextAddSourceFile()
    {
        return this.valueTextAddSourceFile;
    }

    @Override
    public String getTextSetDestinationFolder()
    {
        return this.valueTextSetDestinationFolder;
    }

    @Override
    public String getTextClearSourceFileList()
    {
        return this.valueTextClearSourceFileList;
    }

    @Override
    public String getTextDoAction()
    {
        return this.valueTextDoAction;
    }

    @Override
    public String getTextJFileChooserInitializerTitle()
    {
        return this.valueTextJFileChooserInitializerTitle;
    }

    @Override
    public String getTextJFileChooserInitializerMessage()
    {
        return this.valueTextJFileChooserInitializerMessage;
    }

    @Override
    public String getTextNoSourceFile()
    {
        return this.valueTextNoSourceFile;
    }

    @Override
    public String getTextNoDestinationFolder()
    {
        return this.valueTextNoDestinationFolder;
    }

    @Override
    public String getTextWorkingOn_FMT()
    {
        return this.valueTextWorkingOn_FMT;
    }

    @Override
    public String getTextUnexpectedExceptionTitle()
    {
        return this.valueTextUnexpectedExceptionTitle;
    }

    @Override
    public String getTextExitRequestTitle()
    {
        return this.valueTextExitRequestTitle;
    }

    @Override
    public String getTextExitRequestMessage()
    {
        return this.valueTextExitRequestMessage;
    }

    @Override
    public String getTextExitRequestYes()
    {
        return this.valueTextExitRequestYes;
    }

    @Override
    public String getTextExitRequestNo()
    {
        return this.valueTextExitRequestNo;
    }
}
