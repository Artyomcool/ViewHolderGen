package org.artyomcool.viewholdergen;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
*
*/
class ModelSaxHandler extends DefaultHandler {

    private static final String ANDROID_URI = "http://schemas.android.com/apk/res/android";

    private boolean mFinished;
    private Map<String, String> mIdToClass;

    @Override
    public void startDocument() throws SAXException {
        mFinished = false;
        mIdToClass = new LinkedHashMap<String, String>();
    }

    @Override
    public void endDocument() throws SAXException {
        mIdToClass = Collections.unmodifiableMap(mIdToClass);
        mFinished = true;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        String id = attributes.getValue(ANDROID_URI, "id");

        if (id != null) {
            id = afterSubstring(id, "id/");

            @SuppressWarnings("UnnecessaryLocalVariable")
            String className = localName;

            String oldName = mIdToClass.put(id, className);
            if (oldName != null) {
                throw new SAXException("Id " + id + " duplicates with values " + oldName + "/" + className);
            }
        }
    }

    Map<String, String> getIdToClass() {
        if (!mFinished) {
            throw new IllegalStateException("Attempt to get parsed value before parsing finished");
        }
        return mIdToClass;
    }

    private static String afterSubstring(String original, String substring) {
        int index = original.indexOf(substring);
        if (index == -1) {
            throw new IllegalArgumentException("Original string doesn't contain the substring");
        }
        return original.substring(index + substring.length());
    }
}
