package cx.ath.choisnet.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

// Referenced classes of package cx.ath.choisnet.xml:
//            XMLParser

/**
 * @deprecated Class XMLParserDOM2 is deprecated
 */
@Deprecated
public class XMLParserDOM2
    implements cx.ath.choisnet.xml.XMLParser
{

    private final DocumentBuilder documentBuilder;
    private Document document;

    protected XMLParserDOM2(boolean validation, boolean ignoreWhitespace, boolean ignoreComments, boolean putCDATAIntoText, boolean createEntityRefs, org.xml.sax.ErrorHandler errorHandler)
        throws IOException, ParserConfigurationException, org.xml.sax.SAXException
    {
    //    0    0:aload_0
    //    1    1:invokespecial   #1   <Method void Object()>
        documentBuilder = cx.ath.choisnet.xml.XMLParserDOM2.createDocumentBuilder(validation, ignoreWhitespace, ignoreComments, putCDATAIntoText, createEntityRefs, errorHandler);
    //    2    4:aload_0
    //    3    5:iload_1
    //    4    6:iload_2
    //    5    7:iload_3
    //    6    8:iload           4
    //    7   10:iload           5
    //    8   12:aload           6
    //    9   14:invokestatic    #2   <Method javax.xml.parsers.DocumentBuilder cx.ath.choisnet.xml.XMLParserDOM2.createDocumentBuilder(boolean, boolean, boolean, boolean, boolean, org.xml.sax.ErrorHandler)>
    //   10   17:putfield        #3   <Field javax.xml.parsers.DocumentBuilder cx.ath.choisnet.xml.XMLParserDOM2.documentBuilder>
        document = null;
    //   11   20:aload_0
    //   12   21:aconst_null
    //   13   22:putfield        #4   <Field org.w3c.dom.Document cx.ath.choisnet.xml.XMLParserDOM2.document>
    //   14   25:return
    }

    @Override
    public Document getDocument()
    {
        return document;
    //    0    0:aload_0
    //    1    1:getfield        #4   <Field org.w3c.dom.Document cx.ath.choisnet.xml.XMLParserDOM2.document>
    //    2    4:areturn
    }

    protected void setDocument(Document document)
    {
        this.document = document;
    //    0    0:aload_0
    //    1    1:aload_1
    //    2    2:putfield        #4   <Field org.w3c.dom.Document cx.ath.choisnet.xml.XMLParserDOM2.document>
    //    3    5:return
    }

    public XMLParserDOM2(URL anURL, boolean validation, boolean ignoreWhitespace, boolean ignoreComments, boolean putCDATAIntoText, boolean createEntityRefs, ErrorHandler errorHandler)
        throws IOException, ParserConfigurationException, org.xml.sax.SAXException
    {
        this(validation, ignoreWhitespace, ignoreComments, putCDATAIntoText, createEntityRefs, errorHandler);
    //    0    0:aload_0
    //    1    1:iload_2
    //    2    2:iload_3
    //    3    3:iload           4
    //    4    5:iload           5
    //    5    7:iload           6
    //    6    9:aload           7
    //    7   11:invokespecial   #5   <Method void XMLParserDOM2(boolean, boolean, boolean, boolean, boolean, org.xml.sax.ErrorHandler)>
        document = documentBuilder.parse(anURL.openStream(), anURL.toString());
    //    8   14:aload_0
    //    9   15:aload_0
    //   10   16:getfield        #3   <Field javax.xml.parsers.DocumentBuilder cx.ath.choisnet.xml.XMLParserDOM2.documentBuilder>
    //   11   19:aload_1
    //   12   20:invokevirtual   #6   <Method java.io.InputStream java.net.URL.openStream()>
    //   13   23:aload_1
    //   14   24:invokevirtual   #7   <Method String java.net.URL.toString()>
    //   15   27:invokevirtual   #8   <Method org.w3c.dom.Document javax.xml.parsers.DocumentBuilder.parse(java.io.InputStream, String)>
    //   16   30:putfield        #4   <Field org.w3c.dom.Document cx.ath.choisnet.xml.XMLParserDOM2.document>
    //   17   33:return
    }

    public XMLParserDOM2(InputStream aStream, boolean validation, boolean ignoreWhitespace, boolean ignoreComments, boolean putCDATAIntoText, boolean createEntityRefs, ErrorHandler errorHandler)
        throws IOException, ParserConfigurationException, SAXException
    {
        this(validation, ignoreWhitespace, ignoreComments, putCDATAIntoText, createEntityRefs, errorHandler);
    //    0    0:aload_0
    //    1    1:iload_2
    //    2    2:iload_3
    //    3    3:iload           4
    //    4    5:iload           5
    //    5    7:iload           6
    //    6    9:aload           7
    //    7   11:invokespecial   #5   <Method void XMLParserDOM2(boolean, boolean, boolean, boolean, boolean, org.xml.sax.ErrorHandler)>
        document = documentBuilder.parse(aStream);
    //    8   14:aload_0
    //    9   15:aload_0
    //   10   16:getfield        #3   <Field javax.xml.parsers.DocumentBuilder cx.ath.choisnet.xml.XMLParserDOM2.documentBuilder>
    //   11   19:aload_1
    //   12   20:invokevirtual   #9   <Method org.w3c.dom.Document javax.xml.parsers.DocumentBuilder.parse(java.io.InputStream)>
    //   13   23:putfield        #4   <Field org.w3c.dom.Document cx.ath.choisnet.xml.XMLParserDOM2.document>
    //   14   26:return
    }

    private static DocumentBuilderFactory createDocumentBuilderFactory(
            boolean validation,
            boolean ignoreWhitespace,
            boolean ignoreComments,
            boolean putCDATAIntoText,
            boolean createEntityRefs
            )
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    //    0    0:invokestatic    #10  <Method javax.xml.parsers.DocumentBuilderFactory javax.xml.parsers.DocumentBuilderFactory.newInstance()>
    //    1    3:astore          5
        dbf.setValidating(validation);
    //    2    5:aload           5
    //    3    7:iload_0
    //    4    8:invokevirtual   #11  <Method void javax.xml.parsers.DocumentBuilderFactory.setValidating(boolean)>
        dbf.setIgnoringComments(ignoreComments);
    //    5   11:aload           5
    //    6   13:iload_2
    //    7   14:invokevirtual   #12  <Method void javax.xml.parsers.DocumentBuilderFactory.setIgnoringComments(boolean)>
        dbf.setIgnoringElementContentWhitespace(ignoreWhitespace);
    //    8   17:aload           5
    //    9   19:iload_1
    //   10   20:invokevirtual   #13  <Method void javax.xml.parsers.DocumentBuilderFactory.setIgnoringElementContentWhitespace(boolean)>
        dbf.setCoalescing(putCDATAIntoText);
    //   11   23:aload           5
    //   12   25:iload_3
    //   13   26:invokevirtual   #14  <Method void javax.xml.parsers.DocumentBuilderFactory.setCoalescing(boolean)>
        dbf.setExpandEntityReferences(!createEntityRefs);
    //   14   29:aload           5
    //   15   31:iload           4
    //   16   33:ifne            40
    //   17   36:iconst_1
    //   18   37:goto            41
    //   19   40:iconst_0
    //   20   41:invokevirtual   #15  <Method void javax.xml.parsers.DocumentBuilderFactory.setExpandEntityReferences(boolean)>
        return dbf;
    //   21   44:aload           5
    //   22   46:areturn
    }

    private static DocumentBuilder createDocumentBuilder(boolean validation, boolean ignoreWhitespace, boolean ignoreComments, boolean putCDATAIntoText, boolean createEntityRefs, org.xml.sax.ErrorHandler errorHandler)
        throws ParserConfigurationException
    {
        DocumentBuilderFactory dbf = XMLParserDOM2.createDocumentBuilderFactory(validation, ignoreWhitespace, ignoreComments, putCDATAIntoText, createEntityRefs);
    //    0    0:iload_0
    //    1    1:iload_1
    //    2    2:iload_2
    //    3    3:iload_3
    //    4    4:iload           4
    //    5    6:invokestatic    #16  <Method javax.xml.parsers.DocumentBuilderFactory cx.ath.choisnet.xml.XMLParserDOM2.createDocumentBuilderFactory(boolean, boolean, boolean, boolean, boolean)>
    //    6    9:astore          6
        DocumentBuilder db = null;
    //    7   11:aconst_null
    //    8   12:astore          7
        try
        {
            db = dbf.newDocumentBuilder();
    //    9   14:aload           6
    //   10   16:invokevirtual   #17  <Method javax.xml.parsers.DocumentBuilder javax.xml.parsers.DocumentBuilderFactory.newDocumentBuilder()>
    //   11   19:astore          7
        }
    //*  12   21:goto            29
        catch(ParserConfigurationException pce)
    //*  13   24:astore          8
        {
            throw pce;
    //   14   26:aload           8
    //   15   28:athrow
        }
        db.setErrorHandler(errorHandler);
    //   16   29:aload           7
    //   17   31:aload           5
    //   18   33:invokevirtual   #19  <Method void javax.xml.parsers.DocumentBuilder.setErrorHandler(org.xml.sax.ErrorHandler)>
        return db;
    //   19   36:aload           7
    //   20   38:areturn
    }
}
