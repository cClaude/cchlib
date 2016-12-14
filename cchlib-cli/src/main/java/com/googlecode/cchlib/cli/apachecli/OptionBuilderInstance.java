package com.googlecode.cchlib.cli.apachecli;

import javax.annotation.concurrent.ThreadSafe;
import org.apache.commons.cli.Option;

/**
 * {@link OptionBuilderInstance} allows the user to create Options
 * using descriptive methods.
 *
 * <p>Details on the Builder pattern can be found at
 * <a href="http://c2.com/cgi-bin/wiki?BuilderPattern">
 * http://c2.com/cgi-bin/wiki?BuilderPattern</a>.</p>
 *
 * @author John Keyes (john at integralsource.com)
 * @author Claude Choisnet
 * @since 4.2
 */
@ThreadSafe
public final class OptionBuilderInstance
{
    /** long option */
    private String longopt;

    /** option description */
    private String description;

    /** argument name */
    private String argName;

    /** is required? */
    private boolean required;

    /** the number of arguments */
    private int numberOfArgs = Option.UNINITIALIZED;

    /** option type */
    private Object type;

    /** option can have an optional argument value */
    private boolean optionalArg;

    /** value separator for argument value */
    private char valuesep;

    /**
     * Create a {@link OptionBuilderInstance}
     */
    public OptionBuilderInstance()
    {
        // Empty
    }

    /**
     * Resets the member variables to their default values.
     */
    public void reset()
    {
        this.description = null;
        this.argName = "arg";
        this.longopt = null;
        this.type = null;
        this.required = false;
        this.numberOfArgs = Option.UNINITIALIZED;

        // PMM 9/6/02 - these were missing
        this.optionalArg = false;
        this.valuesep = (char) 0;
    }

    /**
     * The next Option created will have the following long option value.
     *
     * @param newLongopt the long option value
     * @return the {@link OptionBuilderInstance} instance
     */
    public OptionBuilderInstance withLongOpt(final String newLongopt)
    {
        this.longopt = newLongopt;

        return this;
    }

    /**
     * The next Option created will require an argument value.
     *
     * @return the {@link OptionBuilderInstance} instance
     */
    public OptionBuilderInstance hasArg()
    {
        this.numberOfArgs = 1;

        return this;
    }

    /**
     * The next Option created will require an argument value if
     * {@code hasArg} is true.
     *
     * @param hasArg if true then the Option has an argument value
     * @return the {@link OptionBuilderInstance} instance
     */
    public OptionBuilderInstance hasArg(final boolean hasArg)
    {
        this.numberOfArgs = hasArg ? 1 : Option.UNINITIALIZED;

        return this;
    }

    /**
     * The next Option created will have the specified argument value name.
     *
     * @param name the name for the argument value
     * @return the {@link OptionBuilderInstance} instance
     */
    public OptionBuilderInstance withArgName(final String name)
    {
        this.argName = name;

        return this;
    }

    /**
     * The next Option created will be required.
     *
     * @return the {@link OptionBuilderInstance} instance
     */
    public OptionBuilderInstance isRequired()
    {
        this.required = true;

        return this;
    }

    /**
     * The next Option created uses {@code sep} as a means to
     * separate argument values.
     * <p>
     * <b>Example:</b>
     * <pre>
     * Option opt = withValueSeparator(':')
     *                           .create('D');
     *
     * CommandLine line = parser.parse(args);
     * String propertyName = opt.getValue(0);
     * String propertyValue = opt.getValue(1);
     * </pre>
     *
     * @param sep The value separator to be used for the argument values.
     *
     * @return the {@link OptionBuilderInstance} instance
     */
    public OptionBuilderInstance withValueSeparator(final char sep)
    {
        this.valuesep = sep;

        return this;
    }

    /**
     * The next Option created uses '{@code =}' as a means to
     * separate argument values.
     * <p>
     * <b>Example:</b>
     * <pre>
     * Option opt = withValueSeparator()
     *                           .create('D');
     *
     * CommandLine line = parser.parse(args);
     * String propertyName = opt.getValue(0);
     * String propertyValue = opt.getValue(1);
     * </pre>
     *
     * @return the {@link OptionBuilderInstance} instance
     */
    public OptionBuilderInstance withValueSeparator()
    {
        this.valuesep = '=';

        return this;
    }

    /**
     * The next Option created will be required if {@code required}
     * is true.
     *
     * @param required if true then the Option is required
     * @return the {@link OptionBuilderInstance} instance
     */
    public OptionBuilderInstance isRequired( final boolean required )
    {
        this.required = required;

        return this;
    }

    /**
     * The next Option created can have unlimited argument values.
     *
     * @return the {@link OptionBuilderInstance} instance
     */
    public OptionBuilderInstance hasArgs()
    {
        this.numberOfArgs = Option.UNLIMITED_VALUES;

        return this;
    }

    /**
     * The next Option created can have {@code num} argument values.
     *
     * @param num the number of args that the option can have
     * @return the {@link OptionBuilderInstance} instance
     */
    public OptionBuilderInstance hasArgs(final int num)
    {
        this.numberOfArgs = num;

        return this;
    }

    /**
     * The next Option can have an optional argument.
     *
     * @return the {@link OptionBuilderInstance} instance
     */
    public OptionBuilderInstance hasOptionalArg()
    {
        this.numberOfArgs = 1;
        this.optionalArg = true;

        return this;
    }

    /**
     * The next Option can have an unlimited number of optional arguments.
     *
     * @return the {@link OptionBuilderInstance} instance
     */
    public OptionBuilderInstance hasOptionalArgs()
    {
        this.numberOfArgs = Option.UNLIMITED_VALUES;
        this.optionalArg = true;

        return this;
    }

    /**
     * The next Option can have the specified number of optional arguments.
     *
     * @param numArgs - the maximum number of optional arguments
     * the next Option created can have.
     * @return the {@link OptionBuilderInstance} instance
     */
    public OptionBuilderInstance hasOptionalArgs(final int numArgs)
    {
        this.numberOfArgs = numArgs;
        this.optionalArg = true;

        return this;
    }

    /**
     * The next Option created will have a value that will be an instance
     * of {@code type}.
     *
     * @param newType the type of the Options argument value
     * @return the {@link OptionBuilderInstance} instance
     */
    public OptionBuilderInstance withType(final Object newType)
    {
        this.type = newType;

        return this;
    }

    /**
     * The next Option created will have the specified description
     *
     * @param newDescription a description of the Option's purpose
     * @return the {@link OptionBuilderInstance} instance
     */
    public OptionBuilderInstance withDescription(final String newDescription)
    {
        this.description = newDescription;

        return this;
    }

    /**
     * Create an {@link Option} using the current settings and with
     * the specified Option {@code char}.
     *
     * @param opt the character representation of the {@link Option}
     * @return the {@link Option} instance
     * @throws IllegalArgumentException if {@code opt} is not
     * a valid character.  See Option.
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public Option create(final char opt) throws IllegalArgumentException
    {
        return create(String.valueOf(opt));
    }

    /**
     * Create an {@link Option} using the current settings
     *
     * @return the {@link Option} instance
     * @throws IllegalArgumentException if {@code longOpt} has not been set.
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public Option create() throws IllegalArgumentException
    {
        if (this.longopt == null)
        {
            reset();
            throw new IllegalArgumentException("must specify longopt");
        }

        return create(null);
    }

    /**
     * Create an {@link Option} using the current settings and with
     * the specified Option {@code char}.
     *
     * @param opt the {@link String} representation
     * of the {@link Option}
     * @return the {@link Option} instance
     * @throws IllegalArgumentException if {@code opt} is not
     * a valid character.  See Option.
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public Option create(final String opt) throws IllegalArgumentException
    {
        Option option = null;
        try {
            // create the option
            option = new Option(opt, this.description);

            // set the option properties
            option.setLongOpt(this.longopt);
            option.setRequired(this.required);
            option.setOptionalArg(this.optionalArg);
            option.setArgs(this.numberOfArgs);
            option.setType(this.type);
            option.setValueSeparator(this.valuesep);
            option.setArgName(this.argName);
        } finally {
            // reset the OptionBuilder properties
            reset();
        }

        // return the Option instance
        return option;
    }
}
