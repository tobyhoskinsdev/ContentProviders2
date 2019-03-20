package examples.aaronhoskins.com.contentproviders;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import static examples.aaronhoskins.com.contentproviders.ProviderContract.COLUMN_DESCRIPTION;
import static examples.aaronhoskins.com.contentproviders.ProviderContract.COLUMN_GENRE;
import static examples.aaronhoskins.com.contentproviders.ProviderContract.COLUMN_RATING;
import static examples.aaronhoskins.com.contentproviders.ProviderContract.COLUMN_RELEASE_DATE;
import static examples.aaronhoskins.com.contentproviders.ProviderContract.COLUMN_TITLE;

public class MainActivity extends AppCompatActivity {
    EditText etMovieTitle;
    EditText etMovieDecription;
    EditText etMovieRating;
    EditText etMovieReleaseDate;
    EditText etMovieGenre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
    }

    private void bindViews() {
        etMovieTitle = findViewById(R.id.etMovieTitle);
        etMovieDecription = findViewById(R.id.etMovieDescription);
        etMovieRating = findViewById(R.id.etMovieRating);
        etMovieGenre = findViewById(R.id.etMovieGenre);
        etMovieReleaseDate = findViewById(R.id.etMovieReleaseDate);
    }

    private Movies generateMovieFromInput() {
        String title = etMovieTitle.getText().toString();
        String desc = etMovieDecription.getText().toString();
        String rating = etMovieRating.getText().toString();
        String genre = etMovieGenre.getText().toString();
        String releaseDate = etMovieReleaseDate.getText().toString();

        return new Movies(title, rating, desc, releaseDate, genre);
    }

    public void onClick(View view) {
        Movies movie = generateMovieFromInput();
        Uri uri = ProviderContract.MovieEntry.CONTENT_URI;

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE , movie.getTitle());
        contentValues.put(COLUMN_DESCRIPTION, movie.getDesciption());
        contentValues.put(COLUMN_GENRE, movie.getGenre());
        contentValues.put(COLUMN_RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(COLUMN_RATING, movie.getRating());

        getContentResolver().insert(uri, contentValues);
        logAllMovies();

    }

    public void logAllMovies() {
        Uri uri = ProviderContract.MovieEntry.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        if(cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String rating = cursor.getString(cursor.getColumnIndex(COLUMN_RATING));
                String releaseDate = cursor.getString(cursor.getColumnIndex(COLUMN_RELEASE_DATE));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                String genre = cursor.getString(cursor.getColumnIndex(COLUMN_GENRE));

                Movies movie = new Movies(title, rating, description, releaseDate, genre);
                Log.d("TAG", "logAllMovies: " + movie.toString());
            } while(cursor.moveToNext());
        }

        cursor.close();
    }
}
