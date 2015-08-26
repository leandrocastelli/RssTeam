package com.lcsmobileapps.rssteam;

import  android.support.v4.app.FragmentTransaction;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.LruCache;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.lcsmobileapps.rssteam.feed.FeedDownloader;
import com.lcsmobileapps.rssteam.ui.FeedFragment;
import com.lcsmobileapps.rssteam.util.ImageHelper;
import com.lcsmobileapps.rssteam.util.Utils;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       // Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

     //  setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        final int maxMemory = (int)(Runtime.getRuntime().maxMemory() /1024);

        int cacheSize = maxMemory / 10;

        ImageHelper.mMemoryCache = new LruCache<String, Bitmap>(cacheSize);
       // Utils.setPrefTeamName(this, "Palmeiras");

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.main_relative, FeedFragment.newInstance(), "");
        transaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void postXml(String content) {
        //TextView txtView = (TextView)findViewById(R.id.hello_world);
        //txtView.setText(content);
    }
}
