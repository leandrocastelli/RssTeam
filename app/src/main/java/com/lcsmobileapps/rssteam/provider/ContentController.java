package com.lcsmobileapps.rssteam.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.lcsmobileapps.rssteam.feed.Feed;
import com.lcsmobileapps.rssteam.feed.Team;
import com.lcsmobileapps.rssteam.util.Utils;

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

    public Team getTeam(String teamName, Context ctx) {

        ContentResolver contentResolver = ctx.getContentResolver();
        Cursor result = contentResolver.query(NewsProvider.CONTENT_URI_TEAMS, new String[]{Contracts.TeamsContract.NAME,Contracts.TeamsContract.FLAG,Contracts.TeamsContract.LINK}, Contracts.TeamsContract.NAME + "=?", new String[]{teamName}, null);
        if (result != null && result.moveToFirst()) {
            String name = result.getString(0);
            int flag = result.getInt(1);
            String link = result.getString(2);
            result.close();
            return new Team(name, flag, link);
        }
        return null;
    }

    public Team getTeamFromFeed(Feed feed, Context ctx) {

        ContentResolver contentResolver = ctx.getContentResolver();

        /*RAW METHOD
        String tempRaw = "SELECT " + Contracts.TeamsContract.NAME + " FROM " +
                Contracts.TeamsContract.TABLE_NAME + " INNER JOIN " +
                Contracts.NewsContract.TABLE_NAME + " ON " +
                Contracts.TeamsContract.TABLE_NAME+"."+ Contracts.TeamsContract.NAME +
                " = "+Contracts.NewsContract.TABLE_NAME+"."+Contracts.NewsContract.TEAM +
                " WHERE " + Contracts.NewsContract.TABLE_NAME + "."+Contracts.NewsContract.LINK + " = \""+ feed.getLink() + "\""; */
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.

        Cursor result =
        if (result != null && result.moveToFirst()) {
            String name = result.getString(0);
            int flag = result.getInt(1);
            String link = result.getString(2);
            result.close();
            return new Team(name, flag, link);
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

       int temp = contentResolver.bulkInsert(NewsProvider.CONTENT_URI_NEWS, values.toArray(new ContentValues[values.size()]));

        return result;
    }

    public List<Feed> getNews (String teamName, Context ctx) {
        List<Feed> list = new ArrayList<Feed>();
        ContentResolver contentResolver = ctx.getContentResolver();
        Cursor result = contentResolver.query(NewsProvider.CONTENT_URI_NEWS, new String[]{Contracts.NewsContract.TITLE, Contracts.NewsContract.LINK, Contracts.NewsContract.DATE}, Contracts.NewsContract.TEAM + "=?", new String[]{teamName}, null);
        if (result == null) {
            return null;
        }
        while (result.moveToNext()) {
            list.add(new Feed(result.getString(0),result.getString(1),Utils.convertToDate(result.getString(2))));
        }
        return list;
    }


}
