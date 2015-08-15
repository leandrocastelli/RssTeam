package com.lcsmobileapps.rssteam.provider;

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

    DatabaseDAO dao;
    private static final int ALL_NEWS = 0;
    private static final int ITEM_NEWS = 1;
    private static final int ALL_TEAMS = 2;
    private static final int ITEM_TEAMS = 3;
    private DBHelper dbHelper;
    private static UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,Contracts.NewsContract.TABLE_NAME, ALL_NEWS);
        uriMatcher.addURI(AUTHORITY,Contracts.NewsContract.TABLE_NAME+"/#",ITEM_NEWS);
        uriMatcher.addURI(AUTHORITY,Contracts.TeamsContract.TABLE_NAME,ALL_TEAMS);
        uriMatcher.addURI(AUTHORITY,Contracts.TeamsContract.TABLE_NAME+"/#",ITEM_TEAMS);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String table =getTableFromURI(uri);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(table,  projection, selection, selectionArgs, null, null, sortOrder);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {

        int res = uriMatcher.match(uri);
        switch (res) {
            case ALL_TEAMS:
                return CONTENT_TYPE_TEAM;
            case ITEM_TEAMS:
                return CONTENT_TYPE_TEAM_ITEM;
            case ALL_NEWS:
                return CONTENT_TYPE_NEWS;
            case ITEM_NEWS:
                return CONTENT_TYPE_NEWS_ITEM;
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        String type = getType(uri);
        String table = null;
        Uri contentUri = null;
        switch (type) {
            case CONTENT_TYPE_NEWS:
            case CONTENT_TYPE_NEWS_ITEM: {
                table = Contracts.NewsContract.TABLE_NAME;
                contentUri = CONTENT_URI_NEWS;

            }break;
            case CONTENT_TYPE_TEAM:
            case CONTENT_TYPE_TEAM_ITEM: {
                table = Contracts.TeamsContract.TABLE_NAME;
                contentUri = CONTENT_URI_TEAMS;

            }break;
        }
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long value = database.insert(table, null, contentValues);

        return Uri.withAppendedPath(contentUri, String.valueOf(value));
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String table = getTableFromURI(uri);
        SQLiteDatabase dataBase=dbHelper.getWritableDatabase();

        return dataBase.delete(table, selection, selectionArgs);
    }
    private String getTableFromURI (Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ALL_NEWS:
            case ITEM_NEWS:
                return Contracts.NewsContract.TABLE_NAME;

            case ALL_TEAMS:
            case ITEM_TEAMS: {
                return Contracts.TeamsContract.TABLE_NAME;

            }

        }
        return "";
    }
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        String table = getType(uri);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        return database.update(table, contentValues, selection, selectionArgs);

    }

    protected final class DBHelper extends SQLiteOpenHelper {
        String CREATE_TABLE_NEWS =
                "CREATE TABLE IF NOT EXISTS" + Contracts.NewsContract.TABLE_NAME + " ( "
                        + Contracts.NewsContract._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + Contracts.NewsContract.TEAM + " TEXT NOT NULL, "
                        + Contracts.NewsContract.TITLE + " TEXT NOT NULL, "
                        + Contracts.NewsContract.LINK + " TEXT NOT NULL, "
                        + Contracts.NewsContract.DATE  + " TEXT NOT NULL )";
        String CREATE_TABLE_TEAMS =
                "CREATE TABLE IF NOT EXISTS " + Contracts.TeamsContract.TABLE_NAME + " ( "
                        + Contracts.TeamsContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + Contracts.TeamsContract.NAME +" TEXT NOT NULL, "
                        + Contracts.TeamsContract.FLAG + " TEXT NOT NULL, "
                        + Contracts.TeamsContract.LINK + " TEXT NOT NULL) ";


        public DBHelper(Context context) {
            super(context, Contracts.DB, null, 1);

        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_TABLE_NEWS);
            sqLiteDatabase.execSQL(CREATE_TABLE_TEAMS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }

}
