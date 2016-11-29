package com.googlecode.cchlib.cli.apachecli;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;

public class OptionHelper
{
    private static class OptionsFromOptionEnum<E extends Enum<E> & IsOption>
    {
        private final Map<Object,OptionGroup> optionGroups = new HashMap<>();

        private final Options options;

        OptionsFromOptionEnum( final Options options )
        {
            this.options = options;
        }

        void addAll( final Class<E> enumType )
        {
            for( final E optionEnum : enumType.getEnumConstants() ) {
                add( optionEnum );
            }

            for( final OptionGroup optionGroup : this.optionGroups.values() ) {
                this.options.addOptionGroup( optionGroup );
            }
        }

        private void add( final E optionEnum )
        {
            final Option option = optionEnum.getOption();

            checkIfValueExist( option );

            boolean added = false;

            if( optionEnum instanceof InGroup ) {
                final Object groupId = ((InGroup)optionEnum).getGroupId();

                if( groupId != null ) {
                    addToOptionGroup( groupId, option );

                    added = true;
                }
            }

            if( ! added ) {
                this.options.addOption( option );
            }
        }

        private void checkIfValueExist( final Option option )
        {
            OptionHelper.checkIfValueExist( this.options, option );
        }

        private void addToOptionGroup( final Object groupId, final Option option )
        {
            final OptionGroup optionGroup = getOptionGroup( groupId );

            optionGroup.addOption( option );
        }

        private OptionGroup getOptionGroup( final Object groupId )
        {
            OptionGroup optionGroup = this.optionGroups.get( groupId );

            if( optionGroup == null ) {
                optionGroup = new OptionGroup();
                this.optionGroups.put( groupId, optionGroup );
            }

            return optionGroup;
        }
    }

    private OptionHelper()
    {
        // All static
    }

    @SuppressWarnings("squid:S1172") // Unused method parameters should be removed (squid:S1172) - Well, this method is used...
    private static String toOpt( final IsOption optionEnum )
    {
        final Option option = optionEnum.getOption();

        return option.getOpt();
    }

    public static <E extends Enum<E> & IsOption> void addOptionsFromOptionEnum(
        final Options  options,
        final Class<E> enumClass
        )
    {
        final OptionsFromOptionEnum<E> optionsFromEnum = new OptionsFromOptionEnum<>( options );

        optionsFromEnum.addAll( enumClass );
    }

    public static <E extends Enum<E> & IsOption> Set<E> getSelectedOptions(
            final CommandLine   commandLine,
            final Class<E>      enumType
            )
    {
        final EnumSet<E> result = EnumSet.noneOf( enumType );

        for( final E optionEnum : enumType.getEnumConstants() ) {
            if( commandLine.hasOption( toOpt( optionEnum ) ) ) {
                result.add( optionEnum );
            }
        }

        return result;
    }

    public static <E extends Enum<E> & IsOption> String getOptionValue(
            final CommandLine commandLine,
            final E           optionEnum
            )
    {
        return commandLine.getOptionValue( toOpt( optionEnum ) );
    }

    public static <E extends Enum<E> & IsOption> boolean hasOption(
            final CommandLine commandLine,
            final E           optionEnum
            )
    {
        return commandLine.hasOption( toOpt( optionEnum ) );
    }

    public static <E extends Enum<E> & IsOption> String[] getOptionValues(
            final CommandLine commandLine,
            final E           optionEnum
            )
    {
        return commandLine.getOptionValues( toOpt( optionEnum ) );
    }

    public static Option newOption( final char opt, final String longOpt, final String description, final int numberOfArgs )
    {
        final Option option = new Option( Character.toString( opt ), description );

        option.setLongOpt( longOpt );
        option.setArgs( numberOfArgs );

        return option;
    }

    public static void checkIfValueExist( final Options options, final Option option )
    {
        final Set<String>   optionStrings     = new HashSet<>();
        final Collection<?> optionsCollection = options.getOptions();

        for( final Object entry : optionsCollection ) {
            if( entry instanceof Option ) {
                final Option cOption = (Option)entry;

                checkAndAdd( optionStrings, cOption );
            }
        }

        check( optionStrings, option );
    }

    private static void checkAndAdd( final Set<String> options, final Option option )
    {
        final String cOpt = option.getOpt();

        checkAndAdd( options, cOpt );

        final String cLongOpt = option.getLongOpt();

        if( cLongOpt != null ) {
            checkAndAdd( options, cLongOpt );
        }
    }

    private static void checkAndAdd( final Set<String> options, final String option )
    {
        if( options.contains( option ) ) {
            throw new OptionDuplicateFoundException( option );
        }

        options.add( option );
    }

    private static void check( final Set<String> optionStrings, final Option option )
    {
        final String opt = option.getOpt();

        if( optionStrings.contains( opt ) ) {
            throw new OptionTryToAddExistingOptException( opt );
        }

        final String longOpt = option.getLongOpt();

        if( (longOpt != null) && optionStrings.contains( longOpt ) ) {
            throw new OptionTryToAddExistingOptException( longOpt );
        }
    }

    /**
     * Build CLI option (with check for duplicate)
     *
     * @param options currents options
     * @param option option to add
     * @throws OptionHelperRuntimeException if an option is define twice
     */
    public static void addOption( final Options options, final Option option )
    {
        checkIfValueExist( options, option );

        options.addOption( option );
    }
}
