package com.googlecode.cchlib.util.mappable;

import java.text.MessageFormat;

/**
 * NEEDDOC
 */
class MappableBuilderFormat
{
    private final MessageFormat messageFormatIteratorEntry;
    private final MessageFormat messageFormatIterableEntry;
    private final MessageFormat messageFormatEnumerationEntry;
    private final MessageFormat messageFormatArrayEntry;
    private final MessageFormat messageFormatMethodName;

    public MappableBuilderFormat(final MappableBuilderFactory factory )
    {
        this.messageFormatIteratorEntry     = new MessageFormat(factory.getMessageFormatIteratorEntry());
        this.messageFormatIterableEntry     = new MessageFormat(factory.getMessageFormatIterableEntry());
        this.messageFormatEnumerationEntry  = new MessageFormat(factory.getMessageFormatEnumerationEntry());
        this.messageFormatArrayEntry        = new MessageFormat(factory.getMessageFormatArrayEntry());
        this.messageFormatMethodName        = new MessageFormat(factory.getMessageFormatMethodName());
    }

    protected MessageFormat getMessageFormatIteratorEntry()
    {
        return this.messageFormatIteratorEntry;
    }

    protected MessageFormat getMessageFormatIterableEntry()
    {
        return this.messageFormatIterableEntry;
    }

    protected MessageFormat getMessageFormatEnumerationEntry()
    {
        return this.messageFormatEnumerationEntry;
    }

    protected MessageFormat getMessageFormatArrayEntry()
    {
        return this.messageFormatArrayEntry;
    }

    protected MessageFormat getMessageFormatMethodName()
    {
        return this.messageFormatMethodName;
    }
}
