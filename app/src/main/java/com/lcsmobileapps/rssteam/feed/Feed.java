package com.lcsmobileapps.rssteam.feed;

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
}
