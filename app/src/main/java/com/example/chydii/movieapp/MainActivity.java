package com.example.chydii.movieapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

import utils.NetworkUrl;

public class MainActivity extends AppCompatActivity {


        private EditText mSearchBoxEditText;
        private TextView mUrlDisplayTextView;
        private TextView mSearchResultsTextView;
        private TextView mErrorMessageDisplay;
        private ProgressBar mLoadingIndicator;

        @Override
        public void onCreate (Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);

            mUrlDisplayTextView =(TextView)  findViewById(R.id.tv_url_display);

            mSearchResultsTextView = (TextView) findViewById(R.id.tv_moviedb_search_results_json);

            mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
            mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        }
            private void makeMoviedbSearchQuery () {
                String moviedbQuery = mSearchBoxEditText.getText().toString();
                URL moviedbSearchUrl = NetworkUrl.buildUrl(moviedbQuery);
                mUrlDisplayTextView.setText(moviedbSearchUrl.toString());
                new MoviedbQueryTask().execute(moviedbSearchUrl);

            }
            private void showJsonDataView() {
                mErrorMessageDisplay.setVisibility(View.INVISIBLE);
                mSearchResultsTextView.setVisibility(View.VISIBLE);

            }
            private void showErrorMessage() {
                mSearchResultsTextView.setVisibility(View.INVISIBLE);
                mErrorMessageDisplay.setVisibility(View.VISIBLE);
            }

            public class MoviedbQueryTask extends AsyncTask<URL, Void, String> {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                }

                @Override
                protected String doInBackground(URL... params) {
                    URL searchUrl = params[0];
                    String moviedbSearchResults = null;
                    try {
                        moviedbSearchResults = NetworkUrl.getResponseFromHttpUrl(searchUrl);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return moviedbSearchResults;

                }

                @Override
                protected void onPostExecute(String moviedbSearchResults) {
                    mLoadingIndicator.setVisibility(View.INVISIBLE);
                    if (moviedbSearchResults != null && !moviedbSearchResults.equals("")) {
                        showJsonDataView();
                        mSearchResultsTextView.setText(moviedbSearchResults);
                    } else {
                         showErrorMessage();
                    }

                }

            }
            @Override
            public boolean onCreateOptionsMenu(Menu menu){
                getMenuInflater().inflate(R.menu.main, menu);
                return true;

            }

            @Override
            public boolean onOptionsItemSelected (MenuItem item){
                int itemThatWasClickedId = item.getItemId();
                if (itemThatWasClickedId == R.id.action_search) {
                    makeMoviedbSearchQuery();
                    return true;
                }
                return super.onOptionsItemSelected(item);
            }

        }


