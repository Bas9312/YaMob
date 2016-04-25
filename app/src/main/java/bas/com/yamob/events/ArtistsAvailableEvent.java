package bas.com.yamob.events;

import java.util.List;

import bas.com.yamob.models.ArtistModel;

/**
 * Created by bas on 16.04.16.
 */
public class ArtistsAvailableEvent {
    public final List<ArtistModel> artistModels;

    public ArtistsAvailableEvent(List<ArtistModel> artistModels) {
        this.artistModels = artistModels;
    }
}
