package madscience.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public final class MadXML
{
    public static Document loadXMLFromString(String theXML_URL)
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        try
        {
            db = dbf.newDocumentBuilder();
            Document doc = db.parse(new URL(theXML_URL).openStream());
            return doc;
        }
        catch (ParserConfigurationException e)
        {
            return null;
        }
        catch (MalformedURLException e)
        {
            return null;
        }
        catch (SAXException e)
        {
            return null;
        }
        catch (IOException e)
        {
            return null;
        }
    }
}
