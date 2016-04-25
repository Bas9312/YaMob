package bas.com.yamob;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import bas.com.yamob.models.ArtistModel;

/**
 * Created by bas on 16.04.16.
 */
public class DefaultArtistsListGetter implements ArtistsListGetter {
    private static String URL_FOR_DOWNLOAD = "http://cache-ekt05.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/artists.json";

    public void getArtistsList(final OnArtistsAvailableHandler onArtistsAvailableHandler) {
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... urls) {
                if (urls.length == 0) {
                    Log.w("getArtistsList", "Url is not set.");
                    if (onArtistsAvailableHandler != null) onArtistsAvailableHandler.onArtistsAvailable(null);
                    return null;
                }
                Log.d("getArtistsList", "Request from url: " + urls[0]);

                HttpURLConnection connection = null;
                StringBuilder response = new StringBuilder();
                try {
                    URL url = new URL(urls[0]);

                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    if (onArtistsAvailableHandler != null) onArtistsAvailableHandler.onArtistsAvailable(null);
                    return null;
                } finally {
                    if (connection != null) connection.disconnect();
                }

                List<ArtistModel> artistModels = ArtistsJsonParser.parseFromJsonString(response.toString());
                if (onArtistsAvailableHandler != null) onArtistsAvailableHandler.onArtistsAvailable(artistModels);
                return null;
            }
        }.execute(URL_FOR_DOWNLOAD);
    }

}
