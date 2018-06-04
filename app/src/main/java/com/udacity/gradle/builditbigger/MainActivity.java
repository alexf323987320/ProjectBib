package com.udacity.gradle.builditbigger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.jokelibraryandroid.JokeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.jokesApi.JokesApi;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static JokesApi mJokesApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        //Toast.makeText(this, Jokes.getJoke(), Toast.LENGTH_SHORT).show();
        //String joke = Jokes.getJoke();

        //noinspection unchecked
        new EndpointAsyncTask().execute(new Pair<Context, Integer>(this, 0));
    }

    //*******************EndpointAsyncTask*******************//
    private static class EndpointAsyncTask extends AsyncTask<Pair<Context, Integer>, Void, String> {

        @SuppressLint("StaticFieldLeak")
        private Context mContext;
        private int mJokeNumber;

        @Override
        protected String doInBackground(Pair<Context, Integer>[] pairs) {
            mContext = pairs[0].first;
            mJokeNumber = pairs[0].second;

            //initialize once
            if (mJokesApi == null) {
                mJokesApi = new JokesApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(),
                        null).
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        }).build();
            }
            try {
                return mJokesApi.getJoke(0).execute().getStringJoke();
            } catch (IOException e) {
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String joke) {
            super.onPostExecute(joke);

            //start activity from android library
            Intent intent = new Intent(mContext, JokeActivity.class);
            intent.putExtra(JokeActivity.JOKE_KEY, joke);
            mContext.startActivity(intent);

        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
            mContext = null;
        }
    }
//*******************End*of*EndpointAsyncTask*******************//

}
