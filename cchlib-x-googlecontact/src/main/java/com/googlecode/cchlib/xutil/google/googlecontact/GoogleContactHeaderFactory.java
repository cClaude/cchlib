package com.googlecode.cchlib.xutil.google.googlecontact;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.xutil.google.googlecontact.analyser.AnalyserCustomTypeMethodContener;
import com.googlecode.cchlib.xutil.google.googlecontact.analyser.AnalyserMethodContener;
import com.googlecode.cchlib.xutil.google.googlecontact.analyser.GoogleContactAnalyser;
import com.googlecode.cchlib.xutil.google.googlecontact.analyser.GoogleContactCSVException;
import com.googlecode.cchlib.xutil.google.googlecontact.analyser.TypeInfo;
import com.googlecode.cchlib.xutil.google.googlecontact.types.GoogleContact;

public class GoogleContactHeaderFactory {

    private static final class HeaderMethodContenerImpl implements HeaderMethodContener {

        private Method method;

        public HeaderMethodContenerImpl( final AnalyserMethodContener methodContener )
        {
            this.method = methodContener.getMethod();

            assert this.method != null;
        }

        @Override
        public Method getMethod()
        {
            return method;
        }

        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder();
            builder.append( "HeaderMethodContenerImpl [method=" );
            builder.append( method );
            builder.append( ']' );
            return builder.toString();
        }
    }

    private static final class HeaderCustomTypeMethodContenerImpl implements HeaderCustomTypeMethodContener {

        private final String                            header;
        private final AnalyserCustomTypeMethodContener  customTypeMethodContener;
        private final String                            prefix;
        private final int                               position;
        private final String                            suffix;
        private final Map<Integer,HeaderMethodContener> innerMethodContener = new HashMap<>();
        private final TypeInfo typeInfo;

        public HeaderCustomTypeMethodContenerImpl(
            final AnalyserCustomTypeMethodContener customTypeMethodContener,
            final String                           header
            ) throws GoogleContactFactoryException
        {
            this.header                   = header;
            this.customTypeMethodContener = customTypeMethodContener;

            final Matcher matcher = HEADER_PATTERN.matcher( header );

            if( matcher.matches() ) {
                this.prefix   = matcher.group( PREFIX_MATCHER_INDEX );
                this.position = Integer.parseInt( matcher.group( POSITION_MATCHER_INDEX ) );
                this.suffix   = matcher.group( SUFFIX_MATCHER_INDEX );

                this.typeInfo = customTypeMethodContener.getTypeInfo();
            } else {
                final String message = "Can not parse [" + header + "] - Did not match";
                LOGGER.error( message );

                throw new GoogleContactFactoryException( message );
            }

       }

        @Override
        public String getHeader()
        {
            return header;
        }

        @Override
        public String getPrefix()
        {
            return prefix;
        }

        @Override
        public int getPosition()
        {
            return position;
        }

        @Override
        public String getSuffix()
        {
            return suffix;
        }

        @Override
        public Method getMethod()
        {
            return customTypeMethodContener.getMethod();
        }

        @Override
        public void put( final int index, final HeaderMethodContener methodContener )
        {
            innerMethodContener.put( Integer.valueOf( index ), methodContener );
        }
        @Override
        public HeaderMethodContener get( final int index )
        {
            return innerMethodContener.get( Integer.valueOf( index ) );
        }
        @Override
        public int size()
        {
            return innerMethodContener.size();
        }
        @Override
        public Iterator<HeaderMethodContener> iterator()
        {
            return innerMethodContener.values().iterator();
        }

        @Override
        public TypeInfo getTypeInfo()
        {
            return typeInfo;
        }
    }

    private static final int SUFFIX_MATCHER_INDEX = 3;
    private static final int POSITION_MATCHER_INDEX = 2;
    private static final int PREFIX_MATCHER_INDEX = 1;

    private static final Logger LOGGER = Logger.getLogger( GoogleContactHeaderFactory.class );
    private static final Pattern HEADER_PATTERN = Pattern.compile( "([a-zA-Z -]+) ([0-9]+) - ([a-zA-Z \\-]+)" );
    private static final HeaderMethodContener NULL_CONTENER = new HeaderMethodContener() {
        @Override
        public Method getMethod()
        {
            return null;
        }};

    private final GoogleContactAnalyser googleContactAnalyser = new GoogleContactAnalyser();
    private final Map<Integer,HeaderMethodContener> indexMethodConteners = new HashMap<>();

    private GoogleContactHeaderFactory( final String[] headers ) throws GoogleContactFactoryException
    {
        for( int index = 0; index<headers.length; ) {
            final String header = headers[ index ];
            final AnalyserMethodContener methodContener = findAnyAnalyserMethodContener( googleContactAnalyser.getTypeInfo(), header );

            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( "findMethodContener[" + header + "] -> " + methodContener.getMethod() );
            }

            assert methodContener != null;
            assert methodContener.getMethod() != null;
            assert methodContener.getMethod().getParameterTypes().length == 1;

            if( methodContener instanceof AnalyserCustomTypeMethodContener ) {
                assert !methodContener.getMethod().getParameterTypes()[ 0 ].equals( String.class );

                final HeaderCustomTypeMethodContener headerCustomTypeMethodContener = newHeaderCustomTypeMethodContener( header, methodContener );

                final int parameterCount = addCustomType( headers, index, headerCustomTypeMethodContener );

                index += parameterCount;
           } else if( methodContener.getMethod().getDeclaringClass().isAssignableFrom( GoogleContact.class ) ) {
//                methodContener.addIndex( index );

                //mainMethodConteners.add( methodContener );
                assert indexMethodConteners.get( Integer.valueOf( index ) ) == null;
                indexMethodConteners.put( Integer.valueOf( index ), newHeaderMethodContener( methodContener ) );
                index++;
            } else {
               throw new IllegalStateException( "can not handle header index = " + index );
           }
        }

        assert indexMethodConteners.size() == headers.length;
    }

    private int addCustomType(
        final String[]                       headers,
        final int                            index,
        final HeaderCustomTypeMethodContener customTypeMethodContener
        )
    {
        final TypeInfo typeInfo       = customTypeMethodContener.getTypeInfo();
        final int      parameterCount = typeInfo.getParameterCount();

        assert indexMethodConteners.get( Integer.valueOf( index ) ) == null;
        indexMethodConteners.put( Integer.valueOf( index ), customTypeMethodContener );

        for( int i = 0; i<parameterCount; i++ ) {
            final Matcher matcher = HEADER_PATTERN.matcher( headers [ index + i ] );

            if( ! matcher.matches() ) {
                throw new IllegalArgumentException( headers [ index + i ] ); // FIXME
            }

            final AnalyserMethodContener mc = findAnalyserMethodContener( typeInfo, matcher.group( SUFFIX_MATCHER_INDEX ) );

            assert mc != null : "Method for [" + matcher.group( SUFFIX_MATCHER_INDEX ) + "] not found ! Line : " + headers [ index + i ];

            customTypeMethodContener.put( i, newHeaderMethodContener( mc ) );

            if( i != 0 ) {
                assert indexMethodConteners.get( Integer.valueOf( index + i ) ) == null;
                indexMethodConteners.put( Integer.valueOf( index + i ), NULL_CONTENER );
            }
        }

        assert parameterCount > 0;
        return parameterCount;
    }

    private HeaderMethodContener newHeaderMethodContener( final AnalyserMethodContener methodContener )
    {
        assert !(methodContener instanceof AnalyserCustomTypeMethodContener);

        return new HeaderMethodContenerImpl( methodContener );
    }

    private HeaderCustomTypeMethodContener newHeaderCustomTypeMethodContener(
        final String                 header,
        final AnalyserMethodContener methodContener
        ) throws GoogleContactFactoryException
    {
        final AnalyserCustomTypeMethodContener customTypeMethodContener = (AnalyserCustomTypeMethodContener)methodContener;

        return new HeaderCustomTypeMethodContenerImpl( customTypeMethodContener, header );
    }

    private AnalyserMethodContener findAnyAnalyserMethodContener(
        final TypeInfo parentTypeInfo,
        final String   header
        )
    {
        final AnalyserMethodContener methodContener = findAnalyserMethodContener( parentTypeInfo, header );

        if( methodContener != null ) {
            return methodContener;
        }
        else {
            return findAnalyserCustomTypeMethodContener( parentTypeInfo, header );
        }
    }

    private AnalyserMethodContener findAnalyserMethodContener(
        final TypeInfo parentTypeInfo,
        final String   header
        )
    {
        return parentTypeInfo.getMethodForStrings().get( header );
    }

    private AnalyserCustomTypeMethodContener findAnalyserCustomTypeMethodContener(
        final TypeInfo parentTypeInfo,
        final String   header
        )
    {
        final Matcher matcher = HEADER_PATTERN.matcher( header );

        if( matcher.matches() ) {
            final String prefix = matcher.group( 1 );

            final AnalyserCustomTypeMethodContener customTypeMethodContener = parentTypeInfo.getMethodForCustomType().get( prefix );

            if( customTypeMethodContener == null ) {
                throw new GoogleContactCSVException(
                        "Can not handle header = [" + header
                        + "] : prefix [" + prefix
                        + "],number [" +  matcher.group( POSITION_MATCHER_INDEX )
                        + "],suffix [" +  matcher.group( SUFFIX_MATCHER_INDEX )
                        + "] unknown on " + parentTypeInfo
                        );
            }

            assert customTypeMethodContener.isSuffixValid( matcher.group( SUFFIX_MATCHER_INDEX ) );

            return customTypeMethodContener;
        } else {
            throw new GoogleContactCSVException( "Can not handle header = [" + header + "] : can not parse string" );
        }
    }

    public static GoogleContactHeader newGoogleContactHeader( final String[] headers ) throws GoogleContactFactoryException {
        final GoogleContactHeaderFactory factory = new GoogleContactHeaderFactory( headers );

        return new GoogleContactHeader() {
            private Map<Integer, HeaderMethodContener> unmodifiableMapIndexMethodConteners = Collections.unmodifiableMap( new HashMap<>( factory.indexMethodConteners ) );

            @Override
            public Map<Integer,HeaderMethodContener> getIndexMethodConteners()
            {
                   return unmodifiableMapIndexMethodConteners;
            }
        };
    }
}
