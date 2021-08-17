package sg.edu.rp.c346.id19013886.wishanime;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "animes.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_ANIME = "Anime";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_SEASON = "season";
    private static final String COLUMN_STARS = "stars";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // CREATE TABLE Song
        // (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT,
        // singers TEXT, stars INTEGER, year INTEGER );
        String createAnimeTableSql = "CREATE TABLE " + TABLE_ANIME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_SEASON + " INTEGER, "
                + COLUMN_STARS + " INTEGER )";
        db.execSQL(createAnimeTableSql);
        Log.i("info", createAnimeTableSql + "\ncreated tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANIME);
        onCreate(db);
    }

    public long insertAnime(String title, String singers, int season, int stars) {
        // Get an instance of the database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, singers);
        values.put(COLUMN_SEASON, season);
        values.put(COLUMN_STARS, stars);
        // Insert the row into the TABLE_SONG
        long result = db.insert(TABLE_ANIME, null, values);
        // Close the database connection
        db.close();
        Log.d("SQL Insert","" + result);
        return result;
    }

    public ArrayList<Anime> getAllAnimes() {
        ArrayList<Anime> animeslist = new ArrayList<Anime>();
        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_TITLE + "," + COLUMN_DESCRIPTION + ","
                + COLUMN_SEASON + ","
                + COLUMN_STARS + " FROM " + TABLE_ANIME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String desc = cursor.getString(2);
                int season = cursor.getInt(3);
                int stars = cursor.getInt(4);

                Anime newIsland = new Anime(id, title, desc, season, stars);
                animeslist.add(newIsland);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return animeslist;
    }

    public ArrayList<Anime> getAllanimesByStars(int starsFilter) {
        ArrayList<Anime> animeslist = new ArrayList<Anime>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_SEASON, COLUMN_STARS};
        String condition = COLUMN_STARS + ">= ?";
        String[] args = {String.valueOf(starsFilter)};

        Cursor cursor;
        cursor = db.query(TABLE_ANIME, columns, condition, args, null, null, null, null);

        // Loop through all rows and add to ArrayList
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String desc = cursor.getString(2);
                int area = cursor.getInt(3);
                int stars = cursor.getInt(4);

                Anime newAnime = new Anime(id, name, desc, area, stars);
                animeslist.add(newAnime);
            } while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();
        return animeslist;
    }

    /*public ArrayList<String> getStars() {
        ArrayList<String> showStars = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_STARS};

        Cursor cursor;
        cursor = db.query(true, TABLE_ANIME, columns,
                null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                showStars.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return showStars;

    }*/

    public int updateAnime(Anime data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, data.getTitle());
        values.put(COLUMN_DESCRIPTION, data.getDescription());
        values.put(COLUMN_SEASON, data.getSeason());
        values.put(COLUMN_STARS, data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_ANIME, values, condition, args);
        db.close();
        return result;
    }


    public int deleteAnime(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_ANIME, condition, args);
        db.close();
        return result;
    }

}
