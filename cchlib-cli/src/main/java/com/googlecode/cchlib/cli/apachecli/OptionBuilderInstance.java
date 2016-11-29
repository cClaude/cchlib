package com.googlecode.cchlib.cli.apachecli;

import org.apache.commons.cli.Option;

/**
 * OptionBuilder allows the user to create Options using descriptive methods.
 *
 * <p>Details on the Builder pattern can be found at
 * <a href="http://c2.com/cgi-bin/wiki?BuilderPattern">
 * http://c2.com/cgi-bin/wiki?BuilderPattern</a>.</p>
 *
 * @author John Keyes (john at integralsource.com)
 * @version $Revision: 754830 $, $Date: 2009-03-16 00:26:44 -0700 (Mon, 16 Mar 2009) $
 * @since 1.0
 */
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
     * private constructor to prevent instances being created
     */
    public OptionBuilderInstance()
    {
        // hide the constructor
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
     * @return the OptionBuilder instance
     */
    public OptionBuilderInstance withLongOpt(final String newLongopt)
    {
        this.longopt = newLongopt;

        return this;
    }

    /**
     * The next Option created will require an argument value.
     *
     * @return the OptionBuilder instance
     */
    public OptionBuilderInstance hasArg()
    {
        this.numberOfArgs = 1;

        return this;
    }

    /**
     * The next Option created will require an argument value if
     * <code>hasArg</code> is true.
     *
     * @param hasArg if true then the Option has an argument value
     * @return the OptionBuilder instance
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
     * @return the OptionBuilder instance
     */
    public OptionBuilderInstance withArgName(final String name)
    {
        this.argName = name;

        return this;
    }

    /**
     * The next Option created will be required.
     *
     * @return the OptionBuilder instance
     */
    public OptionBuilderInstance isRequired()
    {
        this.required = true;

        return this;
    }

    /**
     * The next Option created uses <code>sep</code> as a means to
     * separate argument values.
     *
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
     * @return the OptionBuilder instance
     */
    public OptionBuilderInstance withValueSeparator(final char sep)
    {
        this.valuesep = sep;

        return this;
    }

    /**
     * The next Option created uses '<code>=</code>' as a means to
     * separate argument values.
     *
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
     * @return the OptionBuilder instance
     */
    public OptionBuilderInstance withValueSeparator()
    {
        this.valuesep = '=';

        return this;
    }

    /**
     * The next Option created will be required if <code>required</code>
     * is true.
     *
     * @param newRequired if true then the Option is required
     * @return the OptionBuilder instance
     */
    public OptionBuilderInstance isRequired(final boolean newRequired)
    {
        this.required = newRequired;

        return this;
    }

    /**
     * The next Option created can have unlimited argument values.
     *
     * @return the OptionBuilder instance
     */
    public OptionBuilderInstance hasArgs()
    {
        this.numberOfArgs = Option.UNLIMITED_VALUES;

        return this;
    }

    /**
     * The next Option created can have <code>num</code> argument values.
     *
     * @param num the number of args that the option can have
     * @return the OptionBuilder instance
     */
    public OptionBuilderInstance hasArgs(final int num)
    {
        this.numberOfArgs = num;

        return this;
    }

    /**
     * The next Option can have an optional argument.
     *
     * @return the OptionBuilder instance
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
     * @return the OptionBuilder instance
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
     * @return the OptionBuilder instance
     */
    public OptionBuilderInstance hasOptionalArgs(final int numArgs)
    {
        this.numberOfArgs = numArgs;
        this.optionalArg = true;

        return this;
    }

    /**
     * The next Option created will have a value that will be an instance
     * of <code>type</code>.
     *
     * @param newType the type of the Options argument value
     * @return the OptionBuilder instance
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
     * @return the OptionBuilder instance
     */
    public OptionBuilderInstance withDescription(final String newDescription)
    {
        this.description = newDescription;

        return this;
    }

    /**
     * Create an Option using the current settings and with
     * the specified Option <code>char</code>.
     *
     * @param opt the character representation of the Option
     * @return the Option instance
     * @throws IllegalArgumentException if <code>opt</code> is not
     * a valid character.  See Option.
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public Option create(final char opt) throws IllegalArgumentException
    {
        return create(String.valueOf(opt));
    }

    /**
     * Create an Option using the current settings
     *
     * @return the Option instance
     * @throws IllegalArgumentException if <code>longOpt</code> has not been set.
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
     * Create an Option using the current settings and with
     * the specified Option <code>char</code>.
     *
     * @param opt the <code>java.lang.String</code> representation
     * of the Option
     * @return the Option instance
     * @throws IllegalArgumentException if <code>opt</code> is not
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
