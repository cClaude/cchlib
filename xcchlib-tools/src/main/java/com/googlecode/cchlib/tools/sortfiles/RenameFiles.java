package com.googlecode.cchlib.tools.sortfiles;

import java.io.File;
import java.io.IOException;
import com.googlecode.cchlib.io.filefilter.FileFileFilter;
import java.text.Normalizer;

public class RenameFiles
{
    public static void main(String[] args) throws IOException
    {
        final File baseInputPath = new File( "E:/Jerome/P1/D!F1");

        main( baseInputPath );
    }

    public static void main( final File baseInputPath ) throws IOException
    {
        File[] files = baseInputPath.listFiles( new FileFileFilter() );

        if( files != null && files.length > 0 ) {
            for( File file : files ) {
                final String name    = file.getName();
                final String newName = toASCIIName( name );
 
                if( ! newName.equals( name ) ) {
                    System.out.println( "Rename " + name + " TO " + newName );

                    File    newFile = new File( file.getParentFile(), newName );
                    boolean res     = file.renameTo( newFile );
                    
                    if( !res ) {
                        System.err.println( "Fail to rename [" + name + "]" );
                    }
                    }
                }
            }
    }
    private static String toASCIIName( final String source )
    {
        final String str = Normalizer.normalize( source, Normalizer.Form.NFKD ).replaceAll("\\p{InCombiningDiacriticalMarks}+","");
        final StringBuilder sb = new StringBuilder();

        for( int i = 0; i<str.length(); i++ ) {
            char c = str.charAt( i );

            if( c > 255 ) {
                switch( c ) {
                    case 'С' : sb.append( 'C' ); break;
                    case 'г' : sb.append( 'r' ); break;
                    case 'е' : sb.append( 'e' ); break;
                    case 'и' : sb.append( 'n' ); break;
                    case 'к' : sb.append( 'k' ); break;
                    case 'м' : sb.append( 'm' ); break;
                    case 'п' : sb.append( 'n' ); break;
                    case 'р' : sb.append( 'p' ); break;
                    case 'с' : sb.append( 'c' ); break;
                    case 'т' : sb.append( 'T' ); break;
                    case 'у' : sb.append( 'y' ); break;
                    case 'ф' : sb.append( 'o' ); break;
                    case 'ы' : sb.append( "bl" ); break;
                    case 'я' : sb.append( 'r' ); break;
                    case 'Д' : sb.append( 'A' ); break;
                    case 'А' : sb.append( 'A' ); break;
                    case 'в' : sb.append( 'B' ); break;
                    case 'ь' : sb.append( 'b' ); break;
                    case 'о' : sb.append( 'o' ); break;
                    case 'х' : sb.append( 'x' ); break;
                    case 'н' : sb.append( 'H' ); break;
                    case 'Б' : sb.append( '6' ); break;
                    case 'б' : sb.append( '6' ); break;
                    case 'а' : sb.append( 'a' ); break;
                    case 'л' : sb.append( 'p' ); break;
                    case 'ц' : sb.append( 'u' ); break;
                    case 'ъ' : sb.append( 'b' ); break;
                    case 'К' : sb.append( 'K' ); break;
                    case 'д' : sb.append( 'A' ); break;
                    case 'П' : sb.append( 'N' ); break;
                    case 'ч' : sb.append( '4' ); break;
                    case 'Р' : sb.append( 'P' ); break;
                    case 'Т' : sb.append( 'T' ); break;
                    case 'И' : sb.append( 'N' ); break;
                    case 'ᅩ' : sb.append( 'T' ); break;
                    case 'ᄏ' : sb.append( 'F' ); break;
                    case 'ᅵ' : sb.append( 'l' ); break;
                    case 'ᆼ' : sb.append( 'O' ); break;
                    case 'М' : sb.append( 'M' ); break;
                    case 'Я' : sb.append( 'R' ); break;
                    case '♥' : sb.append( "c3" ); break;
                    case 'Ш' : sb.append( 'W' ); break;
                    case 'Ч' : sb.append( '4' ); break;
                    case 'Ц' : sb.append( 'U' ); break;
                    case 'Г' : sb.append( 'F' ); break;
                    case 'ж' : sb.append( 'x' ); break;
                    case 'Е' : sb.append( 'E' ); break;
                    case 'Ф' : sb.append( '0' ); break;
                    case 'Н' : sb.append( 'H' ); break;
                    case 'ю' : sb.append( "HD" ); break;
                    case 'Л' : sb.append( 'N' ); break;
                    case 'З' : sb.append( '3' ); break;
                    case 'Ж' : sb.append( 'x' ); break;
                    case 'ш' : sb.append( 'W' ); break;
                    case 'В' : sb.append( 'B' ); break;
                    case 'О' : sb.append( 'O' ); break;
                    case 'У' : sb.append( 'y' ); break;
                    case 'Х' : sb.append( 'X' ); break;
                    case 'Э' : sb.append( '3' ); break;
                    case 'Ю' : sb.append( "HD" ); break;
                    case 'Ы' : sb.append( "BL" ); break;
                    case 'з' : sb.append( '3' ); break;
                    case 'Ь' : sb.append( 'b' ); break;
                    case 'э' : sb.append( '3' ); break;
                    case 'щ' : sb.append( 'W' ); break;
                    case 'ᄂ' : sb.append( 'L' ); break;
                    case 'ᄋ' : sb.append( 'O' ); break;
                    case 'ᄒ' : sb.append( 'f' ); break;
                    case 'ᅦ' : sb.append( 'f' ); break;
                    case 'і' : sb.append( 'i' ); break;
                    case 'І' : sb.append( 'I' ); break;
                    case 'ᅥ' : sb.append( 't' ); break;
                    case 'ᄃ' : sb.append( 'C' ); break;
                    case 'ᅳ' : sb.append( '-' ); break;
                    case 'ᄉ' : sb.append( 'v' ); break;
                    case 'ᆫ' : sb.append( 'L' ); break;
                    case 'ᄇ' : sb.append( 'H' ); break;
                    case '？' : sb.append( '_' ); break;
                    default : 
                        sb.append( c );
                        System.err.println( "ERROR: " + source + " >> " + c );
                        break;
                    }
                }
            else if( c == '?' || c == ':' || c == '*' ) {
                sb.append( '_' );
                }
            else {
                sb.append( c );
                }
            }
        return sb.toString();
    }

    /*
    private static String decompose(String s)
    {
        final String str = Normalizer.normalize( s, Normalizer.Form.NFKD ).replaceAll("\\p{InCombiningDiacriticalMarks}+","");

        String[] split = split( str );

String   join  = join( split );
assert join.equals( str );
System.err.println( ":" + join.equals( str ) + ":" + str.length() + " - " + str );

        String[] ascii = new String[ split.length ];

        for( int i=0; i<split.length; i++ ) {
            System.err.println( "  >> " + split[ i ].length() + " - " + split[ i ] );
            ascii[ i ] = IDN.toASCII( split[ i ] );
            }

        return  join( ascii );
    }


    private static String join( String[] str )
    {
        StringBuilder sb = new StringBuilder();

        for( String s : str ) {
            sb.append( s );
            }

        return sb.toString();
    }

    private final static int MAX = 63;
    private static String[] split( String s )
    {
        int      size = s.length() / MAX;
        String[] str  = new String[ size + 1 ];

        System.err.println( ": size=" + size );

        for( int i=0; i<str.length; i++ ) {
            int end = (i+1) * MAX;

            if( end <= s.length() ) {
                str[ i ] = s.substring( i * MAX, end  );
                }
            else {
                str[ i ] = s.substring( i * MAX );
                }
            }

        return str;
    }
    */
}
