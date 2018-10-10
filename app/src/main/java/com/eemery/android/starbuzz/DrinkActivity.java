package com.eemery.android.starbuzz;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DrinkActivity extends Activity {

    public static final String EXTRA_DRINK_ID = "drinkId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        // Get the Drink from the intent
        int drinkId = (Integer) getIntent().getExtras().get(EXTRA_DRINK_ID);
        Drink drink = Drink.drinks[drinkId];

        // Populate the Drink name
        TextView name = findViewById(R.id.name);
        name.setText(drink.getName());

        // Populate the Drink description
        TextView description = findViewById(R.id.description);
        description.setText(drink.getDescription());

        // Populate the Drink image
        ImageView photo = findViewById(R.id.photo);
        photo.setImageResource(drink.getImageResourceId());
        photo.setContentDescription(drink.getName());
    }
}
