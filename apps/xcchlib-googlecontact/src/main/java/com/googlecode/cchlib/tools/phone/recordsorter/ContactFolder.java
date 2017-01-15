package com.googlecode.cchlib.tools.phone.recordsorter;

import java.util.HashMap;
import java.util.Map;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Contact;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.util.DefaultContact;

public class ContactFolder {

    public static final Contact HIDDEN_CONTACT = newHiddenContact();
    private final Map<Contact,String> contacts = new HashMap<>();

    public String getFolderName( final Contact contact )
    {
        String folderName = this.contacts.get( contact );

        if( folderName == null ) {
            folderName = normalizeName( contact.getName() );

            this.contacts.put( contact, folderName );
        }
        return folderName;
    }

    private String normalizeName( final String name )
    {
        final int           size = name.length();
        final StringBuilder sb   = new StringBuilder(size * 2);

        for (int i = 0; i < size; i++) {
            final char c = name.charAt(i);

            switch( c ) {
                case 'ê' :
                case 'è' :
                case 'é' :
                case 0x20 : // Space
                    sb.append(c);
                    break;

                default:
                    if ( isValid( c ) ) {
                        sb.append(c);
                    } else {
                        // Encode the character using hex notation
                        sb.append('#');
                        sb.append( toHexFromInt(c));
                    }
                   break;
            }
        }

        return sb.toString();
    }

    private boolean isValid( final char c )
    {
        return ((c >= 'a') && (c <= 'z'))
            || ((c >= 'A') && (c <= 'Z'))
            || ((c >= '0') && (c <= '9'))
            || (c == '_') || (c == '-') || (c == '.')
            || (c == '(') || (c == ')')
            || (c=='#');
    }

    @SuppressWarnings("squid:S3346") // assert usage
    private String toHexFromInt( final int i )
    {
        final String hex = Integer.toHexString( i );

        if( hex.length() == 1 ) {
            return '0' + hex.toUpperCase();
        }

        assert hex.length() == 2;

        return hex.toUpperCase();
    }

    private static Contact newHiddenContact()
    {
        final DefaultContact hiddenContact = new DefaultContact();

        hiddenContact.setName( "_hidden_NUMEBER" );
        hiddenContact.setBackup( true );

        return hiddenContact;
    }

}
