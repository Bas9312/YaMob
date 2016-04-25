package bas.com.yamob.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import bas.com.yamob.models.ArtistModel;
import bas.com.yamob.events.ArtistsAvailableEvent;
import bas.com.yamob.ArtistsCache;
import bas.com.yamob.ArtistsListGetter;
import bas.com.yamob.events.ArtistsNotAvailableEvent;
import bas.com.yamob.DefaultArtistsListGetter;
import bas.com.yamob.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by bas on 16.04.16.
 */
public class ArtistsListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.srlArtistsListContainer)
    SwipeRefreshLayout srlArtistsListContainer;

    @Bind(R.id.lvArtistsList)
    ListView lvArtistsList;

    private static ArtistsListGetter usedGetter = null;

    private ArtistsListGetter.OnArtistsAvailableHandler artistsAvailableHandler = new ArtistsListGetter.OnArtistsAvailableHandler() {
        @Override
        public void onArtistsAvailable(List<ArtistModel> artistModels) {
            if (artistModels != null) {
                Collections.sort(artistModels, new Comparator<ArtistModel>() {
                    @Override
                    public int compare(ArtistModel lhs, ArtistModel rhs) {
                        if (lhs == null) return -1;
                        if (rhs == null) return 1;
                        return lhs.getName().compareTo(rhs.getName());
                    }
                });
                EventBus.getDefault().post(new ArtistsAvailableEvent(artistModels));
            } else EventBus.getDefault().post(new ArtistsNotAvailableEvent());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artists_list_activity);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setTitle(getString(R.string.artists_list_activity_title));

        srlArtistsListContainer.setOnRefreshListener(this);
        srlArtistsListContainer.setColorSchemeResources(R.color.ptr_blue, R.color.ptr_green, R.color.ptr_yellow, R.color.ptr_red);

        getData(true);
    }

    private void getData(boolean isUseCachedData) {
        if (usedGetter == null || !isUseCachedData) usedGetter = new ArtistsCache(new DefaultArtistsListGetter());

        usedGetter.getArtistsList(artistsAvailableHandler);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }


    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ArtistsAvailableEvent event) {
        Log.d("onEvent", "ArtistsAvailableEvent, size: " + event.artistModels.size());
        lvArtistsList.setAdapter(new ArtistsListAdapter(this, event.artistModels));
        lvArtistsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("onItemSelected", "Selected position: " + position);
                startActivity(new Intent(ArtistsListActivity.this, InfoAboutArtistActivity.class).putExtra(InfoAboutArtistActivity.EXTRA_ARTIST, ((ArtistModel)parent.getItemAtPosition(position))));
            }
        });

        hideRefresh();
    }

    private void hideRefresh() {
        if (srlArtistsListContainer.isRefreshing()) srlArtistsListContainer.setRefreshing(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ArtistsNotAvailableEvent event) {
        Log.d("onEvent", "ArtistsNotAvailableEvent");
        Toast.makeText(ArtistsListActivity.this, R.string.artists_list_activity_data_unavailable, Toast.LENGTH_SHORT).show();

        hideRefresh();
    }

    @Override
    public void onRefresh() {
        Log.d("onRefresh()", "start refresh");
        getData(false);
    }
}
