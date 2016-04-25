package bas.com.yamob;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import bas.com.yamob.models.ArtistModel;

/**
 * Created by bas on 16.04.16.
 */
public class ArtistsJsonParser {
    public static List<ArtistModel> parseFromJsonString(String jsonArtistsString) {
        return Arrays.asList(new Gson().fromJson(jsonArtistsString, ArtistModel[].class));
    }
}
