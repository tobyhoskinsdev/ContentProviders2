package examples.aaronhoskins.com.contentproviders;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import static examples.aaronhoskins.com.contentproviders.ProviderContract.COLUMN_DESCRIPTION;
import static examples.aaronhoskins.com.contentproviders.ProviderContract.COLUMN_GENRE;
import static examples.aaronhoskins.com.contentproviders.ProviderContract.COLUMN_GENRE_NAME;
import static examples.aaronhoskins.com.contentproviders.ProviderContract.COLUMN_RATING;
import static examples.aaronhoskins.com.contentproviders.ProviderContract.COLUMN_RELEASE_DATE;
import static examples.aaronhoskins.com.contentproviders.ProviderContract.COLUMN_TITLE;
import static examples.aaronhoskins.com.contentproviders.ProviderContract.DATABASE_NAME;
import static examples.aaronhoskins.com.contentproviders.ProviderContract.DATABASE_VERSION;
import static examples.aaronhoskins.com.contentproviders.ProviderContract.TABLE_NAME_GENRE;
import static examples.aaronhoskins.com.contentproviders.ProviderContract.TABLE_NAME_MOVIES;

public class MoviesDbHelper extends SQLiteOpenHelper {
    public MoviesDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createGenreTable(db);
        createMovieTable(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    //Create Movie Table
    private void createMovieTable(SQLiteDatabase db) {
        String createMovieTableQuery = "CREATE TABLE " + TABLE_NAME_MOVIES + " ("
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_RATING + " TEXT, "
                + COLUMN_RELEASE_DATE + " TEXT, "
                + COLUMN_GENRE + " TEXT)";

        db.execSQL(createMovieTableQuery);

    }

    //Create Genre Table
    private void createGenreTable(SQLiteDatabase db) {
        String createGenreTableQuery = "CREATE TABLE " + TABLE_NAME_GENRE + " ("
                + COLUMN_GENRE_NAME + " TEXT)";

        db.execSQL(createGenreTableQuery);

    }

    //insert into Movie Table
    public long insertMovie(Movies movie) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TITLE , movie.getTitle());
        contentValues.put(COLUMN_DESCRIPTION, movie.getDesciption());
        contentValues.put(COLUMN_GENRE, movie.getGenre());
        contentValues.put(COLUMN_RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(COLUMN_RATING, movie.getRating());

        return database.insert(TABLE_NAME_MOVIES, null, contentValues);
    }

    //insert into Genre Table
    public long insertGenre(Genre genre) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_GENRE_NAME, genre.getGenreName());

        return database.insert(TABLE_NAME_GENRE,null, contentValues);
    }

    //update Movie Table
    public long updateMovie(Movies movie) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String whereClause = " WHERE " + COLUMN_TITLE + " = \"" + movie.getTitle() + "\"";

        contentValues.put(COLUMN_TITLE , movie.getTitle());
        contentValues.put(COLUMN_DESCRIPTION, movie.getDesciption());
        contentValues.put(COLUMN_GENRE, movie.getGenre());
        contentValues.put(COLUMN_RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(COLUMN_RATING, movie.getRating());

        return database.update(TABLE_NAME_MOVIES, contentValues, whereClause, null);
    }

    //delete from Movie Table
    public long deleteMovie(String movieTitle) {
        SQLiteDatabase database = this.getWritableDatabase();

        return database.delete(TABLE_NAME_MOVIES,
                " WHERE " + COLUMN_TITLE + " = \"" + movieTitle + "\""
                ,null);
    }

    //delete from Genre Table
    public long deleteGenre(String genreName) {
        SQLiteDatabase database = this.getWritableDatabase();

        return database.delete(TABLE_NAME_GENRE,
                " WHERE " + COLUMN_GENRE_NAME + " = \"" + genreName + "\""
                ,null);
    }

    //Retrieve all movies
    public ArrayList<Movies> retriveAllMovies() {
        SQLiteDatabase database = this.getReadableDatabase();
        String selectionQuery = "SELECT * FROM " + TABLE_NAME_MOVIES;
        ArrayList<Movies> moviesArrayList = new ArrayList<>();

        Cursor cursor = database.rawQuery(selectionQuery, null);

        if(cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String rating = cursor.getString(cursor.getColumnIndex(COLUMN_RATING));
                String releaseDate = cursor.getString(cursor.getColumnIndex(COLUMN_RELEASE_DATE));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                String genre = cursor.getString(cursor.getColumnIndex(COLUMN_GENRE));

                moviesArrayList.add(new Movies(title, rating, description, releaseDate, genre));
            } while(cursor.moveToNext());
        }

        cursor.close();
        return moviesArrayList;
    }

    //Retrieve all genre
    public ArrayList<Genre> retriveAllGenre() {
        SQLiteDatabase database = this.getReadableDatabase();
        String selectionQuery = "SELECT * FROM " + TABLE_NAME_GENRE;
        ArrayList<Genre> genreArrayList = new ArrayList<>();

        Cursor cursor = database.rawQuery(selectionQuery, null);

        if(cursor.moveToFirst()) {
            do {
                String genre = cursor.getString(cursor.getColumnIndex(COLUMN_GENRE_NAME));

                genreArrayList.add(new Genre(genre));
            } while(cursor.moveToNext());
        }

        cursor.close();
        return genreArrayList;
    }

    //Retrieve one movie
    public Movies getSingleMovie(String movieTitle) {
        SQLiteDatabase database = this.getReadableDatabase();
        String selectionQuery = "SELECT * FROM " + TABLE_NAME_MOVIES
                + " WHERE " + COLUMN_TITLE + " = \"" + movieTitle + "\"";
        Movies returnMovie = null;

        Cursor cursor = database.rawQuery(selectionQuery, null);
        if(cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
            String rating = cursor.getString(cursor.getColumnIndex(COLUMN_RATING));
            String releaseDate = cursor.getString(cursor.getColumnIndex(COLUMN_RELEASE_DATE));
            String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
            String genre = cursor.getString(cursor.getColumnIndex(COLUMN_GENRE));

            returnMovie = new Movies(title, rating, description, releaseDate, genre);
        }
        cursor.close();
        return returnMovie;
    }

}
