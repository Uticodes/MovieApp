package utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUrl {
    final static String MOVIEDB_BASE_URL = "https://api.themoviedb.org";
    final static String PARAM_QUERY = "QUE";
    final static String PARAM_SORT = "sort";
    final static String sortBy = "stars";

    public static URL buildUrl(String moviedbSearchQuery){
        Uri buildUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, moviedbSearchQuery)
                .appendQueryParameter(PARAM_SORT, sortBy)
                .build();

        URL url = null;
        try {
            url = new URL(buildUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inpu = urlConnection.getInputStream();

            Scanner scanner = new Scanner(inpu);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput){
                return scanner.next();
            }else{
                return null;
            }
        }finally {
            urlConnection.disconnect();
        }
    }

}
