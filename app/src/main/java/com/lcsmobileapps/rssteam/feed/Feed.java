package com.lcsmobileapps.rssteam.feed;

import android.content.ContentValues;

import com.lcsmobileapps.rssteam.provider.Contracts;
import com.lcsmobileapps.rssteam.util.Utils;

import java.util.Date;

/**
 * Created by leandro.silverio on 11/08/2015.
 */
public class Feed {
    private String title;
    private String link;
    private Date date;
    public Feed(String title, String link) {
        this.setTitle(title);
        this.setLink(link);
        if (title != null && title.length() > 17) {

            int length = title.length();
            String temp = title.substring(title.length() - 15, title.length() - 1);

            date = Utils.convertToDate(temp);
        }
    }
    public Feed(String title, String link, Date date) {
       this(title,link);
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ContentValues toContentValues(String teamName) {
        ContentValues current = new ContentValues();
        current.put(Contracts.NewsContract.TEAM,teamName);
        current.put(Contracts.NewsContract.TITLE,getTitle());
        current.put(Contracts.NewsContract.LINK,getLink());
        current.put(Contracts.NewsContract.DATE,Utils.convertToString(getDate()));
        return  current;
    }
}
