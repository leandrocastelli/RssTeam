package com.lcsmobileapps.rssteam.com.lcsmobileapps.rssteam.feed;

/**
 * Created by leandro.silverio on 11/08/2015.
 */
public class Feed {
    private String title;
    private String link;
    public Feed(String title, String link) {
        this.setTitle(title);
        this.setLink(link);
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
}
