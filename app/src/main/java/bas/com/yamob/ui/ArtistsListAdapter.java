package bas.com.yamob.ui;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import bas.com.yamob.models.ArtistModel;
import bas.com.yamob.R;

/**
 * Created by bas on 16.04.16.
 */
public class ArtistsListAdapter extends ArrayAdapter<ArtistModel> {
    private final List<ArtistModel> artistModels;
    private final Context context;
    public ArtistsListAdapter(Context context, List<ArtistModel> artistModels) {
        super(context, R.layout.artists_list_element, artistModels);
        this.artistModels = artistModels;
        this.context = context;
    }

    @Override
    public int getCount() {
        return artistModels.size();
    }

    @Override
    public ArtistModel getItem(int pos) {
        return artistModels.get(pos);
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup viewGroup) {
        final ArtistModel oneArtist = getItem(pos);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.artists_list_element, null);
            holder = new ViewHolder();
            holder.ivCover = (ImageView) convertView.findViewById(R.id.ivCover);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvGenres = (TextView) convertView.findViewById(R.id.tvGenres);
            holder.tvAlbumsAndTracks = (TextView) convertView.findViewById(R.id.tvAlbumsAndTracks);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        setDataAboutArtist(holder, oneArtist);

        return convertView;
    }

    private void setDataAboutArtist(ViewHolder holder, ArtistModel oneArtist) {
        holder.tvName.setText(oneArtist.getName());
        holder.tvGenres.setText(TextUtils.join(", ", oneArtist.getGenres()));
        holder.tvAlbumsAndTracks.setText(
                String.format(context.getString(R.string.artists_list_element_albums_and_tracks_template),
                        String.format(context.getResources().getQuantityString(R.plurals.artists_list_element_albums_template, oneArtist.getNumberOfAlbums()), oneArtist.getNumberOfAlbums()),
                        String.format(context.getResources().getQuantityString(R.plurals.artists_list_element_tracks_template, oneArtist.getNumberOfTracks()), oneArtist.getNumberOfTracks())));
        Picasso.with(context).load(oneArtist.getCover().getSmall()).placeholder(R.color.colorPrimary).into(holder.ivCover);
    }

    private class ViewHolder {
        public ImageView ivCover;
        public TextView tvName;
        public TextView tvGenres;
        public TextView tvAlbumsAndTracks;
    }
}
