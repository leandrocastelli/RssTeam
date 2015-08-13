package com.lcsmobileapps.rssteam.com.lcsmobileapps.rssteam.com.lcsmobileapps.rssteam.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by leandro.silverio on 13/08/2015.
 */
public class NewsProvider extends ContentProvider {

    private static final String AUTHORITY = "com.lcsmobileapps.rssteams.provider";
    private static final String CONTENT_TYPE_NEWS = "vnd.android.cursor.dir/vnd.com.lcsmobileapps.providers."+ Contracts.NewsContract.TABLE_NAME;
    private static final String CONTENT_TYPE_NEWS_ITEM = "vnd.android.cursor.item/vnd.com.lcsmobileapps.providers."+ Contracts.NewsContract.TABLE_NAME;
    private static final String CONTENT_TYPE_TEAM = "vnd.android.cursor.dir/vnd.com.lcsmobileapps.providers."+ Contracts.TeamsContract.TABLE_NAME;
    private static final String CONTENT_TYPE_TEAM_ITEM = "vnd.android.cursor.item/vnd.com.lcsmobileapps.providers."+ Contracts.TeamsContract.TABLE_NAME;
    protected static final Uri CONTENT_URI_NEWS = Uri.parse("content://"+AUTHORITY+Contracts.NewsContract.TABLE_NAME);
    protected static final Uri CONTENT_URI_TEAMS = Uri.parse("content://"+AUTHORITY+Contracts.TeamsContract.TABLE_NAME);

    private DBHelper dbHelper;
    protected enum Types {
        ALL_NEWS ,
        ITEM_NEWS,
        ALL_TEAMS,
        ITEM_TEAMS
    }
    private static UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,Contracts.NewsContract.TABLE_NAME,Types.ALL_NEWS.ordinal());
        uriMatcher.addURI(AUTHORITY,Contracts.NewsContract.TABLE_NAME+"/#",Types.ITEM_NEWS.ordinal());
        uriMatcher.addURI(AUTHORITY,Contracts.TeamsContract.TABLE_NAME,Types.ALL_TEAMS.ordinal());
        uriMatcher.addURI(AUTHORITY,Contracts.TeamsContract.TABLE_NAME+"/#",Types.ITEM_TEAMS.ordinal());
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    protected final class DBHelper extends SQLiteOpenHelper {
        String CREATE_TABLE =
                "CREATE TABLE " + Contracts.NewsContract.TABLE_NAME + " ( "
                        + Contracts.NewsContract._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + Contracts.NewsContract.TEAM + " TEXT NOT NULL, "
                        + Contracts.NewsContract.TITLE + " TEXT NOT NULL, "
                        + Contracts.NewsContract.LINK + " TEXT NOT NULL, "
                        + Contracts.NewsContract.DATE  + " TEXT NOT NULL );" +
                        "CREATE TABLE " + Contracts.TeamsContract.TABLE_NAME + " ( "
                        + Contracts.TeamsContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + Contracts.TeamsContract.NAME +" TEXT NOT NULL, "
                        + Contracts.TeamsContract.FLAG + " TEXT NOT NULL, "
                        + Contracts.TeamsContract.LINK + " TEXT NOT NULL) ";


        public DBHelper(Context context) {
            super(context, Contracts.DB, null, 1);

        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
