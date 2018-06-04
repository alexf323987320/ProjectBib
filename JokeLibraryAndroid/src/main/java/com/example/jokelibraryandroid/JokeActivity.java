package com.example.jokelibraryandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    public static final String JOKE_KEY = "joke_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        String joke = getIntent().getStringExtra(JOKE_KEY);
        if (joke == null) {
            throw new IllegalArgumentException("No joke extra");
        }
        TextView jokeTv = findViewById(R.id.joke_tv);
        jokeTv.setText(joke);
    }
}
