package com.lcsmobileapps.rssteam.provider;

import android.provider.BaseColumns;

/**
 * Created by leandro.silverio on 13/08/2015.
 */

public final class Contracts {
    protected static final String DB = "news.db";
    public static  final class NewsContract implements BaseColumns{
        protected static final String TABLE_NAME = "News";
        protected static final String TITLE = "Title";
        protected static final String LINK = "Link";
        protected static final String TEAM = "Team";
        protected static final String DATE = "Date";

    }
    public static  final class TeamsContract implements BaseColumns{
        protected static final String TABLE_NAME = "Teams";
        protected static final String NAME = "Name";
        protected static final String LINK = "Link";
        protected static final String FLAG = "Flag";
    }
}