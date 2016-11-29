package com.googlecode.cchlib.cli.apachecli;

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
 * @deprecated see {@link OptionBuilderInstance}
 */
@Deprecated
@SuppressWarnings("squid:S1133") // Not my code (copy of apachecli)
public final class OptionBuilder
{
    private static OptionBuilderInstance instance;

    /**
     * private constructor to prevent instances being created
     */
    private OptionBuilder()
    {
        // hide the constructor
    }

    private static OptionBuilderInstance getOptionBuilderInstance() {
        if( instance == null ) {
            instance = new OptionBuilderInstance();
        }
        return instance;
    }

    /**
     * The next Option created will have the following long option value.
     *
     * @param newLongopt the long option value
     * @return the OptionBuilder instance
     */
    public static OptionBuilderInstance withLongOpt(final String newLongopt)
    {
        return getOptionBuilderInstance().withLongOpt( newLongopt );
    }

    /**
     * The next Option created will require an argument value.
     *
     * @return the OptionBuilder instance
     */
    public static OptionBuilderInstance hasArg()
    {
        return getOptionBuilderInstance().hasArg();
    }

    /**
     * The next Option created will require an argument value if
     * <code>hasArg</code> is true.
     *
     * @param hasArg if true then the Option has an argument value
     * @return the OptionBuilder instance
     */
    public static OptionBuilderInstance hasArg(final boolean hasArg)
    {
        return getOptionBuilderInstance().hasArg( hasArg );
    }

    /**
     * The next Option created will have the specified argument value name.
     *
     * @param name the name for the argument value
     * @return the OptionBuilder instance
     */
    public static OptionBuilderInstance withArgName(final String name)
    {
        return getOptionBuilderInstance().withArgName( name );
    }

    /**
     * The next Option created will be required.
     *
     * @return the OptionBuilder instance
     */
    public static OptionBuilderInstance isRequired()
    {
        return getOptionBuilderInstance().isRequired();
    }

    /**
     * The next Option created uses <code>sep</code> as a means to
     * separate argument values.
     *
     * <b>Example:</b>
     * <pre>
     * Option opt = OptionBuilder.withValueSeparator(':')
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
    public static OptionBuilderInstance withValueSeparator(final char sep)
    {
        return getOptionBuilderInstance().withValueSeparator( sep );
    }

    /**
     * The next Option created uses '<code>=</code>' as a means to
     * separate argument values.
     *
     * <b>Example:</b>
     * <pre>
     * Option opt = OptionBuilder.withValueSeparator()
     *                           .create('D');
     *
     * CommandLine line = parser.parse(args);
     * String propertyName = opt.getValue(0);
     * String propertyValue = opt.getValue(1);
     * </pre>
     *
     * @return the OptionBuilder instance
     */
    public static OptionBuilderInstance withValueSeparator()
    {
        return getOptionBuilderInstance().withValueSeparator();
    }

    /**
     * The next Option created will be required if <code>required</code>
     * is true.
     *
     * @param newRequired if true then the Option is required
     * @return the OptionBuilder instance
     */
    public static OptionBuilderInstance isRequired(final boolean newRequired)
    {
        return getOptionBuilderInstance().isRequired( newRequired );
    }

    /**
     * The next Option created can have unlimited argument values.
     *
     * @return the OptionBuilder instance
     */
    public static OptionBuilderInstance hasArgs()
    {
        return getOptionBuilderInstance().hasArgs();
    }

    /**
     * The next Option created can have <code>num</code> argument values.
     *
     * @param num the number of args that the option can have
     * @return the OptionBuilder instance
     */
    public static OptionBuilderInstance hasArgs(final int num)
    {
        return getOptionBuilderInstance().hasArgs( num );
    }

    /**
     * The next Option can have an optional argument.
     *
     * @return the OptionBuilder instance
     */
    public static OptionBuilderInstance hasOptionalArg()
    {
        return getOptionBuilderInstance().hasOptionalArg();
    }

    /**
     * The next Option can have an unlimited number of optional arguments.
     *
     * @return the OptionBuilder instance
     */
    public static OptionBuilderInstance hasOptionalArgs()
    {
        return getOptionBuilderInstance().hasOptionalArgs();
    }

    /**
     * The next Option can have the specified number of optional arguments.
     *
     * @param numArgs - the maximum number of optional arguments
     * the next Option created can have.
     * @return the OptionBuilder instance
     */
    public static OptionBuilderInstance hasOptionalArgs(final int numArgs)
    {
        return getOptionBuilderInstance().hasOptionalArgs( numArgs );
    }

    /**
     * The next Option created will have a value that will be an instance
     * of <code>type</code>.
     *
     * @param newType the type of the Options argument value
     * @return the OptionBuilder instance
     */
    public static OptionBuilderInstance withType(final Object newType)
    {
        return getOptionBuilderInstance().withType( newType );
    }

    /**
     * The next Option created will have the specified description
     *
     * @param newDescription a description of the Option's purpose
     * @return the OptionBuilder instance
     */
    public static OptionBuilderInstance withDescription(final String newDescription)
    {
        return getOptionBuilderInstance().withDescription( newDescription );
    }
}
