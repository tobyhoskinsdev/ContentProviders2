package examples.aaronhoskins.com.contentproviders;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class ContentProviders extends ContentProvider {
    //Data persistence object
    MoviesDbHelper moviesDbHelper;

    //paths switch values
    public static final int MOVIE = 500;
    public static final int GENRE = 600;
    public static final int MOVIE_ID = 501;
    public static final int GENRE_ID = 601;

    //URI Matcher
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher(){
        String content = ProviderContract.CONTENT_AUTHORITY;

        // All paths to the UriMatcher have a corresponding code to return
        // when a match is found (the ints above).
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(content, ProviderContract.TABLE_NAME_GENRE, GENRE);
        matcher.addURI(content, ProviderContract.TABLE_NAME_GENRE + "/#", GENRE_ID);
        matcher.addURI(content, ProviderContract.TABLE_NAME_MOVIES, MOVIE);
        matcher.addURI(content, ProviderContract.TABLE_NAME_MOVIES + "/#", MOVIE_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        moviesDbHelper = new MoviesDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = moviesDbHelper.getWritableDatabase();
        Cursor retCursor;
        long _id;
        switch(sUriMatcher.match(uri)){
            case GENRE:
                retCursor = db.query(
                        ProviderContract.TABLE_NAME_GENRE,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case GENRE_ID:
                _id = ContentUris.parseId(uri);
                retCursor = db.query(
                        ProviderContract.TABLE_NAME_GENRE,
                        projection,
                        ProviderContract.COLUMN_GENRE_NAME + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            case MOVIE:
                retCursor = db.query(
                        ProviderContract.TABLE_NAME_MOVIES,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case MOVIE_ID:
                _id = ContentUris.parseId(uri);
                retCursor = db.query(
                        ProviderContract.TABLE_NAME_MOVIES,
                        projection,
                        ProviderContract.COLUMN_TITLE + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Set the notification URI for the cursor to the one passed into the function. This
        // causes the cursor to register a content observer to watch for changes that happen to
        // this URI and any of it's descendants. By descendants, we mean any URI that begins
        // with this path.
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch(sUriMatcher.match(uri)){
            case GENRE:
                return ProviderContract.GenreEntry.CONTENT_TYPE;
            case GENRE_ID:
                return ProviderContract.GenreEntry.CONTENT_ITEM_TYPE;
            case MOVIE:
                return ProviderContract.MovieEntry.CONTENT_TYPE;
            case MOVIE_ID:
                return ProviderContract.MovieEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = moviesDbHelper.getWritableDatabase();
        long _id;
        Uri returnUri;

        switch(sUriMatcher.match(uri)){
            case GENRE:
                _id = db.insert(ProviderContract.TABLE_NAME_GENRE, null, values);
                if(_id > 0){
                    returnUri =  ProviderContract.GenreEntry.buildGenreUri(_id);
                } else{
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            case MOVIE:
                _id = db.insert(ProviderContract.TABLE_NAME_MOVIES, null, values);
                if(_id > 0){
                    returnUri = ProviderContract.MovieEntry.buildMovieUri(_id);
                } else{
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Use this on the URI passed into the function to notify any observers that the uri has
        // changed.
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = moviesDbHelper.getWritableDatabase();
        int rowsDeleted = 0;
        switch(sUriMatcher.match(uri)) {
            case GENRE:
                rowsDeleted = db.delete(ProviderContract.TABLE_NAME_GENRE, selection, selectionArgs);
                break;
            case MOVIE:
                rowsDeleted = db.delete(ProviderContract.TABLE_NAME_MOVIES, selection, selectionArgs);
                break;
                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if(selection == null || rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = moviesDbHelper.getWritableDatabase();
            int rows;

            switch(sUriMatcher.match(uri)){
                case GENRE:
                    rows = db.update(ProviderContract.TABLE_NAME_GENRE, values, selection, selectionArgs);
                    break;
                case MOVIE:
                    rows = db.update(ProviderContract.TABLE_NAME_MOVIES, values, selection, selectionArgs);
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
            }

            if(rows != 0){
                getContext().getContentResolver().notifyChange(uri, null);
            }

            return rows;
    }
}
