package bas.com.yamob.models;

import java.io.Serializable;

/**
 * Created by bas on 16.04.16.
 */
public class ArtistModel implements Serializable {
    private long id;
    private String name;
    private String[] genres;
    private int tracks;
    private int albums;
    private String link;
    private String description;
    private Cover cover;

    public String getName() {
        return name;
    }

    public String[] getGenres() {
        return genres;
    }

    public int getNumberOfTracks() {
        return tracks;
    }

    public int getNumberOfAlbums() {
        return albums;
    }

    public String getLinkToArtist() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public Cover getCover() {
        return cover;
    }

    public class Cover implements Serializable{
        private String small;
        private String big;

        public String getSmall() {
            return small;
        }

        public String getBig() {
            return big;
        }
    }

    @Override
    public String toString() {
        return String.format("name: %s, tracks: %d, albums: %d, description: %s", name, tracks, albums, description);
    }
}
