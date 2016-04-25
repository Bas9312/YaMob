package bas.com.yamob;

import java.util.List;

import bas.com.yamob.models.ArtistModel;

/**
 * Created by bas on 25.04.16.
 */
public interface ArtistsListGetter {
    void getArtistsList(final OnArtistsAvailableHandler onArtistsAvailableHandler);

    interface OnArtistsAvailableHandler {
        void onArtistsAvailable(List<ArtistModel> artistModels);
    }
}
