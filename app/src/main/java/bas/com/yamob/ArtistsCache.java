package bas.com.yamob;

import android.util.Log;

import java.util.List;

import bas.com.yamob.models.ArtistModel;

/**
 * Created by bas on 17.04.16.
 */
public class ArtistsCache implements ArtistsListGetter {
    private List<ArtistModel> artistCachedModels = null;
    private final ArtistsListGetter artistsListGetter;

    public ArtistsCache(ArtistsListGetter artistsListGetter) {
        this.artistsListGetter = artistsListGetter;
    }

    @Override
    public void getArtistsList(final OnArtistsAvailableHandler onArtistsAvailableHandler) {
        if (artistCachedModels != null) {
            Log.d("getArtistsList", "get from cache");
            if (onArtistsAvailableHandler != null) onArtistsAvailableHandler.onArtistsAvailable(artistCachedModels);
        }
        else {
            artistsListGetter.getArtistsList(new OnArtistsAvailableHandler() {//Use default getter, and save data
                @Override
                public void onArtistsAvailable(List<ArtistModel> artistModels) {
                    artistCachedModels = artistModels;
                    if (onArtistsAvailableHandler != null) onArtistsAvailableHandler.onArtistsAvailable(artistModels);
                }
            });
        }
    }
}
