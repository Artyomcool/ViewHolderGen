package org.artyomcool.viewholdergen;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

public class XmlParser {

    public Model parse(InputStream xmlStream, String packageName, String layoutName) throws IOException {
        if (xmlStream == null) {
            throw new NullPointerException("xmlStream is null");
        }
        if (packageName == null) {
            throw new NullPointerException("packageName is null");
        }
        if (layoutName == null) {
            throw new NullPointerException("layoutName is null");
        }
        String className = ViewDescriptor.fieldNameFromId(layoutName, false);
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            try {
                SAXParser parser = factory.newSAXParser();
                ModelSaxHandler handler = new ModelSaxHandler();
                parser.parse(xmlStream, handler);
                return new Model(handler.getIdToClass(), packageName, className);
            } catch (ParserConfigurationException e) {
                //TODO throw new ParseException(e);
            } catch (SAXException e) {
                //TODO throw new ParseException(e);
            }
            return new Model(Collections.<String, String>emptyMap(), packageName, className);
        } finally {
            try {
                xmlStream.close();
            } catch (IOException ignored) {
            }
        }
    }
}
