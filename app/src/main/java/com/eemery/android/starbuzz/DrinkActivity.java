package com.eemery.android.starbuzz;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends Activity {

    public static final String EXTRA_DRINK_ID = "drinkId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        // Get the Drink from the intent
        int drinkId = (Integer) getIntent().getExtras().get(EXTRA_DRINK_ID);

        // Create a cursor
        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
        try {
            SQLiteDatabase db = starbuzzDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("DRINK",
                    new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAVORITE"},
                    "_id = ?",
                    new String[]{Integer.toString(drinkId)},
                    null,
                    null,
                    null);

            // Move to the first record in the cursor
            if (cursor.moveToFirst()) {

                // Get the drink details from the cursor
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoId = cursor.getInt(2);
                boolean isFavorite = (cursor.getInt(3) == 1);

                // Populate the Drink name
                TextView name = findViewById(R.id.name);
                name.setText(nameText);

                // Populate the Drink description
                TextView description = findViewById(R.id.description);
                description.setText(descriptionText);

                // Populate the Drink image
                ImageView photo = findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);

                // Populate the favorite checkbox
                CheckBox favorite = findViewById(R.id.favorite);
                favorite.setChecked(isFavorite);

                cursor.close();
            }
        } catch (SQLiteException e) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    public void onFavoriteClicked(View view) {
        int drinkID = (int) getIntent().getExtras().get(EXTRA_DRINK_ID);

        // Get the value of the checkbox
        CheckBox favorite = findViewById(R.id.favorite);
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("FAVORITE", favorite.isChecked());

        // Get a reference to the database and update the FAVORITE column
        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
        try {
            SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();
            db.update("DRINK",
                    drinkValues,
                    "id = ?",
                    new String[]{Integer.toString(drinkID)});
            db.close();
        } catch (SQLException e) {
            Toast.makeText(this, "Database Unavailable", Toast.LENGTH_SHORT).show();
        }
    }
}
