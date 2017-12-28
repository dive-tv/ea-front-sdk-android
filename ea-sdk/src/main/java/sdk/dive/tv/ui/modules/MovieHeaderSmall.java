package sdk.dive.tv.ui.modules;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.touchvie.sdk.model.Card;

import sdk.dive.tv.ui.modules.viewholders.MovieHeaderHolder;

/**
 * Created by Tagsonomy S.L. on 04/10/2016.
 */

public class MovieHeaderSmall extends MovieHeader {
    /**
     * Default constructor
     */
    public MovieHeaderSmall() {
        super();
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(this.layout, group, false);
//        MovieHeaderSmallHolder holder = new MovieHeaderSmallHolder(viewGroup, getClass().getSimpleName());
        MovieHeaderHolder holder = new MovieHeaderHolder(viewGroup, getClass().getSimpleName());
        return holder;
    }

    @Override
    public boolean validate(Card cardData, boolean isCarousel) {
        if (cardData.getTitle() == null || !isCarousel) {
            return false;
        }
        return true;
    }
}
