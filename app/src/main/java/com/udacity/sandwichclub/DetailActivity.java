package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.also_known_tv) TextView tvAlsoKnown;
    @BindView(R.id.ingredients_tv) TextView tvIngredients;
    @BindView(R.id.origin_tv) TextView tvOrigin;
    @BindView(R.id.description_tv) TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        String txtAlsoKnown = TextUtils.join("\n", sandwich.getAlsoKnownAs());
        if (txtAlsoKnown == "" || txtAlsoKnown.isEmpty()) {
            txtAlsoKnown = "-";
        }

        String txtIngredients = TextUtils.join("\n", sandwich.getIngredients());
        if (txtIngredients == "" || txtIngredients.isEmpty()) {
            txtIngredients = "-";
        }

        String txtOrigin = sandwich.getPlaceOfOrigin();
        if (txtOrigin == "" || txtOrigin.isEmpty()) {
            txtOrigin = "-";
        }

        String txtDescription = sandwich.getDescription();
        if (txtDescription == "" || txtDescription.isEmpty()){
            txtDescription = "-";
        }

        tvAlsoKnown.setText(txtAlsoKnown);
        tvIngredients.setText(txtIngredients);
        tvOrigin.setText(txtOrigin);
        tvDescription.setText(txtDescription);
    }
}
