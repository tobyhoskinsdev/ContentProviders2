package examples.aaronhoskins.com.contentproviders;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class ProviderContract {
    //Content authority and base URI
    public static final String CONTENT_AUTHORITY = "examples.aaronhoskins.com.contentproviders";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //Database base info
    public static final String DATABASE_NAME = "movies_db";
    public static final int DATABASE_VERSION = 1;
    //Table names
    public static final String TABLE_NAME_GENRE = "genre_table";
    public static final String TABLE_NAME_MOVIES = "movies_table";
    //Columns - Movies
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_RELEASE_DATE = "release_date";
    public static final String COLUMN_GENRE = "genre";
    //Column - Genres
    public static final String COLUMN_GENRE_NAME = "genre_name";

    public static final class MovieEntry implements BaseColumns {
        // Content URI represents the base location for the table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME_MOVIES).build();

        // These are special type prefixes that specify if a URI returns a list or a specific item
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_URI  + "/" + TABLE_NAME_MOVIES;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_URI + "/" + TABLE_NAME_MOVIES;

        // Define a function to build a URI to find a specific movie by it's identifier
        public static Uri buildMovieUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class GenreEntry implements BaseColumns {
        // Content URI represents the base location for the table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME_GENRE).build();

        // These are special type prefixes that specify if a URI returns a list or a specific item
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_URI  + "/" + TABLE_NAME_GENRE;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_URI + "/" + TABLE_NAME_GENRE;

        // Define a function to build a URI to find a specific genre by it's identifier
        public static Uri buildGenreUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }





}
