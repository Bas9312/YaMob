package bas.com.yamob.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import bas.com.yamob.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by bas on 16.04.16.
 */
public class ArtistBigCoverActivity extends AppCompatActivity {
    public static final String EXTRA_BIG_COVER = "bigCover";

    @Bind(R.id.ivCoverBig)
    ImageView ivCoverBig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artist_big_cover_activity);
        ButterKnife.bind(this);

        String bigCover = getIntent().getStringExtra(EXTRA_BIG_COVER);
        if (bigCover == null) {
            Log.e("ArtistBigCoverActivity", "Does not contain extra bigCover");
            finish();
            return;
        }

        Picasso.with(this).load(bigCover).placeholder(R.color.colorPrimary).into(ivCoverBig);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
    }


}
