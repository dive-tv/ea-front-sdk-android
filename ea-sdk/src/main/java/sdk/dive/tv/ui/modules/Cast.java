package sdk.dive.tv.ui.modules;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.touchvie.sdk.model.Card;
import com.touchvie.sdk.model.Duple;
import com.touchvie.sdk.model.RelationModule;

import sdk.dive.tv.ui.Utils;
import sdk.dive.tv.ui.modules.viewholders.CastHolder;

import static com.touchvie.sdk.model.Duple.ContentTypeEnum.CASTING;

/**
 * Created by Tagsonomy S.L. on 04/10/2016.
 */

public class Cast extends CastLocationsModule {

    /**
     * Default constructor
     */
    public Cast() {
        super();
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(this.layout, group, false);
        CastHolder holder = new CastHolder(viewGroup, getClass().getSimpleName());
        return holder;
    }

    @Override
    public boolean validate(Card cardData, boolean isCarousel) {

        RelationModule relation = Utils.getRelation(cardData, CASTING.getValue());
        if (relation == null || ((Duple) relation).getData() == null || ((Duple) relation).getData().size() < 1) {
            return false;
        }

        return true;
    }
}