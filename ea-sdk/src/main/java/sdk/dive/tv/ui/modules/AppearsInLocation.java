package sdk.dive.tv.ui.modules;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.Duple;
import com.touchvie.sdk.model.RelationModule;

import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.modules.viewholders.AppearsInLocationHolder;

import static com.touchvie.sdk.model.Duple.ContentTypeEnum.FEATURED_IN;

/**
 * Created by Tagsonomy S.L. on 13/10/2016.
 */

public class AppearsInLocation extends HorizontalListModule {

    public AppearsInLocation() {
        super();
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(this.layout, group, false);
        AppearsInLocationHolder holder = new AppearsInLocationHolder(viewGroup, getClass().getSimpleName());
        return holder;
    }

    @Override
    public boolean validate(Card cardData, boolean isCarousel) {
        RelationModule relation = Utils.getRelation(cardData,FEATURED_IN.getValue());
        if (relation == null || ((Duple) relation).getData() == null || ((Duple) relation).getData().size() == 0) {
            return false;
        }
        return true;
    }
}
