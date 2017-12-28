package sdk.dive.tv.ui.modules;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.RelationModule;
import com.touchvie.sdk.model.Single;

import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.modules.viewholders.MovieSoundtrackHolder;

import static com.touchvie.sdk.model.Single.ContentTypeEnum.MOVIE_SONGS;

/**
 * Created by Tagsonomy S.L. on 13/10/2016.
 */

public class MovieSoundtrack extends HorizontalListModule {

    public MovieSoundtrack() {
        super();
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(this.layout, group, false);
        MovieSoundtrackHolder holder = new MovieSoundtrackHolder(viewGroup, getClass().getSimpleName());
        return holder;
    }

    @Override
    public boolean validate(Card cardData, boolean isCarousel) {

        RelationModule relation = Utils.getRelation(cardData, MOVIE_SONGS.getValue());
        if (relation == null || ((Single) relation).getData() == null || ((Single) relation).getData().size() < 1) {
            return false;
        }
        return true;
    }
}

