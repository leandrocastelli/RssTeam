package com.lcsmobileapps.rssteam.feed;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Xml;
import android.widget.Toast;

import com.lcsmobileapps.rssteam.MainActivity;
import com.lcsmobileapps.rssteam.R;
import com.lcsmobileapps.rssteam.provider.ContentController;
import com.lcsmobileapps.rssteam.ui.FeedFragment;
import com.lcsmobileapps.rssteam.util.Utils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by leandro.silverio on 11/08/2015.
 */
public class FeedDownloader extends AsyncTask<String, Void, Integer> {

    private WeakReference<Activity> parent;
    private HttpURLConnection mHttpUrl;
    private ProgressDialog dialog;
    public FeedDownloader(Activity parent) {
        this.parent = new WeakReference<Activity>(parent);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(parent.get());
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage(parent.get().getString(R.string.checking_update));
        dialog.show();
    }

    @Override
    protected Integer doInBackground(String... team) {
        List<Feed> feeds = null;
        int rowsInserted = 0;
        BufferedReader in = null;
        ContentController controllerInstance = ContentController.getInstance();
        XmlPullParser parser = Xml.newPullParser();
        String link = controllerInstance.getTeamLink(team[0],parent.get());
        try {
            URL url = new URL(link);
            mHttpUrl = (HttpURLConnection) url.openConnection();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(mHttpUrl.getInputStream(), null);
            //Enter into Channel TAG
            parser.nextTag();
            parser.nextTag();
            feeds = FeedParser.parseXml(parser);
            rowsInserted = controllerInstance.insertNews(feeds, team[0], parent.get());

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mHttpUrl.disconnect();

        }
        return rowsInserted;
    }

    @Override
    protected void onPostExecute(Integer rowsInsert) {
        super.onPostExecute(rowsInsert);
        if (dialog.isShowing()) {
            dialog.cancel();
        }

        if (rowsInsert > 0) {
            Toast.makeText(parent.get(), "Novas noticias: " + rowsInsert, Toast.LENGTH_SHORT).show();
            FeedFragment.MyHandler myHandler = new FeedFragment.MyHandler();
            myHandler.sendEmptyMessage(FeedFragment.WHAT_REFRESH_CONTENT);
        }
    }
}
