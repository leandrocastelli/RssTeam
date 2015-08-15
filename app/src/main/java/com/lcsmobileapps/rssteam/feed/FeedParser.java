package com.lcsmobileapps.rssteam.feed;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leandro.silverio on 11/08/2015.
 */
public class FeedParser {


    public static List<Feed> parseXml(XmlPullParser parser) throws IOException, XmlPullParserException {
        List<Feed> entries = new ArrayList<Feed>();
        parser.require(XmlPullParser.START_TAG, null, "channel");
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if (name.equals("item")) {
               entries.add(readFeed(parser));
            } else {
             skip(parser);
            }
        }



        return entries;
    }

    private static Feed readFeed (XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG,null,"item");
        String title = null;
        String link = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if (name.equals("title")) {
                if (parser.next() == XmlPullParser.TEXT) {
                    title = parser.getText();
                    parser.nextTag();
                }

            }
            if (name.equals("link")) {
                if (parser.next() == XmlPullParser.TEXT) {
                    link = parser.getText();
                    parser.nextTag();
                }
            }
        }


        return new Feed(title.trim(),link.trim());
    }
    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
