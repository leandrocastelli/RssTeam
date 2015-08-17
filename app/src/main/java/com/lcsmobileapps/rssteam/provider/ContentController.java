package com.lcsmobileapps.rssteam.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

import com.lcsmobileapps.rssteam.feed.Feed;

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

    public void insertNews (List<Feed> list, String teamName) {

    }
}
