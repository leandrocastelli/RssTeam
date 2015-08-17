package com.lcsmobileapps.rssteam.feed;

import android.os.AsyncTask;
import android.util.Xml;

import com.lcsmobileapps.rssteam.MainActivity;
import com.lcsmobileapps.rssteam.util.Utils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by leandro.silverio on 11/08/2015.
 */
public class FeedDownloader extends AsyncTask<String, Void, String> {

    private WeakReference<MainActivity> parent;
    private HttpURLConnection mHttpUrl;

    public FeedDownloader(MainActivity parent) {
        this.parent = new WeakReference<MainActivity>(parent);
    }
    @Override
    protected String doInBackground(String... team) {
        String result = null;
        BufferedReader in = null;

        XmlPullParser parser = Xml.newPullParser();
        String link = Utils.getTeamLink(team[0]);
        try {
            URL url = new URL(link);
            mHttpUrl = (HttpURLConnection) url.openConnection();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(mHttpUrl.getInputStream(), null);
            //Enter into Channel TAG
            parser.nextTag();
            parser.nextTag();
            FeedParser.parseXml(parser);

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mHttpUrl.disconnect();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
      //  parent.get().postXml(s);
    }
}
