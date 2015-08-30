package com.lcsmobileapps.rssteam.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.util.Xml;

import com.lcsmobileapps.rssteam.MainActivity;
import com.lcsmobileapps.rssteam.feed.Feed;
import com.lcsmobileapps.rssteam.feed.FeedParser;
import com.lcsmobileapps.rssteam.feed.Team;
import com.lcsmobileapps.rssteam.provider.ContentController;
import com.lcsmobileapps.rssteam.util.ImageHelper;
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
    private Team team;
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String prefTeam = Utils.getPrefTeamName(this);
            team = ContentController.getInstance().getTeam(prefTeam, this);
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

        String link = controllerInstance.getTeamLink(team.name,this);
        try {
            URL url = new URL(link);
            mHttpUrl = (HttpURLConnection) url.openConnection();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(mHttpUrl.getInputStream(), null);
            //Enter into Channel TAG
            parser.nextTag();
            parser.nextTag();
            feeds = FeedParser.parseXml(parser);
            rowsInserted = controllerInstance.insertNews(feeds, team.name, this);

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mHttpUrl.disconnect();

        }

        if (rowsInserted > 0) {
            buildNotification();
        }
        stopSelf();
    }

    public void buildNotification () {
        NotificationManager notificationManager = (NotificationManager)this.getSystemService(NOTIFICATION_SERVICE);

        Bitmap bitmap = ImageHelper.getBitmap(team.flag, this);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(team.flag);
        builder.setLargeIcon(bitmap);
        builder.setContentText("Novas Noticias");
        builder.setContentTitle(team.name);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pi);
        builder.setAutoCancel(true);
        notificationManager.notify(123, builder.build());
    }
}
