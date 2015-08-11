package com.lcsmobileapps.rssteam.com.lcsmobileapps.rssteam.downloader;

import android.os.AsyncTask;
import android.util.Log;

import com.lcsmobileapps.rssteam.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by leandro.silverio on 11/08/2015.
 */
public class FeedDownloader extends AsyncTask<Void, Void, String> {

    private WeakReference<MainActivity> parent;
    private HttpURLConnection mHttpUrl;

    public FeedDownloader(MainActivity parent) {
        this.parent = new WeakReference<MainActivity>(parent);
    }
    @Override
    protected String doInBackground(Void... voids) {
        String result = null;
        BufferedReader in = null;
        try {

            URL url = new URL("http://esporte.uol.com.br/futebol/clubes/palmeiras.xml");
            mHttpUrl = (HttpURLConnection) url.openConnection();
            in = new BufferedReader(new InputStreamReader(
                    mHttpUrl.getInputStream()));

            StringBuffer sb = new StringBuffer("");
            String line = "";
            while ((line = in.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();

        } catch (MalformedURLException e) {
            Log.e("Uhlala", "MalformedURLException");
        } catch (IOException e) {
            Log.e("Uhlala", "IOException");
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                Log.e("Uhlala", "IOException");
            }
            mHttpUrl.disconnect();
        }
       return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        parent.get().postXml(s);
    }
}
