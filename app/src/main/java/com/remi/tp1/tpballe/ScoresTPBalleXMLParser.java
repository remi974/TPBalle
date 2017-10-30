package com.remi.tp1.tpballe;
/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

/**
 * Created by Rémi on 23/10/2017.
 */


import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * This class parses XML feeds from stackoverflow.com.
 * Given an InputStream representation of a feed, it returns a List of entries,
 * where each list element represents a single entry (post) in the XML feed.
 */
public class ScoresTPBalleXMLParser {

    private static final String ns = null;

    private List<Score> scores = null;

    public List<Score> parse(InputStream in) throws XmlPullParserException, IOException {
        scores = new ArrayList<Score>();
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List<Score> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {

        parser.require(XmlPullParser.START_TAG, ns, "scores");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the score tag
            if (name.equals("entry")) {
                scores.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }
        return scores;
    }

    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them
    // off
    // to their respective &quot;read&quot; methods for processing. Otherwise, skips the tag.
    private Score readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "entry");

        String joueur = null;
        String score = null;
        String[] data = new String[3];
        String date = null;
//        Date date = null;
//        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.FRANCE);
//
//        Date today;
//        String output;
//        SimpleDateFormat formatter;
//        String pattern = "dd/MM/YYYY hh:mm aaa";
//        formatter = new SimpleDateFormat(pattern, Locale.FRANCE);
//        today = new Date();
//        output = formatter.format(today);
//        Log.i("Date" , pattern + " " + output);


        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("joueur")) {
                joueur = readSingleData(parser,"joueur");
            } else if (name.equals("score")) {
                score = readSingleData(parser,"score");
            } else if (name.equals("date")) {
                date = readSingleData(parser,"date");
            } else if (name.equals("gps")) {
                data = readGps(parser);
            } else {
                skip(parser);
            }
        }
        return new Score(joueur, score, date, data);
    }

    // Processes title tags in the feed.
    private String readSingleData(XmlPullParser parser, String tagname) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, tagname);
        String dataText = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, tagname);
        return dataText;
    }

    // Processes title tags in the feed.
    private String[] readGps(XmlPullParser parser) throws IOException, XmlPullParserException {

        parser.require(XmlPullParser.START_TAG, ns, "gps");

        String[] data = new String[3];
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("latitude")) {
                data[0] = readSingleData(parser,"latitude");
            } else if (name.equals("longitude")) {
                data[1] = readSingleData(parser,"longitude");
            } else if (name.equals("markerlabel")) {
                data[2] = readSingleData(parser,"markerlabel");
            } else {
                skip(parser);
            }
        }

        return data;
    }


    /*// Processes link tags in the feed.
    private String readPosition(XmlPullParser parser) throws IOException, XmlPullParserException {
        String link = "";
        parser.require(XmlPullParser.START_TAG, ns, "");
        String tag = parser.getName();
        String relType = parser.getAttributeValue(null, "rel");
        if (tag.equals("link")) {
            if (relType.equals("alternate")) {
                link = parser.getAttributeValue(null, "href");
                parser.nextTag();
            }
        }
        parser.require(XmlPullParser.END_TAG, ns, "link");
        return link;
    }*/

    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    // Skips tags the parser isn't interested in. Uses depth to handle nested tags. i.e.,
    // if the next tag after a START_TAG isn't a matching END_TAG, it keeps going until it
    // finds the matching END_TAG (as indicated by the value of "depth" being 0).
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
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

    public boolean isEmpty() {

        return this.scores.isEmpty();
    }

    public List<Score> getScores() {

        return this.scores;
    }

}
