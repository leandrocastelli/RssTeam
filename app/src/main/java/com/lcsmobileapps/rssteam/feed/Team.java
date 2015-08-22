package com.lcsmobileapps.rssteam.feed;

/**
 * Created by leandro.silverio on 20/08/2015.
 */
public class Team {
    public int flag;
    public String name;
    public String link;

    public Team (String name, int flag, String link) {
        this.name = name;
        this.flag = flag;
        this.link = link;
    }
}
