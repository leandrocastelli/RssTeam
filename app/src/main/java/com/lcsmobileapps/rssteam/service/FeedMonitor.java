package com.lcsmobileapps.rssteam.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.lcsmobileapps.rssteam.feed.Feed;
import com.lcsmobileapps.rssteam.feed.FeedParser;
import com.lcsmobileapps.rssteam.feed.Team;
import com.lcsmobileapps.rssteam.provider.ContentController;
import com.lcsmobileapps.rssteam.util.Utils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class FeedMonitor extends IntentService implements BackgroundDownloader{

    public FeedMonitor() {
        super("FeedMonitor");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            downloadFeeds();
        }
    }


    @Override
    public void downloadFeeds() {

        List<Feed> feeds = null;
        int rowsInserted = 0;
        BufferedReader in = null;
        ContentController controllerInstance = ContentController.getInstance();
        HttpURLConnection mHttpUrl = null;
        XmlPullParser parser = Xml.newPullParser();
        String prefTeam = Utils.getPrefTeamName(this);
        Team currentTeam = ContentController.getInstance().getTeam(prefTeam, this);
        String link = controllerInstance.getTeamLink(currentTeam.name,this);
        try {
            URL url = new URL(link);
            mHttpUrl = (HttpURLConnection) url.openConnection();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(mHttpUrl.getInputStream(), null);
            //Enter into Channel TAG
            parser.nextTag();
            parser.nextTag();
            feeds = FeedParser.parseXml(parser);
            rowsInserted = controllerInstance.insertNews(feeds, currentTeam.name, this);

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mHttpUrl.disconnect();

        }
        for (Feed feed : feeds) {
            Log.i("Leandro","Feed "+ feed.getTitle());
        }
    }
}
