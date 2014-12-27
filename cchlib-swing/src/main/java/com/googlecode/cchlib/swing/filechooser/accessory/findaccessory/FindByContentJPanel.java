package com.googlecode.cchlib.swing.filechooser.accessory.findaccessory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import org.apache.log4j.Logger;

//TODO: Optimizations

/**
 * Implements user interface and generates FindFilter for selecting
 * files by content.
 * <P>
 * <b>WARNING:</B> The FindFilter inner class for this object does
 * not implement an efficient string search algorithm. Efficiency was
 * traded for code simplicity.
 */
class FindByContentJPanel extends JPanel implements FindFilterFactory
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( FindByContentJPanel.class );

    /**
     * Find for the first occurrence of the text in this field.
     */
    protected JTextField    contentField = null;
    protected JCheckBox     ignoreCaseCheck = null;


    /**
        Constructs a user interface and a FindFilterFactory for searching
        files containing specified text.
    */
    FindByContentJPanel()
    {
        super();

        setLayout(new BorderLayout());

        final JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));

        // Name
        final JLabel l = new JLabel("File contains...",SwingConstants.LEFT);
        l.setForeground(Color.black);
        l.setFont(new Font("Helvetica",Font.PLAIN,10));
        p.add(l);

        this.contentField = new JTextField();
        this.contentField.setForeground(Color.black);
        this.contentField.setFont(new Font("Helvetica",Font.PLAIN,10));
        p.add(this.contentField);

        // ignore case
        this.ignoreCaseCheck = new JCheckBox("ignore case",true);
        this.ignoreCaseCheck.setForeground(Color.black);
        this.ignoreCaseCheck.setFont(new Font("Helvetica",Font.PLAIN,9));
        p.add(this.ignoreCaseCheck);

        add(p,BorderLayout.NORTH);
    }

    @Override
    public FindFilter createFindFilter ()
    {
        return new ContentFilter(this.contentField.getText(),
                                    this.ignoreCaseCheck.isSelected());
    }

    /**
        Implements a simple content filter.
    */
    static class ContentFilter implements FindFilter
    {
        protected String    content = null;
        protected boolean   ignoreCase = true;

        ContentFilter (final String s, final boolean ignore)
        {
            this.content = s;
            this.ignoreCase = ignore;
        }

        @Override
        public boolean accept (final File f, final FindProgressCallback callback)
        {
            if (f == null) {
                return false;
                }
            if (f.isDirectory()) {
                return false;
                }
            if ((this.content == null) || (this.content.length() == 0)) {
                return true;
                }

            boolean             result  = false;
            BufferedInputStream in      = null;
            LocatorStream       locator = null;

            try {
                final long fileLength = f.length();
                in = new BufferedInputStream(new FileInputStream(f));
                byte[] contentBytes = null;
                if( this.ignoreCase ) {
                    contentBytes = this.content.toLowerCase().getBytes();
                    }
                else {
                    contentBytes = this.content.getBytes();
                    }

                locator = new LocatorStream(contentBytes);
                long counter = 0;
                int callbackCounter = 20; // Only call back every 20 bytes
                int c = -1;

                while((c = in.read()) != -1) {
                    counter++;
                    int matchChar = c;
                    if (this.ignoreCase) {
                        matchChar = Character.toLowerCase((char)c);
                        }
                    locator.write(matchChar);

                    // This search could be time consuming, especially since
                    // this algorithm is not exactly the most efficient.
                    // Report progress to search monitor and abort
                    // if method returns false.
                    if (callback != null) {
                        if (--callbackCounter <= 0) {
                            if (!callback.reportProgress(this, f,counter,fileLength)) {
                                return false;
                                }
                            callbackCounter = 20;
                            }
                        }
                    }
                }
            catch( final LocatedException e ) { // $codepro.audit.disable logExceptions
                result = true;
                }
            catch (final Throwable e) {
                LOGGER.warn( "ContentFilter error", e );
                }
            finally {
                try { if (locator != null) { locator.close(); } } catch (final IOException e){} // $codepro.audit.disable emptyCatchClause, logExceptions
                try { if (in != null) { in.close(); } } catch (final IOException e){} // $codepro.audit.disable emptyCatchClause, logExceptions
                }

            return result;
        }

        /**
            Thrown when a LocatorStream object finds a byte array.
        */
        class LocatedException extends IOException
        {
            private static final long serialVersionUID = 1L;

            public LocatedException(final String msg)
            {
                super(msg);
            }

            public LocatedException(final long location)
            {
                super(String.valueOf(location));
            }
        }

        /**
         * Locate an array of bytes on the output stream. Throws
         * a LocatedStream exception for every occurrence of the byte
         * array.
         */
        class LocatorStream extends OutputStream
        {
            protected byte[]                locate = null;
            protected Vector<MatchStream>   matchMakers = new Vector<MatchStream>();
            protected long                  mark = 0;

            LocatorStream (final byte[] b)
            {
                this.locate = b;
            }

            @Override
            public void write (final int b) throws IOException
            {
                if( this.locate == null ) {
                    throw new IOException("NULL locator array");
                    }
                if( this.locate.length == 0 ) {
                    throw new IOException("Empty locator array");
                    }

                long foundAt = -1;

                for( int i=this.matchMakers.size()-1; i>=0; i-- ) {
                    final MatchStream m = this.matchMakers.elementAt(i);

                    try {
                        m.write(b);
                        }
                    catch (final MatchMadeException e) { // $codepro.audit.disable logExceptions
                        foundAt = m.getMark();
                        this.matchMakers.removeElementAt(i);
                        }
                    catch (final IOException e) { // $codepro.audit.disable logExceptions
                        // Match not made. Remove current matchMaker stream.
                        this.matchMakers.removeElementAt(i);
                        }
                    }

                if( b == this.locate[0] ) {
                    final MatchStream m = new MatchStream(this.locate,this.mark);
                    m.write(b); // This will be accepted
                    this.matchMakers.addElement(m);
                    }
                this.mark++;

                if (foundAt >= 0) {
                    throw new LocatedException(foundAt);
                    }
            }

            /**
                Thrown when the bytes written match the byte pattern.
            */
            class MatchMadeException extends IOException
            {
                private static final long serialVersionUID = 1L;

                public MatchMadeException (final String msg)
                {
                    super(msg);
                }

                public MatchMadeException (final long mark)
                {
                    super(String.valueOf(mark));
                }
            }

            /**
             * Accept "output" as long as it matches a specified array of bytes.
             * Throw a MatchMadeException when the bytes written equals the
             * match array.
             * Throw an IOException when a byte does not match. Ignore
             * everything after a match is made.
             */
            class MatchStream extends OutputStream
            {
                protected long      mark = -1;
                protected int       pos = 0;
                protected byte[]    match = null;
                protected boolean   matchMade = false;

                MatchStream (final byte[] b, final long m)
                {
                    this.mark = m;
                    this.match = b;
                }

                @Override
                public void write (final int b) throws IOException
                {
                    if (this.matchMade) {
                        return;
                    }
                    if (this.match == null) {
                        throw new IOException("NULL match array");
                    }

                    if (this.match.length == 0) {
                        throw new IOException("Empty match array");
                    }

                    if (this.pos >= this.match.length) {
                        throw new IOException("No match");
                    }

                    if (b != this.match[this.pos]) {
                        throw new IOException("No match");
                    }

                    this.pos++;
                    if (this.pos >= this.match.length)
                    {
                        this.matchMade = true;
                        throw new MatchMadeException(this.mark);
                    }
                }

                public long getMark ()
                {
                    return this.mark;
                }
            }
        }

    }

}
