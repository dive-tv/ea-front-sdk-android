package sdk.dive.tv.ui.modules;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.RelationModule;
import com.touchvie.sdk.model.Single;

import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.modules.viewholders.RecommendedHolder;

import static com.touchvie.sdk.model.Single.ContentTypeEnum.RECOMMENDED;

/**
 * Created by Tagsonomy S.L. on 14/11/2016.
 */

public class Recommended extends HorizontalListModule {

    public Recommended() {
        super();
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(this.layout, group, false);
        RecommendedHolder holder = new RecommendedHolder(viewGroup, getClass().getSimpleName());
        return holder;
    }

    @Override
    public boolean validate(Card cardData, boolean isCarousel) {

        RelationModule relation = Utils.getRelation(cardData, RECOMMENDED.getValue());
        if (relation == null || ((Single) relation).getData() == null || ((Single) relation).getData().size() < 1) {
            return false;
        }
        return true;
    }
}
