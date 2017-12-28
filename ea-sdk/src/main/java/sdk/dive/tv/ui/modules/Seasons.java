package sdk.dive.tv.ui.modules;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.touchvie.sdk.model.Card;

import sdk.dive.tv.ui.modules.viewholders.SeasonsHolder;

/**
 * Created by Tagsonomy S.L. on 13/10/2016.
 */

public class Seasons extends HorizontalListModule {

    public Seasons() {
        super();
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(this.layout, group, false);
        SeasonsHolder holder = new SeasonsHolder(viewGroup, getClass().getSimpleName());
        return holder;
    }

    @Override
    public boolean validate(Card cardData, boolean isCarousel) {
        return true;
    }
}