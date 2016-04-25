package bas.com.yamob.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import bas.com.yamob.models.ArtistModel;
import bas.com.yamob.R;
import bas.com.yamob.ui.widgets.AspectImageView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by bas on 16.04.16.
 */
public class InfoAboutArtistActivity extends AppCompatActivity {
    public static final String EXTRA_ARTIST = "artist";
    private static final double BIG_COVER_HEIGHT_ASPECT_PORTRAIT = 0.7d;
    private static final double BIG_COVER_HEIGHT_ASPECT_LANDSCAPE = 0.35d;

    @Bind(R.id.ivCoverBig)
    AspectImageView ivCoverBig;

    @Bind(R.id.tvGenres)
    TextView tvGenres;

    @Bind(R.id.tvAlbumsAndTracks)
    TextView tvAlbumsAndTracks;

    @Bind(R.id.tvBiography)
    TextView tvBiography;

    private ArtistModel artistModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_about_artist_activity);

        artistModel = (ArtistModel) getIntent().getSerializableExtra(EXTRA_ARTIST);
        if (artistModel == null) {
            Log.e("InfoAboutArtistActivity", "Does not contain extra artist");
            Toast.makeText(this, R.string.info_anout_artist_activity_no_extra_error, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        Log.d("InfoAboutArtistActivity", "Artist: " + artistModel.getName());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setTitle(artistModel.getName());
    }

    @Override
    protected void onStart() {
        super.onStart();
        ButterKnife.bind(this);
        updateUi();

        ivCoverBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InfoAboutArtistActivity.this, ArtistBigCoverActivity.class).putExtra(ArtistBigCoverActivity.EXTRA_BIG_COVER, artistModel.getCover().getBig()));
            }
        });
    }


    private void updateUi() {
        Picasso.with(this).load(artistModel.getCover().getBig()).placeholder(R.color.colorPrimary).into(ivCoverBig);
        double heightAspect;
        switch (this.getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                heightAspect = BIG_COVER_HEIGHT_ASPECT_LANDSCAPE;
                break;
            default:
                heightAspect = BIG_COVER_HEIGHT_ASPECT_PORTRAIT;
        }
        ivCoverBig.setHeightAspect(heightAspect);

        tvGenres.setText(TextUtils.join(", ", artistModel.getGenres()));
        tvAlbumsAndTracks.setText(
                String.format(getString(R.string.artists_list_element_albums_and_tracks_template),
                        String.format(getResources().getQuantityString(R.plurals.artists_list_element_albums_template, artistModel.getNumberOfAlbums()), artistModel.getNumberOfAlbums()),
                        String.format(getResources().getQuantityString(R.plurals.artists_list_element_tracks_template, artistModel.getNumberOfTracks()), artistModel.getNumberOfTracks())));
        tvBiography.setText(artistModel.getDescription());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (artistModel.getLinkToArtist() != null) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.artist_info_actions, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_open_artist_syte:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(artistModel.getLinkToArtist())));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
