package com.lcsmobileapps.rssteam.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.lcsmobileapps.rssteam.feed.Feed;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leandro.silverio on 17/08/2015.
 */
public class ContentController {

    private static ContentController instance;
    public String getTeamLink(String teamName, Context ctx) {

        ContentResolver contentResolver = ctx.getContentResolver();
        Cursor result = contentResolver.query(NewsProvider.CONTENT_URI_TEAMS, new String[]{Contracts.TeamsContract.LINK}, Contracts.TeamsContract.NAME + "=?", new String[]{teamName}, null);
        if (result != null && result.moveToFirst()) {
            String link = result.getString(0);
            result.close();
            return link;
        }
        return null;
    }

    public static ContentController getInstance() {
        if (instance == null) {
            instance = new ContentController();
        }
        return instance;
    }
    private ContentController() {

    }

    public int insertNews (List<Feed> list, String teamName, Context ctx) {
        int previous = 0;
        int result = 0;
        ContentResolver contentResolver = ctx.getContentResolver();
        List<ContentValues> values = new ArrayList<ContentValues>();
        for(Feed feed : list) {
           values.add(feed.toContentValues(teamName));
        }
        //TODO Find a better way to check how many new rows were inserted
       Cursor cursor = contentResolver.query(NewsProvider.CONTENT_URI_NEWS, null,Contracts.NewsContract.TEAM+" = ?",new String[]{teamName},null);
        if (cursor != null) {
            previous = cursor.getCount();
            cursor.close();
        }
       contentResolver.bulkInsert(NewsProvider.CONTENT_URI_NEWS,values.toArray(new ContentValues[values.size()]));
        cursor = contentResolver.query(NewsProvider.CONTENT_URI_NEWS, null,Contracts.NewsContract.TEAM+" = ?",new String[]{teamName},null);
        if (cursor != null) {
            result = cursor.getCount() - previous;
            cursor.close();
        }
        return result;
    }


}
